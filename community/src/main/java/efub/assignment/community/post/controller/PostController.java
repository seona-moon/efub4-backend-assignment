package efub.assignment.community.post.controller;


import efub.assignment.community.board.service.BoardService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.AllPostsResponseDto;
import efub.assignment.community.post.dto.PostRequestDto;
import efub.assignment.community.post.dto.PostResponseDto;
import efub.assignment.community.post.service.PostService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResponseDto createNewPost(@RequestBody @Valid final PostRequestDto dto){
        Post savedPost = postService.createNewPost(dto);
        return PostResponseDto.from(savedPost,savedPost.getAccount().getNickname(),savedPost.getBoard().getBoardId());
    }

    @GetMapping("/boards/{board_id}/posts")
    @ResponseStatus(value = HttpStatus.OK)
    public AllPostsResponseDto getAllPost(@PathVariable(name="board_id")Long board_id){

        List<PostResponseDto> list = new ArrayList<>();
        List<Post> posts = postService.findAllPosts();
        //posts.stream().forEach(post -> { //posts리스트를 스트림으로 변환 후, 각 글에 대해
        //    PostResponseDto dto = PostResponseDto.from(post, post.getAccount().getNickname(), post.getBoard().getBoardId());
        //    list.add(dto);
        //});

        for (Post post : posts){
            PostResponseDto dto = PostResponseDto.from(post, post.getAccount().getNickname(), post.getBoard().getBoardId());
            list.add(dto);
        }
        long count = postService.countAllPosts();
        return new AllPostsResponseDto(list,count);
    }

    @GetMapping("/posts/{post_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto getOnePost(@PathVariable(name="post_id")Long post_id){
        Post post = postService.findPostById(post_id);
        return PostResponseDto.from(post, post.getAccount().getNickname(), post.getBoard().getBoardId());
    }


}
