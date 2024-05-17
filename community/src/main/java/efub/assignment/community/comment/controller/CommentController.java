package efub.assignment.community.comment.controller;


import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.AccountInfoRequestDto;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.dto.CommentResponseDto;
import efub.assignment.community.comment.service.CommentHeartService;
import efub.assignment.community.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments/{commentId}")
public class CommentController {

    private final CommentService commentService;
    private final CommentHeartService commentHeartService;

    // 댓글 수정
    @PutMapping
    public CommentResponseDto updateComment(@PathVariable(name = "commentId") Long id,
                                            @RequestBody @Valid final CommentRequestDto requestdto,
                                            @RequestParam(name = "accountId") Long accountId){
        Comment comment = commentService.updateComment(id, accountId, requestdto);
        return CommentResponseDto.of(comment);
    }

    // 댓글 삭제
    @DeleteMapping
    public String deleteComment(@PathVariable(name = "commentId") Long id,
                                @RequestParam(name = "accountId") Long accountId){
        commentService.deleteComment(id, accountId);

        return "성공적으로 삭제되었습니다.";
    }

    // 댓글 좋아요 등록
    @PostMapping("/hearts")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String createCommentLike(@PathVariable(name = "commentId") final Long commentId,
                                    @RequestBody final AccountInfoRequestDto requestDto){
        commentHeartService.create(commentId, requestDto);
        return "좋아요를 눌렀습니다.";
    }

    // 댓글 좋아요 삭제
    @DeleteMapping("/hearts")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteCommentLike(@PathVariable(name = "commentId") final Long commentId,
                                    @RequestParam(name = "accountId") final Long accountId){
        commentHeartService.delete(commentId, accountId);
        return "좋아요가 취소되었습니다.";
    }
}
