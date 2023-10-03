package com.example.jpa.extra;

import com.example.jpa.common.model.ResponseResult;
import com.example.jpa.extra.model.KakaoTranslateInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApiKakaoController {

    @GetMapping("/api/extra/kakao/translate")
    public ResponseEntity<?> translate(@RequestBody KakaoTranslateInput kakaoTranslateInput) {
        String key = "";
        String url = "https://depi.kakao.com/v2/translation/translate";

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> parameter = new LinkedMultiValueMap<>();
        parameter.add("src_lang", "kr");
        parameter.add("target_lang", "en");
        parameter.add("query", kakaoTranslateInput.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "KakaoAK " + key);

        HttpEntity formEntity = new HttpEntity(parameter, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, formEntity, String.class);



        return ResponseResult.success(response.getBody());
    }
}
