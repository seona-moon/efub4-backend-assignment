package efub.assignment.community.board.domain;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", updatable = false)
    private Long boardId;

    @ManyToOne
    @JoinColumn(name = "account_id", updatable = false)
    private Account account;

    @Column(nullable = false, length = 50)
    private String boardName;

    @Column(nullable = false, length = 1000)
    private String boardDescription;

    @Column(nullable = false, length = 1000)
    private String boardNotice;

    @Builder
    public Board(Account account, String boardName, String boardDescription, String boardNotice){
        this.account = account;
        this.boardName = boardName;
        this.boardDescription = boardDescription;
        this.boardNotice = boardNotice;
    }

}
