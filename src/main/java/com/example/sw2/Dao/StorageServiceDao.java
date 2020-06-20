package com.example.sw2.Dao;


import com.example.sw2.constantes.CustomConstants;
import com.example.sw2.entity.Inventario;
import com.example.sw2.entity.StorageServiceResponse;
import com.example.sw2.entity.Usuarios;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class StorageServiceDao {

    private final String USER_PATH = "saveProfile";
    private final String INVENTORY_PATH = "saveInventory";

    private final String BASE_URL = "https://storage-service.mosqoy.dns-cloud.net/";
    private final String API_KEY = "80e50707-27f2-481a-96d5-23e61f4cd29c-p4ssw0rd-r4nd0m";

    public StorageServiceResponse store(Usuarios u, MultipartFile multipartFile) throws IOException {
        if(multipartFile!=null){
            String name = Integer.toString(u.getIdusuarios()*CustomConstants.BIGNUMBER).hashCode()+Integer.toString(u.getIdusuarios());
            StorageServiceResponse response = new RestTemplate().postForObject(BASE_URL+USER_PATH,
                    prepareEntity(multipartFile,name),StorageServiceResponse.class);
            if (response!=null){
                u.setFoto(response.getUrl());
                return response;
            }
            else return new StorageServiceResponse(){{
                setStatus("error");
                setMsg("Error en transacción de imagen");
            }};

        }
        return null;
    }

    public StorageServiceResponse store(Inventario inventario, MultipartFile multipartFile) throws IOException {
        if(multipartFile!=null){
            String name = inventario.getCodigoinventario();
            StorageServiceResponse response = new RestTemplate().postForObject(BASE_URL+INVENTORY_PATH,
                    prepareEntity(multipartFile,name),StorageServiceResponse.class);
            if (response!=null){
                inventario.setFoto(response.getUrl());
                return response;
            }
            else return new StorageServiceResponse(){{
                setStatus("error");
                setMsg("Error en transacción de imagen");
            }};
        }
        return null;
    }

    private HttpEntity<MultiValueMap<String,Object>> prepareEntity(MultipartFile multipartFile, String name) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource contentsAsResource = new ByteArrayResource(multipartFile.getBytes()){
            @Override
            public String getFilename(){ return multipartFile.getOriginalFilename(); }
        };
        body.add("key", API_KEY);
        body.add("file", contentsAsResource);
        body.add("name", name);
        return new HttpEntity<>(body,headers);
    }

}
