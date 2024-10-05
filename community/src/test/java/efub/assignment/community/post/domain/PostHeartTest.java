package efub.assignment.community.post.domain;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.board.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class PostHeartTest {

    @Autowired
    private EntityManager entityManager;


    @Test
    @DisplayName("PostHeart 객체 생성 테스트")
    void createPostHeart_Success() {
        // given
        Account account = Account.builder()
                .email("user@domain.com")
                .password("password123")
                .nickname("nickname")
                .university("Test University")
                .studentId("123456")
                .build();

        Board board = Board.builder()
                .account(account)
                .boardName("Test Board")
                .boardDescription("A board for testing")
                .boardNotice("This is a test notice")
                .build();

        Post post = Post.builder()
                .account(account)
                .board(board)
                .title("Test Title")
                .content("This is the content of the post.")
                .writerOpen("PUBLIC")
                .build();

        // when
        PostHeart postHeart = PostHeart.builder()
                .post(post)
                .account(account)
                .build();

        // then
        assertThat(postHeart.getId()).isNull(); // 아직 저장되지 않았으므로 ID는 null
        assertThat(postHeart.getPost()).isEqualTo(post);
        assertThat(postHeart.getWriter()).isEqualTo(account);
    }

    @Test
    @DisplayName("PostHeart 객체 생성 시 writer가 null인 경우 실패")
    void createPostHeart_Failure_NullWriter() {
        // given
        Board board = Board.builder()
                .account(Account.builder()
                        .email("user@domain.com")
                        .password("password123")
                        .nickname("nickname")
                        .university("Test University")
                        .studentId("123456")
                        .build())
                .boardName("Test Board")
                .boardDescription("A board for testing")
                .boardNotice("This is a test notice")
                .build();

        Post post = Post.builder()
                .account(board.getAccount())
                .board(board)
                .title("Test Title")
                .content("This is the content of the post.")
                .writerOpen("PUBLIC")
                .build();

        // when & then
        PostHeart postHeart = PostHeart.builder()
                .post(post)
                .account(null) // writer를 null로 설정
                .build();

        assertThatThrownBy(() -> {
            entityManager.persist(post);
            entityManager.persist(postHeart);
            entityManager.flush(); // flush로 강제 유효성 검증 수행
        }).isInstanceOf(ConstraintViolationException.class);
    }
}
