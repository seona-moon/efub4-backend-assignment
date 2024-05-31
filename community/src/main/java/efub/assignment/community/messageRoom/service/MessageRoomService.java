package efub.assignment.community.messageRoom.service;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.account.service.AccountService;
import efub.assignment.community.exception.CustomDeleteException;
import efub.assignment.community.exception.ErrorCode;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.dto.MessageRoomRequestDto;
import efub.assignment.community.messageRoom.repository.MessageRoomRepository;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageRoomService {

    private final MessageRoomRepository messageRoomRepository;
    private final AccountService accountService;
    private final PostService postService;

    // 쪽지방 생성
    public MessageRoom createMessageRoom(MessageRoomRequestDto requestDto) {
        Account sendAccount = accountService.findAccountById(requestDto.getFirstSendAccountId());
        Account receiveAccount = accountService.findAccountById(requestDto.getFirstReceiveAccountId());
        Post post = postService.findPostById(requestDto.getStartPostId());

        MessageRoom messageRoom = requestDto.toEntity(sendAccount,receiveAccount,post);

        messageRoomRepository.save(messageRoom);
        return messageRoom;
    }

    //messageRoom 여부 조회
    @Transactional(readOnly = true)
    public MessageRoom getMessageRoomId(Long viewAccountId, Long receiveAccountId, Long postId) {

        Account viewAccount = accountService.findAccountById(viewAccountId);
        Account receiveAccount = accountService.findAccountById(receiveAccountId);

        return messageRoomRepository.findByFirstSendAccountAndFirstReceiveAccount(viewAccount, receiveAccount).orElseGet(()->null);
    }

    //messageRoom 삭제
    public void deleteMessageRoom(Long messageRoomId, Long accountId) {
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId)
                .orElseThrow(()-> new EntityNotFoundException("해당 id를 가진 MessageRoom을 찾을 수 없습니다. id="+messageRoomId));
        if(accountId != messageRoom.getFirstSendAccount().getAccountId()){
            throw new CustomDeleteException(ErrorCode.PERMISSION_REJECTED_USER);
        }
        messageRoomRepository.delete(messageRoom);
    }

    // account가 속해있는 messageroom List 반환
    @Transactional(readOnly = true)
    public List<MessageRoom> findMessageRoomList(Account account) {

        // account가 FirstSendAccount 혹은 FirstReceiveAccount인 messageRoomList를 각각 찾음
        List<MessageRoom> messageRoomList1 = messageRoomRepository.findAllByFirstSendAccount(account);
        List<MessageRoom> messageRoomList2 = messageRoomRepository.findAllByFirstReceiveAccount(account);

        // 두 리스트 합쳐서 전체 리스트 반환
        List<MessageRoom> combinedmessageRoomList = new ArrayList<>();
        combinedmessageRoomList.addAll(messageRoomList1);
        combinedmessageRoomList.addAll(messageRoomList2);

        return combinedmessageRoomList;
    }

    //messageRoomId로 messageRoom 찾기
    @Transactional(readOnly = true)
    public MessageRoom findMessageRoomById(Long messageRoomId) {
        return messageRoomRepository.findMessageRoomByMessageRoomId(messageRoomId)
                .orElseThrow(()->new EntityNotFoundException("해당 id의 messageRoom이 존재하지 않습니다.id"+messageRoomId));
    }

    //쪽지방 상대방 accountId 찾기
    @Transactional(readOnly = true)
    public Long findCorrespondentAccountId(Long messageRoomId,Account account){
        MessageRoom messageRoom = findMessageRoomById(messageRoomId);
        if(account.getAccountId() == messageRoom.getFirstReceiveAccount().getAccountId()){
            return messageRoom.getFirstSendAccount().getAccountId();
        }
        else if(account.getAccountId() == messageRoom.getFirstSendAccount().getAccountId()){
            return messageRoom.getFirstReceiveAccount().getAccountId();
        }
        // 쪽지방에 account가 포함 되어있지 않을 때
        // 쪽지방에 포함된 account만 조회할 수 있게 권한 확인
        else {
            throw new CustomDeleteException(ErrorCode.PERMISSION_REJECTED_USER);
        }
    }

}
