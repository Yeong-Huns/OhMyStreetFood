package org.omsf.member.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;


@Service
public class UploadService {
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${aws.bucketname}")
	private String bucketName;
	
	private static final String UPLOAD_PATH = "member/";
	
	public MultipartFile getImageAsMultipartFile(String imagePath) throws IOException {
		 URL url = new URL(imagePath);
		    ByteArrayOutputStream output = new ByteArrayOutputStream();
		    
		    try (InputStream inputStream = url.openStream()) {
		        byte[] buffer = new byte[1024];
		        int bytesRead;
		        while ((bytesRead = inputStream.read(buffer)) != -1) {
		            output.write(buffer, 0, bytesRead);
		        }
		    }
		    
		    byte[] imageBytes = output.toByteArray();
		    return new MockMultipartFile("file", "image.png", "image/png", imageBytes);
	}
	
    public String uploadImage(MultipartFile file) {
		
        // 확장자 추출
    	String originalFileName = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid.toString() + fileExtension;
        
        //파일경로: 업로드폴더 + uuid.확장자
		String filePath = UPLOAD_PATH + savedFileName;
		try {
			s3Client.putObject(new PutObjectRequest(bucketName, filePath, file.getInputStream(), null));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String url = s3Client.getUrl(bucketName, filePath).toString();
		
		return url;
    }
}
