package efub.assignment.community.comment.repository;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByWriter(Account account);
}
