package efub.assignment.community.account.domain;

import efub.assignment.community.account.AccountRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class AccountTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("Account 생성 성공")
    void createAccount_Success() {
        // given
        Account account = Account.builder()
                .email("testuser@domain.com")
                .password("password123")
                .nickname("testuser")
                .university("Test University")
                .studentId("12345678")
                .build();

        // when
        Account savedAccount = accountRepository.save(account);

        // then
        assertThat(savedAccount.getAccountId()).isNotNull();
        assertThat(savedAccount.getEmail()).isEqualTo("testuser@domain.com");
        assertThat(savedAccount.getNickname()).isEqualTo("testuser");
        assertThat(savedAccount.getStatus()).isEqualTo(AccountStatus.REGISTERED);
    }

    @Test
    @DisplayName("Account 정보 업데이트")
    void updateAccount_Success() {
        // given
        Account account = Account.builder()
                .email("testuser@domain.com")
                .password("password123")
                .nickname("testuser")
                .university("Test University")
                .studentId("12345678")
                .build();
        Account savedAccount = accountRepository.save(account);

        // when
        savedAccount.updateAccount("newemail@domain.com", "newnickname", "newpassword123");
        Account updatedAccount = accountRepository.save(savedAccount);

        // then
        assertThat(updatedAccount.getEmail()).isEqualTo("newemail@domain.com");
        assertThat(updatedAccount.getNickname()).isEqualTo("newnickname");
        assertThat(updatedAccount.getPassword()).isEqualTo("newpassword123");
    }

    @Test
    @DisplayName("Account 탈퇴 처리")
    void withdrawAccount_Success() {
        // given
        Account account = Account.builder()
                .email("testuser@domain.com")
                .password("password123")
                .nickname("testuser")
                .university("Test University")
                .studentId("12345678")
                .build();
        Account savedAccount = accountRepository.save(account);

        // when
        savedAccount.withdrawAccount();
        Account withdrawnAccount = accountRepository.save(savedAccount);

        // then
        assertThat(withdrawnAccount.getStatus()).isEqualTo(AccountStatus.UNREGISTERED);
    }

    @Test
    @DisplayName("이메일이 null인 경우 Account 생성에 실패")
    void createAccount_Failure_NullEmail() {
        // given
        Account account = Account.builder()
                .email(null)  // 유효하지 않은 값
                .password("password123")
                .nickname("testuser")
                .university("Test University")
                .studentId("12345678")
                .build();

        // when & then
        assertThatThrownBy(() -> accountRepository.save(account))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("닉네임이 빈 문자열인 경우 Account 생성 실패")
    void createAccount_Failure_EmptyNickname() {
        // given
        Account account = Account.builder()
                .email("testuser@domain.com")
                .password("password123")
                .nickname("")  // 유효하지 않은 값
                .university("Test University")
                .studentId("12345678")
                .build();

        // when & then
        assertThatThrownBy(() -> accountRepository.save(account))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
