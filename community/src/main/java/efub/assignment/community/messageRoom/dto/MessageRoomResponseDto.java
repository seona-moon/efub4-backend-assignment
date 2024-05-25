package efub.assignment.community.messageRoom.dto;

import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.service.MessageService;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
public class MessageRoomResponseDto {
    private Long messageRoomId;
    private Long firstSendAccountId;
    private Long firstReceiveAccountId;
    private String firstContent;
    private LocalDateTime createdDate;

    public static MessageRoomResponseDto from(MessageRoom messageRoom, List<Message> messageList) {

        Optional<Message> firstMessage = messageList
                .stream()
                .min(Comparator.comparing(Message::getCreatedDate));

        if (firstMessage.isPresent()) {
            String content = firstMessage.get().getContent();
            return MessageRoomResponseDto.builder()
                    .messageRoomId(messageRoom.getMessageRoomId())
                    .firstSendAccountId(messageRoom.getFirstSendAccount().getAccountId())
                    .firstReceiveAccountId(messageRoom.getFirstReceiveAccount().getAccountId())
                    .firstContent(content)
                    .createdDate(messageRoom.getCreatedDate())
                    .build();
        } else {
            throw new RuntimeException("첫 쪽지가 존재하지 않습니다.");
        }
    }
}
