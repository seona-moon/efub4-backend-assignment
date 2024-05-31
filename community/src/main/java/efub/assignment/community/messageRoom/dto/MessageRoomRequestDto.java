package efub.assignment.community.messageRoom.dto;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MessageRoomRequestDto {
    @NotNull(message = "처음 보낸 사람 accountId는 필수입니다.")
    private Long firstSendAccountId;

    @NotNull(message = "처음 받는 사람 accountId는 필수입니다.")
    private Long firstReceiveAccountId;

    @NotBlank(message = "첫 쪽지 내용은 필수입니다.")
    private String firstContent;

    @NotNull(message = "시작된 글 postId는 필수입니다.")
    private Long startPostId;

    public MessageRoom toEntity(Account sendAccount, Account receiveAccount, Post post) {
        return MessageRoom.builder()
                .firstSendAccount(sendAccount)
                .firstReceiveAccount(receiveAccount)
                .startPost(post)
                .build();
    }
}
