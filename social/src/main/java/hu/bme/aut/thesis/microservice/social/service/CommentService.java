package hu.bme.aut.thesis.microservice.social.service;

import hu.bme.aut.thesis.microservice.social.controller.exceptions.ForbiddenException;
import hu.bme.aut.thesis.microservice.social.controller.exceptions.NotFoundException;
import hu.bme.aut.thesis.microservice.social.model.Comment;
import hu.bme.aut.thesis.microservice.social.model.Post;
import hu.bme.aut.thesis.microservice.social.models.NewCommentDto;
import hu.bme.aut.thesis.microservice.social.repository.CommentRepository;
import hu.bme.aut.thesis.microservice.social.repository.PostRepository;
import hu.bme.aut.thesis.microservice.social.security.LoggedInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LoggedInUserService loggedInUserService;

    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found"));
        Post post = postRepository.findById(comment.getPostId()).orElseThrow(() -> new NotFoundException("Post not found"));

        Integer userId = loggedInUserService.getLoggedInUser().getId();

        if (!comment.getUserId().equals(userId)) {
            throw new ForbiddenException("Wrong user");
        }

        post.setComments(post.getComments() - 1);

        postRepository.save(post);

        commentRepository.delete(comment);
    }

    public List<Comment> getCommentsForPost(Integer postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment createComment(NewCommentDto newCommentDto) {
        Integer userId = loggedInUserService.getLoggedInUser().getId();

        Post post = postRepository.findById(newCommentDto.getPostId()).orElseThrow(() -> new NotFoundException("Post not found"));

        Comment comment = new Comment(userId,post.getId(), newCommentDto.getText());

        commentRepository.save(comment);

        post.setComments(post.getComments() + 1);

        postRepository.save(post);

        return comment;
    }

    public Comment editComment(Integer commentId, NewCommentDto newCommentDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment not found"));

        Integer userId = loggedInUserService.getLoggedInUser().getId();

        if (!comment.getUserId().equals(userId)) {
            throw new ForbiddenException("Wrong user");
        }

        comment.setComment(newCommentDto.getText());

        commentRepository.save(comment);

        return comment;
    }
}
