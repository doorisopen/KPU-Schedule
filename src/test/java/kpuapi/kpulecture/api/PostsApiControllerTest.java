//package kpuapi.kpulecture.api;
//
//import kpuapi.kpulecture.api.dto.PostsSaveRequestDto;
//import kpuapi.kpulecture.api.dto.PostsUpdateRequestDto;
//import kpuapi.kpulecture.domain.posts.Posts;
//import kpuapi.kpulecture.domain.posts.PostsRepository;
//import org.junit.After;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class PostsApiControllerTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    PostsRepository postsRepository;
//
//    @After
//    public void tearDown() throws Exception {
//        postsRepository.deleteAll();
//    }
//
//    @Test
//    public void Posts_등록된다() throws Exception {
//        //given
//        String title = "title";
//        String content = "content";
//        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
//                .title(title)
//                .content(content)
//                .author("author")
//                .build();
//        String url = "http://localhost:" + port + "/api/v1/posts";
//
//        //when
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
//        List<Posts> findPosts = postsRepository.findAll();
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//        assertThat(findPosts.get(0).getTitle()).isEqualTo(title);
//        assertThat(findPosts.get(0).getContent()).isEqualTo(content);
//
//    }
//
//    @Test
//    public void Posts_수정된다() throws Exception {
//        //given
//        Posts savedPosts = postsRepository.save(Posts.builder()
//                .title("old title")
//                .content("old content")
//                .author("author")
//                .build());
//
//        Long updateId = savedPosts.getId();
//        String expectedTitle = "new title";
//        String expectedContent = "new content";
//
//        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
//                .title(expectedTitle)
//                .content(expectedContent)
//                .build();
//
//        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
//
//        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
//
//        //when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
//        List<Posts> findPosts = postsRepository.findAll();
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//        assertThat(findPosts.get(0).getTitle()).isEqualTo(expectedTitle);
//        assertThat(findPosts.get(0).getContent()).isEqualTo(expectedContent);
//    }
//
//}