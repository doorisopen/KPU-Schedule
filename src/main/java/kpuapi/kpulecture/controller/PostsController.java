package kpuapi.kpulecture.controller;

import kpuapi.kpulecture.api.dto.PostsResponseDto;
import kpuapi.kpulecture.api.dto.PostsSaveRequestDto;
import kpuapi.kpulecture.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class PostsController {

    private final PostsService postsService;

    @GetMapping("/posts")
    public String postsList(Model model) {
        model.addAttribute("posts", postsService.findAll());
        return "posts/postsList";
    }

    @GetMapping("/posts/new")
    public String createForm(Model model) {
        model.addAttribute("form", new PostsSaveRequestDto());
        return "posts/createPostsForm";
    }


    @GetMapping("/posts/{id}/edit")
    public String updateForm(@PathVariable Long id, Model model) {
        PostsResponseDto form = postsService.findById(id);
        model.addAttribute("form", form);

        return "posts/updatePostsForm";
    }

}
