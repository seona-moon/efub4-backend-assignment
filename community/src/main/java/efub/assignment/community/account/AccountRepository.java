package efub.assignment.community.account;

import efub.assignment.community.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Boolean existsByEmail(String email);

    Boolean existsByNickname(String nickname);

    Account findByEmail(String email);

    Account findByNickname(String name);
}
