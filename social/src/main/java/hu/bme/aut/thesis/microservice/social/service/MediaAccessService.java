package hu.bme.aut.thesis.microservice.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class MediaAccessService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Value("${social.app.media-access-url}")
    private String mediaAccessUrl;

    public String getMediaAccessUrl(Integer userId, List<Integer> postIds) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));

        HttpEntity<Map<String, Object>> entity = new HttpEntity(new MediaAccessDto(userId, postIds), headers);

        try {
            String accessKey = restTemplateBuilder.build().postForEntity(mediaAccessUrl, entity, String.class).getBody();
            return  accessKey;
        } catch (RestClientException e) {
            return null;
        }
    }

    private class MediaAccessDto {
        public Integer userId;
        public List<Integer> postIds;

        public MediaAccessDto(Integer userId, List<Integer> postIds) {
            this.userId = userId;
            this.postIds = postIds;
        }
    }
}
