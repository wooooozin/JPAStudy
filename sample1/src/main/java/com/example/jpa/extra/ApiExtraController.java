package com.example.jpa.extra;

import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.extra.model.OpenApiResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
public class ApiExtraController {


    @GetMapping("/api/extra/pharmacy")
    public ResponseEntity<?> pharmacy() {
        String apiResult = "";
        String apiKey = "4ai%2FD6l7vfzncAjYnLRRlEgF2w3Oj4mT%2BBs6SJ96GZyL4Nj27cirw14KcDkSR6DddR6AbH%2B0VNWxl0ac8HbUbA%3D%3D";
        String url = "https://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown?serviceKey=%s&pageNo=1&numOfRows=10";
        try {
            URI uri = new URI(String.format(url, apiKey));
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            apiResult = restTemplate.getForObject(uri, String.class);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        OpenApiResult jsonResult = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonResult = objectMapper.readValue(apiResult, OpenApiResult.class);

        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseResult.success(jsonResult);
    }
}
