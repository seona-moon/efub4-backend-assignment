package efub.assignment.community.account;
import efub.assignment.community.account.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("이메일로 Account 존재 여부 확인")
    void existsByEmail_Success() {
        // given
        String email = "testuser@domain.com";
        Account account = Account.builder()
                .email(email)
                .password("password123")
                .nickname("testuser")
                .university("Test University")
                .studentId("12345678")
                .build();
        accountRepository.save(account);

        // when
        Boolean exists = accountRepository.existsByEmail(email);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("닉네임으로 Account 존재 여부 확인")
    void existsByNickname_Success() {
        // given
        String nickname = "testuser";
        Account account = Account.builder()
                .email("testuser@domain.com")
                .password("password123")
                .nickname(nickname)
                .university("Test University")
                .studentId("12345678")
                .build();
        accountRepository.save(account);

        // when
        Boolean exists = accountRepository.existsByNickname(nickname);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("이메일로 Account 조회 성공")
    void findByEmail_Success() {
        // given
        String email = "testuser@domain.com";
        Account account = Account.builder()
                .email(email)
                .password("password123")
                .nickname("testuser")
                .university("Test University")
                .studentId("12345678")
                .build();
        accountRepository.save(account);

        // when
        Optional<Account> foundAccount = accountRepository.findByEmail(email);

        // then
        assertThat(foundAccount).isPresent();
        assertThat(foundAccount.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("닉네임으로 Account 조회 성공")
    void findByNickname_Success() {
        // given
        String nickname = "testuser";
        Account account = Account.builder()
                .email("testuser@domain.com")
                .password("password123")
                .nickname(nickname)
                .university("Test University")
                .studentId("12345678")
                .build();
        accountRepository.save(account);

        // when
        Optional<Account> foundAccount = accountRepository.findByNickname(nickname);

        // then
        assertThat(foundAccount).isPresent();
        assertThat(foundAccount.get().getNickname()).isEqualTo(nickname);
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 Account 존재 확인 실패")
    void existsByEmail_Failure() {
        // when
        Boolean exists = accountRepository.existsByEmail("nonexistent@domain.com");

        // then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("존재하지 않는 닉네임으로 Account 존재 확인 실패")
    void existsByNickname_Failure() {
        // when
        Boolean exists = accountRepository.existsByNickname("nonexistent");

        // then
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 Account 조회 실패")
    void findByEmail_Failure() {
        // when
        Optional<Account> foundAccount = accountRepository.findByEmail("nonexistent@domain.com");

        // then
        assertThat(foundAccount).isNotPresent();
    }

    @Test
    @DisplayName("존재하지 않는 닉네임으로 Account 조회 실패")
    void findByNickname_Failure() {
        // when
        Optional<Account> foundAccount = accountRepository.findByNickname("nonexistent");

        // then
        assertThat(foundAccount).isNotPresent();
    }
}
