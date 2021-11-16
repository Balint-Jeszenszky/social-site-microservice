package hu.bme.aut.thesis.microservice.social.mapper;

import hu.bme.aut.thesis.microservice.social.model.Comment;
import hu.bme.aut.thesis.microservice.social.models.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "comment", target = "text")
    CommentDto commentToCommentDto(Comment comment);
}
