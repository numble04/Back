package com.numble.backend.meeting.application;

import org.springframework.beans.factory.annotation.Value;
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
import com.numble.backend.user.domain.User;
import com.numble.backend.user.domain.UserRepository;
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

		Meeting meeting = MeetingCreateMapper.INSTANCE.toEntity(meetingCreateRequest,img,cafe);

		MeetingUser meetingUser = new MeetingUser(user,meeting,true,true);
		meetingUserRepository.save(meetingUser);

		return meetingRepository.save(meeting).getId();
	}

	@Transactional
	public String uploadFile(MultipartFile multipartFile) {
		String fileName = S3Utils.uploadFileS3(amazonS3Client, bucketName, multipartFile);
		return amazonS3Client.getUrl(bucketName, fileName).toString();
	}
}
