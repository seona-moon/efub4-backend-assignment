package efub.assignment.community.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountUpdateRequestDto {

    @NotBlank(message = "이메일은 필수로 입력해야합니다.")
    @Email(message = "유효하지 않은 이메일 형식입니다.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    private String email;

    @NotBlank(message = "비밀번호는 필수로 입력해야합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!.?,])[A-Za-z\\d!.?,]{2,16}$",
            message = "16자 이내의 영문자 및 숫자와 ?,!,.,, 특수문자로 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임은 필수로 입력해야합니다.")  // 닉네임은 필수로 입력
    private String nickname;

    // 학교와 학번은 수정 불가하게 구현
    // private String university;
    // private String studentId;

    @Builder
    public AccountUpdateRequestDto(String email, String nickname, String password){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
