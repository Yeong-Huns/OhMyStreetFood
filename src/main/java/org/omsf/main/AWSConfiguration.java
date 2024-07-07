package org.omsf.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

/**
 * packageName    : org.omsf.main
 * fileName       : AWSConfiguration
 * author         : KIMCHANGHWAN
 * date           : 2024-07-05
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-05      KIMCHANGHWAN       
 */

@Configuration
@EnableWebMvc
public class AWSConfiguration implements WebMvcConfigurer{
	
	@Value("${aws.accesskey}")
	private String accessKey;

	@Value("${aws.secretkey}")
	private String secretKey;
	
	@Bean
	public BasicAWSCredentials AwsCredentials() {
		//access키, secret키
		BasicAWSCredentials AwsCreds = new BasicAWSCredentials(accessKey, secretKey); 	
		return AwsCreds;
	}	
	
	@Bean
	public AmazonS3 AwsS3Client() {
		
		AmazonS3 s3Builder = AmazonS3ClientBuilder.standard() // 스탠다드 S3
				.withRegion(Regions.AP_NORTHEAST_2) //지역
				.withCredentials(new AWSStaticCredentialsProvider(this.AwsCredentials()))
				.build();	

		return s3Builder;
	}
}
