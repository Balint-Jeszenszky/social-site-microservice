package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.api.CommentApi;
import hu.bme.aut.thesis.microservice.social.mapper.CommentMapper;
import hu.bme.aut.thesis.microservice.social.model.Comment;
import hu.bme.aut.thesis.microservice.social.models.CommentDto;
import hu.bme.aut.thesis.microservice.social.models.NewCommentDto;
import hu.bme.aut.thesis.microservice.social.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController implements CommentApi {

    @Autowired
    private CommentService commentService;

    @Override
    public ResponseEntity<Void> deleteCommentCommentId(Integer commentId) {
        commentService.deleteComment(commentId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<CommentDto>> getCommentPostId(Integer postId) {
        List<Comment> comments = commentService.getCommentsForPost(postId);

        List<CommentDto> commentDtos = mapCommentsToCommentDto(comments);

        return new ResponseEntity(commentDtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentDto> postComment(NewCommentDto body) {
        Comment comment = commentService.createComment(body);

        CommentDto commentDto = CommentMapper.INSTANCE.commentToCommentDto(comment);

        return new ResponseEntity(commentDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CommentDto> putCommentCommentId(Integer commentId, NewCommentDto body) {
        Comment comment = commentService.editComment(commentId, body);

        CommentDto commentDto = CommentMapper.INSTANCE.commentToCommentDto(comment);

        return new ResponseEntity(commentDto, HttpStatus.ACCEPTED);
    }

    private List<CommentDto> mapCommentsToCommentDto(List<Comment> comments) {
        return comments.stream().map(c -> CommentMapper.INSTANCE.commentToCommentDto(c)).collect(Collectors.toList());
    }
}
