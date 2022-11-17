package com.numble.backend.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.marvinproject.image.transform.scale.Scale;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.numble.backend.common.exception.business.FileCountExceedException;
import com.numble.backend.common.exception.business.FileUploadFailedException;

import lombok.RequiredArgsConstructor;
import marvin.image.MarvinImage;

@RequiredArgsConstructor
@Service
public class S3Utils {

	private static final int RESIZE_SIZE=768;

	public static String uploadFileS3(AmazonS3Client amazonS3Client, String bucketName,
		MultipartFile multipartFile) {

		validateFileExists(multipartFile);
		String fileName = buildFileName(multipartFile.getOriginalFilename());

		uploadFile(amazonS3Client, bucketName, multipartFile, fileName);

		return fileName;
	}

	public static List<String> uploadMultiFilesS3(AmazonS3Client amazonS3Client, String bucketName,
		List<MultipartFile> multipartFiles, int size) {

		List<String> fileNames = new ArrayList<>();

		for (MultipartFile multipartFile : multipartFiles) {
			validateFileExists(multipartFile);

			if (fileNames.size() > size) {
				throw new FileCountExceedException();
			}

			String fileName = buildFileName(multipartFile.getOriginalFilename());
			fileNames.add(fileName);

			uploadFile(amazonS3Client, bucketName, multipartFile, fileName);
		}
		return fileNames;
	}

	private static void uploadFile(AmazonS3Client amazonS3Client, String bucketName, MultipartFile file,
		String fileName) {

		String fileFormatName = file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);
		MultipartFile resizedFile = resizeImage(fileName, fileFormatName, file, RESIZE_SIZE);

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(resizedFile.getSize());
		objectMetadata.setContentType(file.getContentType());

		try (InputStream inputStream = resizedFile.getInputStream()) {
			amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw new FileUploadFailedException();
		}

	}

	private static void validateFileExists(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			throw new FileUploadFailedException();
		}

		if (!Objects.requireNonNull(multipartFile.getContentType()).contains("image")) {
			throw new FileUploadFailedException("이미지 파일만 업로드 가능합니다.");
		}
	}

	private static String buildFileName(String originalFileName) {
		String FILE_EXTENSION_SEPARATOR = ".";

		int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		String fileExtension = originalFileName.substring(fileExtensionIndex);
		String fileName = originalFileName.substring(0, fileExtensionIndex);
		String now = String.valueOf(System.currentTimeMillis());

		return fileName + now + fileExtension;
	}

	private static MultipartFile resizeImage(String fileName, String fileFormatName, MultipartFile originalImage,
		int targetWidth) {
		try {
			BufferedImage image = ImageIO.read(originalImage.getInputStream());

			int originWidth = image.getWidth();
			int originHeight = image.getHeight();

			if (originWidth < targetWidth)
				return originalImage;

			MarvinImage imageMarvin = new MarvinImage(image);

			Scale scale = new Scale();
			scale.load();
			scale.setAttribute("newWidth", targetWidth);
			scale.setAttribute("newHeight", targetWidth * originHeight / originWidth);

			scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

			BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(imageNoAlpha, fileFormatName, baos);
			baos.flush();

			return new MockMultipartFile(fileName, baos.toByteArray());

		} catch (IOException e) {
			throw new FileUploadFailedException("파일 리사이징에 실패했습니다");
		}
	}

}
