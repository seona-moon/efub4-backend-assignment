package efub.assignment.community.messageRoom.dto;

import efub.assignment.community.messageRoom.domain.MessageRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MessageRoomIdResponseDto {
    private Long messageRoomId;

    public static MessageRoomIdResponseDto from(MessageRoom messageRoom){
        return MessageRoomIdResponseDto.builder()
                .messageRoomId(messageRoom.getMessageRoomId())
                .build();
    }
}
