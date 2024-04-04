package efub.assignment.community.account.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public Account(String email, String password, String nickname, String university, String studentId){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.university = university;
        this.studentId = studentId;
        this.status = REGISTERED;
    }

    public void updateAccount(String email, String nickname, String password, String university, String studentId) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.university = university;
        this.studentId = studentId;
    }

    public void withdrawAccount(){
        this.status = AccountStatus.UNREGISTERED;
    }
}
