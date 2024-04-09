package efub.assignment.community.account.dto;

import efub.assignment.community.account.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountResponseDto {
    private Long accountId;
    private String email;
    private String nickname;
    private String university;
    private String studentId;

    public AccountResponseDto(Long accountId, String email, String nickname, String university, String studentId){
        this.accountId = accountId;
        this.email = email;
        this.nickname = nickname;
        this.university = university;
        this.studentId = studentId;
    }

    public static AccountResponseDto from(Account account){
        return new AccountResponseDto(account.getAccountId(),
                account.getEmail(),
                account.getNickname(),
                account.getUniversity(),
                account.getStudentId());
    }
}
