package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.api.MediaApi;
import hu.bme.aut.thesis.microservice.social.models.MediaStatusDto;
import hu.bme.aut.thesis.microservice.social.service.MediaAccessService;
import hu.bme.aut.thesis.microservice.social.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostMediaController extends GlobalExceptionHandler implements MediaApi {

    @Autowired
    private PostService postService;

    @Autowired
    private MediaAccessService mediaAccessService;

    @Override
    public ResponseEntity<Void> getMediaId(Integer id) {

        return null;
    }

    @Override
    public ResponseEntity<Void> putMedia(Integer id, MediaStatusDto body) {
        postService.setPostMediaStatus(id, body.getStatus(), body.getName());
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
