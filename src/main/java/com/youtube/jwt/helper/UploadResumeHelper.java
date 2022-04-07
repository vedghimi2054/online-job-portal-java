package com.youtube.jwt.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class UploadResumeHelper {
    public final String UPLOAD_DIR="/home/spacex/Desktop/springbootimage";

    public boolean uploadFile(MultipartFile multipartFile){
        boolean f=false;

        try {
//            InputStream is=multipartFile.getInputStream();
//            byte data[]= new byte[is.available()];
//            is.read(data);
//
//            //write into folder
//            FileOutputStream fos=new FileOutputStream(UPLOAD_DIR+"/"+multipartFile.getOriginalFilename());
//            fos.write(data);
//            fos.close();
            //one step
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss");
            String fileLocation  = UPLOAD_DIR + File.separator+multipartFile.getOriginalFilename()+localDateTime.format(dateTimeFormatter);
            System.out.println("file location");
            Files.copy(multipartFile.getInputStream(), Paths.get(fileLocation), StandardCopyOption.REPLACE_EXISTING);
            f=true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }

}
