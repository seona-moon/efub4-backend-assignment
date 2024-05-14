package efub.assignment.community.comment.controller;


import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.dto.CommentResponseDto;
import efub.assignment.community.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments/{commentId}")
public class CommentController {

    private final CommentService commentService;

    @PutMapping
    public CommentResponseDto updateComment(@PathVariable(name = "commentId") Long id,
                                            @RequestBody @Valid final CommentRequestDto requestdto,
                                            @RequestParam(name = "accountId") Long accountId){
        Comment comment = commentService.updateComment(id, accountId, requestdto);
        return CommentResponseDto.of(comment);
    }

    @DeleteMapping
    public String deleteComment(@PathVariable(name = "commentId") Long id,
                                @RequestParam(name = "accountId") Long accountId){
        commentService.deleteComment(id, accountId);

        return "성공적으로 삭제되었습니다.";
    }

}
