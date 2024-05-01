package efub.assignment.community.comment.service;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.account.service.AccountService;
import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.repository.CommentRepository;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
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
    private final CommentRepository commentRepository;

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
}
