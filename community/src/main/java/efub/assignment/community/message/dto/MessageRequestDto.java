package efub.assignment.community.message.dto;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.board.domain.Board;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MessageRequestDto {

    @NotNull(message = "messageRoomId는 필수입니다.")
    private Long messageRoomId;

    @NotNull(message = "보낸사람 accountId는 필수입니다.")
    private Long sendAccountId;

    @NotBlank(message = "message 내용은 필수입니다.")
    private String content;

    public static Message toEntity(MessageRoom messageRoom, Account account, String content){
        return Message.builder()
                .sendAccount(account)
                .messageRoom(messageRoom)
                .content(content)
                .build();
    }
}
