package efub.assignment.community.messageRoom.repository;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {


    Optional<MessageRoom> findByFirstSendAccountAndFirstReceiveAccount(Account account1, Account account2);

    List<MessageRoom> findAllByFirstSendAccount(Account account);

    List<MessageRoom> findAllByFirstReceiveAccount(Account account);

    Optional<MessageRoom> findMessageRoomByMessageRoomId(Long messageRoomId);

}
