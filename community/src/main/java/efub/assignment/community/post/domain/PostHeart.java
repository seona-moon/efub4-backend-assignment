package efub.assignment.community.post.domain;

import efub.assignment.community.account.domain.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class PostHeart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_heart_id")
    private Long id;

    // 각 post는 좋아요를 여러 개 가질 수 있음
    // 각 좋아요는 하나의 post에 속함
    // 양방향 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "게시글은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    // 유저는 post 좋아요를 여러 개 생성 가능
    // 각 post 좋아요는 한 명의 작성자를 가짐
    // Account에서는 PostHeart 객체를 사용하지 않지만, PostHeart는 'account_id'를 참조
    // -> 단방향 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "작성자는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "account_id", updatable = false)
    private Account writer;

    @Builder
    public PostHeart(Post post, Account account){
        this.post = post;
        this.writer = account;
    }
}
