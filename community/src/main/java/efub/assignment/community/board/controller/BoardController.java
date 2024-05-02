package efub.assignment.community.board.controller;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.dto.BoardRequestDto;
import efub.assignment.community.board.dto.BoardResponseDto;
import efub.assignment.community.board.dto.BoardUpdateDto;
import efub.assignment.community.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public BoardResponseDto createNewBoard(@RequestBody @Valid BoardRequestDto dto){
        Board savedBoard = boardService.createNewBoard(dto);
        return BoardResponseDto.from(savedBoard, savedBoard.getAccount().getNickname());
    }

    @GetMapping("/{board_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BoardResponseDto getBoard(@PathVariable(name="board_id")Long board_id){
        Board board = boardService.findBoardById(board_id);
        return BoardResponseDto.from(board, board.getAccount().getNickname());
    }

    @PutMapping("/{board_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BoardResponseDto updateBoard(@PathVariable(name="board_id")Long board_id,
                                        @RequestBody @Valid final BoardUpdateDto dto){
        Board board = boardService.updateBoard(board_id, dto);
        return BoardResponseDto.from(board, board.getAccount().getNickname());
    }

    @DeleteMapping("/{board_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteBoard(@PathVariable(name="board_id")Long board_id,
                              @RequestParam(name="accountId")Long account_id){
        boardService.deleteBoard(board_id,account_id);

        return "게시판을 삭제하였습니다.";
    }
}
