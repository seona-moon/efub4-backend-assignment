package efub.assignment.community.board.service;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.account.service.AccountService;
import efub.assignment.community.board.BoardRepository;
import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.dto.BoardRequestDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final AccountService accountService;

    public Board createNewBoard(BoardRequestDto dto){
        Account account = accountService.findAccountByNickname(dto.getOwnerNickname());
        Board board = dto.toEntity(account);
        Board savedBoard = boardRepository.save(board);
        return savedBoard;
    }
}
