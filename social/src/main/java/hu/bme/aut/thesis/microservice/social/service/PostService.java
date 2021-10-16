package hu.bme.aut.thesis.microservice.social.service;

import hu.bme.aut.thesis.microservice.social.models.PostsDto;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    public PostsDto getPosts() {
        PostsDto postDtos = new PostsDto();

        return postDtos;
    }
}
