package efub.assignment.community.messageRoom.dto;

import efub.assignment.community.message.domain.Message;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageRoomListResponseDto {
    private List<SingleAccountMessageRoom> accountMessageRoomList;

    @Getter
    public static class SingleAccountMessageRoom{

        // 쪽지방 id
        private Long messageRoomId;
        // 가장 최근 쪽지
        private String lastMessage;
        //최신 쪽지방 최근 쪽지 날짜
        private LocalDateTime lastMessageDate;

        // 하나의 쪽지방
        // messageRoom을 파라미터로 받음
        public SingleAccountMessageRoom(Long messageRoomId, String lastMessage, LocalDateTime lastMessageDate){
            //쪽지방 id
            this.messageRoomId = messageRoomId;
            // 가장 최근 쪽지
            this.lastMessage = lastMessage;
            // 가장 최근 쪽지 날짜
            this.lastMessageDate = lastMessageDate;
        }

        public static MessageRoomListResponseDto.SingleAccountMessageRoom of(MessageRoom messageRoom){

            Long messageRoomId = messageRoom.getMessageRoomId();
            List<Message> messages = messageRoom.getMessageList();

            // 쪽지 리스트에서 가장 최근에 작성된 쪽지
            Message lastMessage = messages.stream()
                    .max(Comparator.comparing(Message::getCreatedDate)) // 생성일자를 기준으로 내림차순 정렬
                    .orElse(null); // 비어있는 경우 null

            String lastMessageContent = lastMessage.getContent();
            LocalDateTime lastMessageDate = lastMessage.getCreatedDate();

            return new MessageRoomListResponseDto.SingleAccountMessageRoom(messageRoomId, lastMessageContent, lastMessageDate);
        }
    }

    public static MessageRoomListResponseDto of(List<MessageRoom> messageRoomList){

        return MessageRoomListResponseDto.builder()
                .accountMessageRoomList(messageRoomList.stream().map(MessageRoomListResponseDto.SingleAccountMessageRoom::of).collect(Collectors.toList()))
                .build();
    }
}
