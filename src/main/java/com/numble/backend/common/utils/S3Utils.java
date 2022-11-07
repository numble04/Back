package com.numble.backend.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.numble.backend.post.exception.FileCountExceedException;
import com.numble.backend.post.exception.FileUploadFailedException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class S3Utils {


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

			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(multipartFile.getContentType());

			try (InputStream inputStream = multipartFile.getInputStream()) {
				amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			} catch (IOException e) {
				throw new FileUploadFailedException();
			}
		}
		return fileNames;
	}

	private static void validateFileExists(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			throw new FileUploadFailedException();
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

}
