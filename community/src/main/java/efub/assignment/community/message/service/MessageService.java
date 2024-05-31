package efub.assignment.community.message.service;


import efub.assignment.community.account.domain.Account;
import efub.assignment.community.account.service.AccountService;
import efub.assignment.community.board.domain.Board;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.dto.MessageRequestDto;
import efub.assignment.community.message.repository.MessageRepository;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.dto.MessageRoomRequestDto;
import efub.assignment.community.messageRoom.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {
    @Autowired
    private MessageRoomService messageRoomService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageRepository messageRepository;


    // message 생성
    public Message createMessage(MessageRequestDto dto) {

        MessageRoom messageRoom = messageRoomService.findMessageRoomById(dto.getMessageRoomId());
        Account sendAccount = accountService.findAccountById(dto.getSendAccountId());
        Message message = dto.toEntity(messageRoom, sendAccount, dto.getContent());
        return messageRepository.save(message);
    }

    // messageRoom 생성 시 첫 message 생성
    public Message createFirstMessage(Long messageRoomId, MessageRoomRequestDto dto) {

        MessageRoom messageRoom = messageRoomService.findMessageRoomById(messageRoomId);
        Account sendAccount = accountService.findAccountById(dto.getFirstSendAccountId());
        Message message = MessageRequestDto.toEntity(messageRoom, sendAccount, dto.getFirstContent());
        return messageRepository.save(message);
    }

    @Transactional(readOnly = true)
    public List<Message> findMessageRoomMessageList(Long messageRoomId) {
        MessageRoom messageRoom = messageRoomService.findMessageRoomById(messageRoomId);
        return messageRepository.findAllByMessageRoom(messageRoom);
    }
}
