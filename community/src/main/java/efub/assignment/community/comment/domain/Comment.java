package efub.assignment.community.comment.domain;


import efub.assignment.community.account.domain.Account;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) /* 지연 로딩을 명시함 */
    @JoinColumn(name = "account_id", updatable = false)  /* FK 칼럼 지정 */
    private Account writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CommentHeart> commentLikeList = new ArrayList<>();

    @Builder
    public Comment(String content, Account writer, Post post){
        this.content = content;
        this.writer = writer;
        this.post = post;
    }

    public void updateComment(String content){
        this.content = content;
    }
}
