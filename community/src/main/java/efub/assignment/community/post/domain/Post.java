package efub.assignment.community.post.domain;


import efub.assignment.community.account.domain.Account;
import efub.assignment.community.board.domain.Board;
import efub.assignment.community.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id",updatable = false)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "account_id",updatable = false)  //외래키 매핑
    private Account account;

    @ManyToOne
    @JoinColumn(name = "board_id",updatable = false)  //외래키 매핑
    private Board board;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false, length = 10)
    private String writerOpen;

    @Builder
    public Post(Account account, Board board, String title, String content, String writerOpen){
        this.account = account;
        this.board = board;
        this.title = title;
        this.content = content;
        this.writerOpen = writerOpen;
    }
}
