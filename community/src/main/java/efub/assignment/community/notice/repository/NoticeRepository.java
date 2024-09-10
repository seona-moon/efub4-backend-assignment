package efub.assignment.community.notice.repository;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByAccount(Account account);
}
