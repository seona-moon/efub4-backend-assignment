package efub.assignment.community.post.domain;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.board.domain.Board;
import efub.assignment.community.post.dto.PostUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostTest {

    @Test
    @DisplayName("Post 객체 생성 테스트")
    void createPost_Success() {
        // given
        Account account = Account.builder()
                .email("user@domain.com")
                .password("password123")
                .nickname("nickname")
                .university("Test University")
                .studentId("123456")
                .build();

        // Board 엔티티의 account도 설정
        Board board = Board.builder()
                .account(account)
                .boardName("Test Board")
                .boardDescription("A board for testing")
                .boardNotice("This is a test notice")
                .build();

        // when
        Post post = Post.builder()
                .account(account)
                .board(board)
                .title("Test Title")
                .content("This is the content of the post.")
                .writerOpen("PUBLIC")
                .build();

        // then
        assertThat(post.getPostId()).isNull(); // 아직 저장되지 않았으므로 ID는 null
        assertThat(post.getAccount()).isEqualTo(account);
        assertThat(post.getBoard()).isEqualTo(board);
        assertThat(post.getTitle()).isEqualTo("Test Title");
        assertThat(post.getContent()).isEqualTo("This is the content of the post.");
        assertThat(post.getWriterOpen()).isEqualTo("PUBLIC");
        assertThat(post.getCommentList()).isEmpty();
        assertThat(post.getPostHeartList()).isEmpty();
    }

    @Test
    @DisplayName("Post 객체 업데이트 테스트")
    void updatePost_Success() {
        // given
        Account account = Account.builder()
                .email("user@domain.com")
                .password("password123")
                .nickname("nickname")
                .university("Test University")
                .studentId("123456")
                .build();

        // Board 엔티티의 account도 설정
        Board board = Board.builder()
                .account(account)
                .boardName("Test Board")
                .boardDescription("A board for testing")
                .boardNotice("This is a test notice")
                .build();

        Post post = Post.builder()
                .account(account)
                .board(board)
                .title("Original Title")
                .content("Original content.")
                .writerOpen("PUBLIC")
                .build();

        PostUpdateDto updateDto = new PostUpdateDto("Updated Title", "Updated content.");

        // when
        post.update(updateDto);

        // then
        assertThat(post.getTitle()).isEqualTo("Updated Title");
        assertThat(post.getContent()).isEqualTo("Updated content.");
    }
}
