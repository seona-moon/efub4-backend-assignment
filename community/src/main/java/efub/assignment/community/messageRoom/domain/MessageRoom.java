package efub.assignment.community.messageRoom.domain;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MessageRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageRoom_id", updatable = false)
    private Long messageRoomId;


    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "send_account_id", updatable = false)
    private Account firstSendAccount;
    

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "receive_account_id", updatable = false)
    private Account firstReceiveAccount;


    //쪽지가 시작된 글
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "post_id", updatable = false)
    private Post startPost;

    // mappedBy : 연관관계의 주인
    // 쪽지 리스트
    @OneToMany(mappedBy = "messageRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messageList = new ArrayList<>();


    public MessageRoom(Account sendAccount, Account receiveAccount, String firstContent, Post startPost){
        this.firstSendAccount = sendAccount;
        this.firstReceiveAccount = receiveAccount;
        //this.firstContent = firstContent;
        this.startPost = startPost;
    }

}
