package efub.assignment.community.comment.service;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.account.service.AccountService;
import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.repository.CommentRepository;
import efub.assignment.community.exception.CustomDeleteException;
import efub.assignment.community.exception.ErrorCode;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final AccountService accountService;
    private final PostService postService;
    private final PostService noticeService;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public Comment findCommentById(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new EntityNotFoundException("해당 id를 가진 comment를 찾을 수 없습니다. id="+commentId));
        return comment;
    }

    /* 댓글 생성 */
    public Comment saveComment(Long postId, CommentRequestDto requestDto) {
        Account writer = accountService.findAccountById(requestDto.getAccountId());
        Post post = postService.findPostById(postId);

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .writer(writer)
                .post(post)
                .build();
        commentRepository.save(comment);


        return comment;
    }

    public List<Comment> findPostCommentList(Long postId){
        Post post = postService.findPostById(postId);
        return commentRepository.findAllByPost(post);
    }

    /* 작성자의 댓글 목록 조회 */
    public List<Comment> findAccountCommentList(Account writer){
        return commentRepository.findAllByWriter(writer);
    }

    /* 댓글 수정 */
    public Comment updateComment(Long id,Long accountId, CommentRequestDto requestdto) {
        Comment comment = findCommentById(id);
        if(accountId!=comment.getWriter().getAccountId()){
            throw new CustomDeleteException(ErrorCode.PERMISSION_REJECTED_USER);
        }
        comment.updateComment(requestdto.getContent());
        return comment;
    }

    /* 댓글 삭제 */
    public void deleteComment(Long id, Long accountId){
        Comment comment = findCommentById(id);
        if(accountId!=comment.getWriter().getAccountId()){
            throw new CustomDeleteException(ErrorCode.PERMISSION_REJECTED_USER);
        }
        commentRepository.delete(comment);
    }

}
