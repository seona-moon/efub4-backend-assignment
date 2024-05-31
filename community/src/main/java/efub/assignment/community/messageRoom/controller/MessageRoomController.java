package efub.assignment.community.messageRoom.controller;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.messageRoom.dto.MessageRoomListResponseDto;
import efub.assignment.community.account.service.AccountService;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.service.MessageService;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.dto.MessageRoomIdResponseDto;
import efub.assignment.community.messageRoom.dto.MessageRoomRequestDto;
import efub.assignment.community.messageRoom.dto.MessageRoomResponseDto;
import efub.assignment.community.messageRoom.service.MessageRoomService;
import efub.assignment.community.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messageRooms")
public class MessageRoomController {
    private final AccountService accountService;
    private final MessageRoomService messageRoomService;
    private final MessageService messageService;
    private final NoticeService noticeService;

    // 쪽지방 생성 api
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MessageRoomResponseDto createMessageRoom(@RequestBody @Valid final MessageRoomRequestDto requestDto){
        //messageRoom 생성
        MessageRoom messageRoom = messageRoomService.createMessageRoom(requestDto);
        // 처음 쪽지를 받는 사람에게 알림 생성
        noticeService.createMessageRoomNotice(requestDto.getFirstReceiveAccountId());

        // 첫 쪽지 생성
        messageService.createFirstMessage(messageRoom.getMessageRoomId(),requestDto);

        List<Message> messageList = messageService.findMessageRoomMessageList(messageRoom.getMessageRoomId());

        return MessageRoomResponseDto.from(messageRoom, messageList);
    }

    //쪽지방 목록 조회 api
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public MessageRoomListResponseDto getMessageRoomList(@RequestParam(name = "viewAccountId")Long viewAccountId){
        // 조회하는 사람 account
        Account viewAccount = accountService.findAccountById(viewAccountId);
        // viewAccount가 포함되어 있는 쪽지방 List
        List<MessageRoom> messageRoomList = messageRoomService.findMessageRoomList(viewAccount);

        return MessageRoomListResponseDto.of(messageRoomList);
    }

    // 쪽지방 여부 조회 api
    // GET 요청은 body 사용x
    @GetMapping("/exists")
    @ResponseStatus(value = HttpStatus.OK)
    public MessageRoomIdResponseDto getMessageRoomId(@RequestParam(name = "viewAccountId")Long viewAccountId,
                                                     @RequestParam(name = "receiveAccountId")Long receiveAccountId,
                                                     @RequestParam(name = "postId")Long postId){

        MessageRoom messageRoom = messageRoomService.getMessageRoomId(viewAccountId, receiveAccountId, postId);

        if(messageRoom == null){
            return null;
        }
        else{
            return MessageRoomIdResponseDto.from(messageRoom);
        }
    }

    // 쪽지방 삭제 api
    @DeleteMapping("/{messageRoomId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteMessageRoom(@PathVariable(name = "messageRoomId")Long messageRoomId,
                                    @RequestParam(name = "accountId")Long accountId){
        messageRoomService.deleteMessageRoom(messageRoomId, accountId);

        return "쪽지방이 성공적으로 삭제되었습니다.";
    }

}
