package efub.assignment.community.message.domain;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;


    @Column(length = 1000)
    private String content;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "send_account_id", updatable = false)
    private Account sendAccount;

    // messageRoom과 연관관계 설정
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "messageRoom_id", updatable = false)
    private MessageRoom messageRoom;

    @Builder
    public Message(String content, Account sendAccount, MessageRoom messageRoom){
        this.content = content;
        this.sendAccount = sendAccount;
        this.messageRoom = messageRoom;
    }
}
