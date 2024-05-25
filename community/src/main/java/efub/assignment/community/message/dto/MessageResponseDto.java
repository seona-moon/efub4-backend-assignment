package efub.assignment.community.message.dto;

import efub.assignment.community.message.domain.Message;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.dto.MessageRoomResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MessageResponseDto {
    private Long messageRoomId;
    private Long sendAccountId;
    private String content;
    private LocalDateTime createdDate;

    public static MessageResponseDto from(Message message){
        return MessageResponseDto.builder()
                .messageRoomId(message.getMessageRoom().getMessageRoomId())
                .sendAccountId(message.getSendAccount().getAccountId())
                .content(message.getContent())
                .createdDate(message.getCreatedDate())
                .build();
    }
}
