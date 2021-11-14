package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.model.Post;
import hu.bme.aut.thesis.microservice.social.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;


    @BeforeEach
    void setup() {
        postRepository.deleteAll();

        createPost(1, "asd");
    }

    @Test
    void deletePostPostId() throws Exception {
        Integer postId = createPost(1, "test").getId();

        RequestBuilder request = MockMvcRequestBuilders.delete("/post/" + postId)
                .header("Authorization", "Bearer test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    void getPostAll() {
    }

    @Test
    void getPostAllUserId() {
    }

    @Test
    void postPost() {
    }

    @Test
    void putPostPostId() {
    }

    private Post createPost(Integer userId, String text) {
        Post post = new Post(userId, text);

        return postRepository.save(post);
    }
}