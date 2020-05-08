package com.example.sw2.controller;

import com.example.sw2.utils.DeleteObjectNonVersionedBucket;
import com.example.sw2.utils.UploadObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Controller
public class MainController {

    private final String FILE_NAME = "test.png";

    @GetMapping("/{p}")
    public String cont(@PathVariable("p") String page){
        return page;
    }

    @GetMapping("/")
    public String login(){
        return "login";
    }

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

}
