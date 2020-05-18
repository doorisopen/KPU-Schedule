package kpuapi.kpulecture.api;

import kpuapi.kpulecture.api.dto.PostsResponseDto;
import kpuapi.kpulecture.api.dto.PostsSaveRequestDto;
import kpuapi.kpulecture.api.dto.PostsUpdateRequestDto;
import kpuapi.kpulecture.domain.posts.Posts;
import kpuapi.kpulecture.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @GetMapping("/api/v1/posts")
    public Result findAll() {
        List<Posts> list = postsService.findAll();
        List<PostsResponseDto> resultList = list.stream()
                .map(o -> new PostsResponseDto(o))
                .collect(Collectors.toList());
        return new Result(HttpStatus.OK, resultList);
    }

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

}
