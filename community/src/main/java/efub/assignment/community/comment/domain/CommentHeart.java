package efub.assignment.community.comment.domain;

import efub.assignment.community.account.domain.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentHeart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "댓글은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "comment_id", updatable = false)
    private Comment comment;

    // 유저는 댓글 좋아요를 여러 개 생성 가능
    // 각 댓글 좋아요는 한 명의 작성자를 가짐
    // Account에서는 CommentHeart 객체를 사용하지 않지만, CommentHeart는 'account_id'를 참조
    // -> 단방향 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "작성자는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "account_id", updatable = false)
    private Account writer;

    @Builder
    public CommentHeart(Comment comment, Account account){
        this.comment = comment;
        this.writer = account;
    }
}
