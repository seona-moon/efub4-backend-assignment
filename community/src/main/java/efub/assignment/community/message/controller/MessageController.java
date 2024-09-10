package efub.assignment.community.message.controller;

import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.dto.MessageRequestDto;
import efub.assignment.community.message.dto.MessageResponseDto;
import efub.assignment.community.message.service.MessageService;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.dto.MessageRoomRequestDto;
import efub.assignment.community.messageRoom.dto.MessageRoomResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    //message 생성 api
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MessageResponseDto createMessage(@RequestBody @Valid final MessageRequestDto dto){
        Message message = messageService.createMessage(dto);
        return MessageResponseDto.from(message);
    }
}
