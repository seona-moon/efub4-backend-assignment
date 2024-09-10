package efub.assignment.community.account.dto;

import efub.assignment.community.account.domain.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDto {
    @NotBlank(message = "이메일은 필수입니다.")
      @Email(message = "유효하지 않은 이메일 형식입니다.")
      @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
      @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!.?,])[A-Za-z\\d!.?,]{2,16}$",
              message = "16자 이내의 영문자 및 숫자와 ?,!,.,, 특수문자로 입력해주세요.")
    private String password;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @NotBlank(message = "학교는 필수입니다.")
    private String university;

    @NotBlank(message = "학번은 필수입니다.")
    private String studentId;

    @Builder
    public SignUpRequestDto(String email, String password, String nickname, String university, String studentId){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.university = university;
        this.studentId = studentId;
    }

    public Account toEntity(){
        return Account.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .university(this.university)
                .studentId(this.studentId)
                .build();
    }
}
