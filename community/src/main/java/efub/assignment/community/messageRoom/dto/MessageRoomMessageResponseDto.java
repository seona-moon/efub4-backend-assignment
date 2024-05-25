package efub.assignment.community.messageRoom.dto;

import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.dto.MessageRequestDto;
import efub.assignment.community.message.dto.MessageResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE )
public class MessageRoomMessageResponseDto {
    private Long messageRoomId;
    private Long correspondentAccountId;
    private List<SingleMessage> messageRoomMessageList;

    @Getter
    public static class SingleMessage{

        private Long messageRoomId;
        private String sendOrReceive;
        private String content;
        private LocalDateTime createdDate;

        public SingleMessage(Long messageRoomId, String sendOrReceive, String content, LocalDateTime createdDate){
            this.messageRoomId = messageRoomId;
            this.sendOrReceive = sendOrReceive;
            this.content = content;
            this.createdDate = createdDate;
        }
        public static MessageRoomMessageResponseDto.SingleMessage of(Message message, Long correspondentAccountId){
            String sendOrReceive;
            if (correspondentAccountId == message.getSendAccount().getAccountId()){
                sendOrReceive = "receive";
            }
            else{
                sendOrReceive = "send";
            }
            return new MessageRoomMessageResponseDto.SingleMessage(message.getMessageRoom().getMessageRoomId(),sendOrReceive, message.getContent(), message.getCreatedDate());
        }
    }

    public static MessageRoomMessageResponseDto of(Long messageRoomId,Long correspondentAccountId, List<Message> messageList){
        return MessageRoomMessageResponseDto.builder()
                .messageRoomId(messageRoomId)
                .correspondentAccountId(correspondentAccountId)
                .messageRoomMessageList(messageList.stream()
                        .map(message -> SingleMessage.of(message, correspondentAccountId)).collect(Collectors.toList()))
                .build();
    }

}
