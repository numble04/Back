package com.numble.backend.meeting.application;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.numble.backend.cafe.domain.Cafe;
import com.numble.backend.cafe.domain.CafeRepository;
import com.numble.backend.cafe.exception.CafeNotFoundException;
import com.numble.backend.common.config.security.CustomUserDetails;
import com.numble.backend.common.utils.S3Utils;
import com.numble.backend.meeting.domain.Meeting;
import com.numble.backend.meeting.domain.MeetingUser;
import com.numble.backend.meeting.domain.repository.MeetingRepository;
import com.numble.backend.meeting.domain.mapper.MeetingCreateMapper;
import com.numble.backend.meeting.domain.repository.MeetingUserRepository;
import com.numble.backend.meeting.dto.request.MeetingCreateRequest;
import com.numble.backend.meeting.dto.response.MeetingDetailResponse;
import com.numble.backend.meeting.dto.response.MeetingResponse;
import com.numble.backend.meeting.exception.DuplicateMeetingUserException;
import com.numble.backend.meeting.exception.MeetingFullException;
import com.numble.backend.meeting.exception.MeetingNotFoundException;
import com.numble.backend.meeting.exception.MeetingUserNotFoundException;
import com.numble.backend.post.dto.response.PostDetailResponse;
import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserRepository;
import com.numble.backend.user.exception.UserNotAuthorException;
import com.numble.backend.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeetingService {

	private final MeetingRepository meetingRepository;
	private final MeetingUserRepository meetingUserRepository;
	private final UserRepository userRepository;
	private final CafeRepository cafeRepository;
	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	@Transactional
	public Long save(CustomUserDetails customUserDetails, MeetingCreateRequest meetingCreateRequest,
		MultipartFile multipartFile) {

		Cafe cafe = cafeRepository.findById(meetingCreateRequest.getCafeId())
			.orElseThrow(() -> new CafeNotFoundException());

		User user = userRepository.findById(customUserDetails.getId())
			.orElseThrow(() -> new UserNotFoundException());

		String img = uploadFile(multipartFile);

		Meeting meeting = MeetingCreateMapper.INSTANCE.toEntity(meetingCreateRequest, img, cafe);

		MeetingUser meetingUser = new MeetingUser(user, meeting, true, true, false);
		meetingUserRepository.save(meetingUser);

		return meetingRepository.save(meeting).getId();
	}

	public Slice<MeetingResponse> findAllByDong(String city, String dong, Double latitude, Double longitude,
		LocalDate startDate, LocalDate endDate, Pageable pageable) {

		return meetingRepository.findAllByDong(city, dong, latitude, longitude, startDate, endDate, pageable);
	}

	@Transactional
	public String uploadFile(MultipartFile multipartFile) {
		String fileName = S3Utils.uploadFileS3(amazonS3Client, bucketName, multipartFile);
		return amazonS3Client.getUrl(bucketName, fileName).toString();
	}

	public MeetingDetailResponse findById(Long id, CustomUserDetails customUserDetails) {

		MeetingUser meetingUser = meetingUserRepository.findByUserIdAndMeetingId(customUserDetails.getId(), id)
			.orElseThrow(() -> new MeetingUserNotFoundException());

		MeetingDetailResponse response = meetingRepository.findDetailById(id, customUserDetails.getId(),
				meetingUser.getIsLeader())
			.orElseThrow(() -> new MeetingNotFoundException());

		return response;
	}

	@Transactional
	public Long saveMeetingUser(Long id, CustomUserDetails customUserDetails) {

		validateDuplicateMeetingUser(customUserDetails.getId(), id);

		User user = userRepository.findById(customUserDetails.getId())
			.orElseThrow(() -> new UserNotFoundException());

		Meeting meeting = meetingRepository.findById(id)
			.orElseThrow(() -> new MeetingNotFoundException());

		if (meeting.getIsFull()){
			throw new MeetingFullException();
		}

		MeetingUser meetingUser = new MeetingUser(user, meeting, false, false, false);

		return meetingUserRepository.save(meetingUser).getId();
	}

	@Transactional
	public void updateMeetingUserApprove(Long id, Long userId, CustomUserDetails customUserDetails) {

		validateUserIsAuthor(customUserDetails.getId(), id);

		MeetingUser meetingUser = meetingUserRepository.findByUserIdAndMeetingId(userId, id).
			orElseThrow(() -> new MeetingUserNotFoundException());

		meetingUser.updateApprove();

		checkIsFull(id);
	}

	@Transactional
	public void updateMeetingUserReject(Long id, Long userId, CustomUserDetails customUserDetails) {

		validateUserIsAuthor(customUserDetails.getId(), id);

		MeetingUser meetingUser = meetingUserRepository.findByUserIdAndMeetingId(userId, id).
			orElseThrow(() -> new MeetingUserNotFoundException());

		meetingUser.updateReject();
	}

	private void validateDuplicateMeetingUser(Long userId, Long id) {
		if (meetingUserRepository.findByUserIdAndMeetingId(userId, id).isPresent()) {
			throw new DuplicateMeetingUserException();
		}
	}

	private void validateUserIsAuthor(Long userId, Long id) {
		MeetingUser myMeetingUser = meetingUserRepository.findByUserIdAndMeetingId(userId, id).
			orElseThrow(() -> new MeetingUserNotFoundException());

		if (!myMeetingUser.getIsLeader()) {
			throw new UserNotAuthorException();
		}
	}

	@Transactional
	private void checkIsFull(Long id) {
		Meeting meeting = meetingRepository.findById(id)
			.orElseThrow(() -> new MeetingNotFoundException());

		int nowPersonnel = meetingUserRepository.countByMeetingId(id);
		meeting.updateIsFull(nowPersonnel);
	}
}
