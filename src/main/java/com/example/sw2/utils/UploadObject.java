package com.example.sw2.utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.entity.Inventario;
import com.example.sw2.entity.RestBean;
import com.example.sw2.entity.RestResponse;
import com.example.sw2.entity.Usuarios;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class UploadObject {

    private final static String API_KEY = "80e50707-27f2-481a-96d5-23e61f4cd29c-r4nd0m";

    public static void main() throws IOException {
        Regions clientRegion = Regions.US_EAST_1;
        String bucketName = "test-bucket-sw2";
        String stringObjKeyName = "test/test_object2";
        String fileObjKeyName = "test/test.txt";
        String fileName = "/Users/Gustavo_Meza/Desktop/aaa/test.txt";
        try {
            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            // Upload a text string as a new object.
            s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            System.out.println(e.toString());
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

    public static void uploadPhoto(String fileObjKeyName,  File file) throws Exception {
        Regions clientRegion = Regions.US_EAST_1;
        String bucketName = CustomConstants.AWS_BUCKET_NAME;
        fileObjKeyName = CustomConstants.INVENTARIO+"/" + fileObjKeyName;
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName,  file);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/png");
            s3Client.putObject(request
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

    public static String uploadPhoto(String fileObjKeyName,  MultipartFile multipartFile, String folder) throws Exception {

        if(Objects.requireNonNull(multipartFile.getOriginalFilename()).contains("..")) {
            throw new IOException();
        }
        else {
            System.out.println("Paso 1");
            String var;
            if (multipartFile.getOriginalFilename().contains(".jpg")){
                var = ".jpg";
            }
            else if(multipartFile.getOriginalFilename().contains(".png")){
                var = ".png";
            }
            else {var = "";}
            fileObjKeyName = fileObjKeyName + var;
            File file = new File(fileObjKeyName);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            Regions clientRegion = Regions.US_EAST_1;
            String bucketName = CustomConstants.AWS_BUCKET_NAME;
            fileObjKeyName = folder + "/" + fileObjKeyName;
            System.out.println(fileObjKeyName);
            try {
                AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
                PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, file);
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(multipartFile.getContentType());
                s3Client.putObject(request
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                file.delete();
                return "https://"+CustomConstants.AWS_BUCKET_NAME+".s3.amazonaws.com/"+fileObjKeyName;
            } catch (SdkClientException e) {
                // The call was transmitted successfully, but Amazon S3 couldn't process
                // it, so it returned an error response.
                file.delete();
                e.printStackTrace();
            }
        }
        return null;

    }

    public static RestResponse uploadProfilePhoto(Usuarios u, MultipartFile multipartFile) throws JsonProcessingException {
        final String uri = "http://ec2-100-26-215-115.compute-1.amazonaws.com/saveProfile";
        String name = Integer.toString(u.getIdusuarios()* CustomConstants.BIGNUMBER).hashCode()+Integer.toString(u.getIdusuarios());
        return  sendFile(new RestBean(API_KEY, name, multipartFile), uri);
    }

    public static RestResponse uploadProductPhoto(Inventario i, MultipartFile multipartFile) throws JsonProcessingException {
        final String uri = "http://ec2-100-26-215-115.compute-1.amazonaws.com/saveInventory";
        String name = i.getCodigoinventario();
        return sendFile(new RestBean(API_KEY, name, multipartFile), uri);
    }

    private static RestResponse sendFile(RestBean data, String uri) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        RestTemplate restTemplate = new RestTemplate();
        String someJsonString = mapper.writeValueAsString(data);

        return restTemplate.postForObject(uri,mapper.readValue(someJsonString,RestBean.class), RestResponse.class);
    }
}

