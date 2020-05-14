package com.example.sw2.controller;

import com.example.sw2.utils.CustomMailService;
import com.example.sw2.utils.DeleteObjectNonVersionedBucket;
import com.example.sw2.utils.UploadObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class MainController {

    @Autowired
    CustomMailService customMailService;

    private final String FILE_NAME = "test.png";


    @GetMapping("/file")
    public String blankPageFile(){
        return "blank";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("archivo") MultipartFile multipartFile){
        try {
            DeleteObjectNonVersionedBucket.deletePhoto(FILE_NAME);
            File file = new File(FILE_NAME);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            UploadObject.uploadPhoto(FILE_NAME, file);
            file.delete();
        }
        catch (Exception e){
            System.out.println("error");
        }
        return "redirect:/file";
    }

    @ResponseBody
    @GetMapping(value = "/testEmail",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> testEmail(){

        try {
            customMailService.sendTestEmailWithAtachment();
            return new ResponseEntity<>("nice", HttpStatus.OK);
        }
        catch (IOException | MessagingException ex){
            return new ResponseEntity<>(ex.toString(), HttpStatus.ACCEPTED);
        }
    }

}
