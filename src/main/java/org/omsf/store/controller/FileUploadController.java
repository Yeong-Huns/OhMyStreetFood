package org.omsf.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
	
	 @Autowired
	 private ServletContext servletContext;
	
	@GetMapping("/upload")
	public String fileUploadForm() {
		return "store/uploadtest/fileUploadForm";
	}
	
	@RequestMapping("/upload/fileUploadMultiple")
    public String fileUploadMultiple(@RequestParam("uploadFileMulti") ArrayList<MultipartFile> files, Model model) throws IOException {
        String savedFileName = "";
        
        String uploadPath = servletContext.getRealPath("/uploaded_files/");

        ArrayList<String> originalFileNameList = new ArrayList<String>();
        for(MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            originalFileNameList.add(originalFileName);
            
            UUID uuid = UUID.randomUUID();
            // 확장자 추출
            String fileExtension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            savedFileName = uuid.toString() + fileExtension;

            File file1 = new File(uploadPath + savedFileName);
           
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            
            //서버로 전송
            file.transferTo(file1);
        }
        // model로 저장
        model.addAttribute("originalFileNameList", originalFileNameList);
        return"store/uploadtest/fileUploadMultipleResult";
    }
}
