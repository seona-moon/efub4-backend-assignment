package efub.assignment.community.messageRoom.controller;


import efub.assignment.community.account.domain.Account;
import efub.assignment.community.account.service.AccountService;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.service.MessageService;
import efub.assignment.community.messageRoom.dto.MessageRoomMessageResponseDto;
import efub.assignment.community.messageRoom.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messageRooms/{messageRoomId}/messages")
public class MessageRoomMessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageRoomService messageRoomService;

    //쪽지방에 있는 모든 message 조회 api
    @GetMapping
    public ResponseEntity<MessageRoomMessageResponseDto> getMessageRoomMessageList(@PathVariable(name = "messageRoomId")Long messageRoomId,
                                                                                   @RequestParam(name = "viewAccountId")Long viewAccountId){
        //조회하는 사람 계정
        Account viewAccount = accountService.findAccountById(viewAccountId);
        //쪽지방의 상대 accountId
        Long corespondentAccountId = messageRoomService.findCorrespondentAccountId(messageRoomId,viewAccount);
        //쪽지방의 쪽지 리스트
        List<Message> messageList = messageService.findMessageRoomMessageList(messageRoomId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(MessageRoomMessageResponseDto.of(messageRoomId, corespondentAccountId, messageList));
    }
}
