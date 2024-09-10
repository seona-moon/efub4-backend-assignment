package efub.assignment.community.post.controller;


import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.dto.CommentResponseDto;
import efub.assignment.community.comment.service.CommentService;
import efub.assignment.community.notice.domain.Notice;
import efub.assignment.community.notice.service.NoticeService;
import efub.assignment.community.post.dto.PostCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class PostCommentController {

    private final CommentService commentService;
    private final NoticeService noticeService;

    /* 게시글에 댓글 생성 */
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable("postId") Long postId,
                                                            @RequestBody CommentRequestDto requestDto){
        Comment comment = commentService.saveComment(postId, requestDto);
        //댓글 알림 생성
        noticeService.createCommentNotice(postId, comment.getContent());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommentResponseDto.of(comment));
    }

    /* 게시글의 댓글 목록 조회 */
    @GetMapping
    public ResponseEntity<PostCommentResponseDto> getPostCommentList(@PathVariable("postId") Long postId){
        List<Comment> commentList = commentService.findPostCommentList(postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(PostCommentResponseDto.of(postId, commentList));
    }
}
