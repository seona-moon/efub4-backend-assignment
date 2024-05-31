package efub.assignment.community.account.domain;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static efub.assignment.community.account.domain.AccountStatus.REGISTERED;
import static efub.assignment.community.account.domain.AccountStatus.UNREGISTERED;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id",updatable = false)
    private Long accountId;

    @Column(nullable = false, length = 60)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = true, length = 16)
    private String nickname;

    @Column(nullable = false, length = 20)
    private String university;

    @Column(nullable = false)
    private String studentId;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    /* mappedBy : 연관관계의 주인 */
    /* cascade : 엔티티 삭제 시 연관된 엔티티의 처리 방식 */
    /* orphanRemoval : 고아 객체의 처리 방식 */
    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Account(String email, String password, String nickname, String university, String studentId){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.university = university;
        this.studentId = studentId;
        this.status = REGISTERED;
    }

    public void updateAccount(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void withdrawAccount(){
        this.status = AccountStatus.UNREGISTERED;
    }
}
