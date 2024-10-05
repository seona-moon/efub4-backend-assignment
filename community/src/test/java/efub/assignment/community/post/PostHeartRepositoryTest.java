package efub.assignment.community.post;

import efub.assignment.community.account.AccountRepository;
import efub.assignment.community.account.domain.Account;
import efub.assignment.community.board.BoardRepository;
import efub.assignment.community.board.domain.Board;
import efub.assignment.community.post.PostHeartRepository;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.domain.PostHeart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostHeartRepositoryTest {

    @Autowired
    private PostHeartRepository postHeartRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("특정 Post에 대한 좋아요 개수 확인")
    void countByPost_Success() {
        // given
        Account account = Account.builder()
                .email("user@domain.com")
                .password("password123")
                .nickname("nickname")
                .university("Test University")
                .studentId("123456")
                .build();

        // 먼저 account를 영속화합니다.
        accountRepository.save(account);

        Board board = Board.builder()
                .account(account)
                .boardName("Test Board")
                .boardDescription("A board for testing")
                .boardNotice("This is a test notice")
                .build();

        boardRepository.save(board);

        Post post = Post.builder()
                .account(account)
                .board(board)
                .title("Test Title")
                .content("This is the content of the post.")
                .writerOpen("PUBLIC")
                .build();

        // post도 영속화합니다.
        postRepository.save(post);

        PostHeart postHeart1 = PostHeart.builder()
                .post(post)
                .account(account)
                .build();

        postHeartRepository.save(postHeart1);

        // when
        Integer count = postHeartRepository.countByPost(post);

        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("작성자로 좋아요 조회")
    void findByWriter_Success() {
        // given
        Account account = Account.builder()
                .email("user@domain.com")
                .password("password123")
                .nickname("nickname")
                .university("Test University")
                .studentId("123456")
                .build();

        // 먼저 account를 영속화합니다.
        accountRepository.save(account);

        Board board = Board.builder()
                .account(account)
                .boardName("Test Board")
                .boardDescription("A board for testing")
                .boardNotice("This is a test notice")
                .build();

        boardRepository.save(board);

        Post post = Post.builder()
                .account(account)
                .board(board)
                .title("Test Title")
                .content("This is the content of the post.")
                .writerOpen("PUBLIC")
                .build();

        // post도 영속화합니다.
        postRepository.save(post);

        PostHeart postHeart1 = PostHeart.builder()
                .post(post)
                .account(account)
                .build();

        postHeartRepository.save(postHeart1);

        // when
        List<PostHeart> postHearts = postHeartRepository.findByWriter(account);

        // then
        assertThat(postHearts).isNotEmpty();
        assertThat(postHearts.get(0).getWriter()).isEqualTo(account);
    }

    @Test
    @DisplayName("작성자와 Post로 좋아요 존재 여부 확인")
    void existsByWriterAndPost_Success() {
        // given
        Account account = Account.builder()
                .email("user@domain.com")
                .password("password123")
                .nickname("nickname")
                .university("Test University")
                .studentId("123456")
                .build();

        // 먼저 account를 영속화합니다.
        accountRepository.save(account);

        Board board = Board.builder()
                .account(account)
                .boardName("Test Board")
                .boardDescription("A board for testing")
                .boardNotice("This is a test notice")
                .build();

        boardRepository.save(board);

        Post post = Post.builder()
                .account(account)
                .board(board)
                .title("Test Title")
                .content("This is the content of the post.")
                .writerOpen("PUBLIC")
                .build();

        // post도 영속화합니다.
        postRepository.save(post);

        PostHeart postHeart = PostHeart.builder()
                .post(post)
                .account(account)
                .build();

        postHeartRepository.save(postHeart);

        // when
        boolean exists = postHeartRepository.existsByWriterAndPost(account, post);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("작성자와 Post로 좋아요 조회")
    void findByWriterAndPost_Success() {
        // given
        Account account = Account.builder()
                .email("user@domain.com")
                .password("password123")
                .nickname("nickname")
                .university("Test University")
                .studentId("123456")
                .build();

        // 먼저 account를 영속화합니다.
        accountRepository.save(account);

        Board board = Board.builder()
                .account(account)
                .boardName("Test Board")
                .boardDescription("A board for testing")
                .boardNotice("This is a test notice")
                .build();

        boardRepository.save(board);

        Post post = Post.builder()
                .account(account)
                .board(board)
                .title("Test Title")
                .content("This is the content of the post.")
                .writerOpen("PUBLIC")
                .build();

        // post도 영속화합니다.
        postRepository.save(post);

        PostHeart postHeart = PostHeart.builder()
                .post(post)
                .account(account)
                .build();

        postHeartRepository.save(postHeart);

        // when
        Optional<PostHeart> foundPostHeart = postHeartRepository.findByWriterAndPost(account, post);

        // then
        assertThat(foundPostHeart).isPresent();
        assertThat(foundPostHeart.get().getPost()).isEqualTo(post);
        assertThat(foundPostHeart.get().getWriter()).isEqualTo(account);
    }
}
