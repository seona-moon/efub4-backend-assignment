package efub.assignment.community.account;

import efub.assignment.community.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByNickname(String name);
}
