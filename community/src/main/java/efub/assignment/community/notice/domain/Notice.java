package efub.assignment.community.notice.domain;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", updatable = false)
    private Long noticeId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = true)
    private String boardName;

    @Builder
    public Notice(String type, String content, String boardName, Account account){
        this.account = account;
        this.type = type;
        this.content = content;
        this.boardName = boardName;
    }

}
