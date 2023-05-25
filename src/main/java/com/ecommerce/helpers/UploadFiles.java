package com.ecommerce.helpers;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class UploadFiles {
    
    public static String saveFile(MultipartFile multipartFile, String path){

        String originalName = multipartFile.getOriginalFilename();

        if(originalName != null){
            originalName = originalName.replace(" ", "-");
        }else{
            return null;
        }

        String fileName = randomAlphaNumeric(8)+originalName;

        try {
            
            File file = new File(path + fileName);

            multipartFile.transferTo(file);

            return fileName;

        } catch (Exception e) {
            System.out.println("ERROR:"+e.getMessage());
            return null;
        }
    }

    public static String randomAlphaNumeric(int count){
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while(count -- != 0){
            int character = (int) (Math.random() * CHARACTERS.length());
            builder.append(CHARACTERS.charAt(character));
        }
        return builder.toString();
    }
}
