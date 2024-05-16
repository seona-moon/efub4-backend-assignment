package efub.assignment.community.comment.repository;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.domain.CommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {

    Integer countByComment(Comment comment);
    List<CommentHeart> findByWriter(Account account);
    boolean existsByWriterAndComment(Account account, Comment comment);

    Optional<CommentHeart> findByWriterAndComment(Account account, Comment comment);
}
