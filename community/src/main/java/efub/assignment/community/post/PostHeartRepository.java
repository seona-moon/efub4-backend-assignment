package efub.assignment.community.post;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    Integer countByPost(Post post);

    List<PostHeart> findByWriter(Account account);

    boolean existsByWriterAndPost(Account account, Post post);

    Optional<PostHeart> findByWriterAndPost (Account account, Post post);

}
