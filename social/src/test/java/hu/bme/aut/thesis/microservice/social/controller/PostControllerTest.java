package hu.bme.aut.thesis.microservice.social.controller;

import hu.bme.aut.thesis.microservice.social.model.Friendship;
import hu.bme.aut.thesis.microservice.social.model.Post;
import hu.bme.aut.thesis.microservice.social.models.NewPostDto;
import hu.bme.aut.thesis.microservice.social.models.UserDetailsDto;
import hu.bme.aut.thesis.microservice.social.repository.FriendshipRepository;
import hu.bme.aut.thesis.microservice.social.repository.PostRepository;
import hu.bme.aut.thesis.microservice.social.util.MockUserAuthService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static hu.bme.aut.thesis.microservice.social.util.TestHelper.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Autowired
    FriendshipRepository friendshipRepository;

    static Integer LOGGEDIN_USER_ID = 1;
    static Integer OTHER_USER_ID = 2;
    static Integer THIRD_USER_ID = 3;

    @BeforeEach
    void setup() {
        UserDetailsDto userDetailsDto = MockUserAuthService.getUserDetailsDto();
        userDetailsDto.setId(LOGGEDIN_USER_ID);
        MockUserAuthService.setUserDetailsDto(userDetailsDto);
    }

    @AfterEach
    void cleanup() {
        postRepository.deleteAll();
        friendshipRepository.deleteAll();
    }

    @Test
    void deletePostByPostId() throws Exception {
        Integer postId = createPost(LOGGEDIN_USER_ID, "test").getId();

        RequestBuilder request = MockMvcRequestBuilders.delete("/post/" + postId)
                .header("Authorization", "Bearer test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    void deleteOtherUsersPost() throws Exception {
        Integer postId = createPost(OTHER_USER_ID, "test").getId();

        RequestBuilder request = MockMvcRequestBuilders.delete("/post/" + postId)
                .header("Authorization", "Bearer test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    void deleteNotExistingPost() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/post/" + Integer.MAX_VALUE)
                .header("Authorization", "Bearer test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    void getPostAll() throws Exception {
        Friendship friendship = new Friendship(LOGGEDIN_USER_ID, OTHER_USER_ID);
        friendship.setPending(false);
        friendshipRepository.save(friendship);

        createPost(LOGGEDIN_USER_ID, "user1 post");
        createPost(OTHER_USER_ID, "user2 post");
        createPost(THIRD_USER_ID, "user3 post");

        RequestBuilder request = MockMvcRequestBuilders.get("/post/all")
                .header("Authorization", "Bearer test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void getPostsByOwnUserId() throws Exception {
        createPost(LOGGEDIN_USER_ID, "user1 post");
        createPost(LOGGEDIN_USER_ID, "user2 post");

        RequestBuilder request = MockMvcRequestBuilders.get("/post/all/" + LOGGEDIN_USER_ID)
                .header("Authorization", "Bearer test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void getPostsByOtherUserId() throws Exception {
        Friendship friendship = new Friendship(LOGGEDIN_USER_ID, 2);
        friendship.setPending(false);
        friendshipRepository.save(friendship);

        createPost(OTHER_USER_ID, "user2 post1");
        createPost(OTHER_USER_ID, "user2 post2");

        RequestBuilder request = MockMvcRequestBuilders.get("/post/all/" + OTHER_USER_ID)
                .header("Authorization", "Bearer test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void getPostsByNotFriendUserId() throws Exception {
        createPost(OTHER_USER_ID, "user2 post1");
        createPost(OTHER_USER_ID, "user2 post2");

        RequestBuilder request = MockMvcRequestBuilders.get("/post/all/" + OTHER_USER_ID)
                .header("Authorization", "Bearer test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    @Test
    void postPost() throws Exception {
        NewPostDto newPostDto = new NewPostDto();
        newPostDto.setText("asdasd");
        newPostDto.setMedia(false);

        RequestBuilder request = MockMvcRequestBuilders.post("/post")
                .header("Authorization", "Bearer test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(newPostDto));


        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("asdasd"))
                .andExpect(jsonPath("$.user.id").value(LOGGEDIN_USER_ID))
                .andExpect(jsonPath("$.mediaName").doesNotExist());
    }

    @Test
    void putPostPostId() throws Exception {
        Post post = createPost(LOGGEDIN_USER_ID, "user1 post");

        NewPostDto newPostDto = new NewPostDto();
        newPostDto.setText("asdasd");

        RequestBuilder request = MockMvcRequestBuilders.put("/post/" + post.getId())
                .header("Authorization", "Bearer test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(newPostDto));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("asdasd"))
                .andExpect(jsonPath("$.user.id").value(LOGGEDIN_USER_ID))
                .andExpect(jsonPath("$.mediaName").doesNotExist());
    }

    @Test
    void putPostOtherUserPostId() throws Exception {
        Post post = createPost(OTHER_USER_ID, "user1 post");

        NewPostDto newPostDto = new NewPostDto();
        newPostDto.setText("asdasd");

        RequestBuilder request = MockMvcRequestBuilders.put("/post/" + post.getId())
                .header("Authorization", "Bearer test")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(newPostDto));

        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    private Post createPost(Integer userId, String text) {
        Post post = new Post(userId, text);

        return postRepository.save(post);
    }
}