package kpuapi.kpulecture.domain.posts;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @Test
    public void 게시글저장_불러오기() throws Exception {
        //given
        String title = "테스트 제목";
        String content = "테스트 내용";

        postsRepository.save(Posts.builder()
                                .title(title)
                                .content(content)
                                .author("테스트 작성자")
                                .build());

        //when
        List<Posts> findPosts = postsRepository.findAll();

        //then
        assertThat(findPosts.get(0).getTitle()).isEqualTo(title);
        assertThat(findPosts.get(0).getContent()).isEqualTo(content);

    }

    @Test
    public void BaseTimeEntity_등록() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2020,05,16,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> findPosts = postsRepository.findAll();

        //then
        Posts posts = findPosts.get(0);
        System.out.println("CreatedDate= " + posts.getCreatedDate() + " ModifiedDate= " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);

    }

}