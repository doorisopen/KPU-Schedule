package kpuapi.kpulecture.domain.posts;

import kpuapi.kpulecture.domain.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
}
