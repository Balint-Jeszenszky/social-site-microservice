package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.api.DeleteApi;
import hu.bme.aut.thesis.microservice.social.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteController implements DeleteApi {

    @Autowired
    private DeleteService deleteService;

    @Override
    public ResponseEntity<Void> deleteDeleteUserId(Integer userId) {
        deleteService.deleteUser(userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
