package hu.bme.aut.thesis.microservice.social.mapper;

import hu.bme.aut.thesis.microservice.social.model.Post;
import hu.bme.aut.thesis.microservice.social.models.PostDto;
import org.mapstruct.factory.Mappers;

public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostDto postToPostDto(Post user);
}
