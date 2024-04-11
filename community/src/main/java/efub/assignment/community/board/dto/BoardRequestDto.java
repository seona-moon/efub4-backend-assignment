package efub.assignment.community.board.dto;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.account.domain.Account;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardRequestDto {

    @NotBlank(message = "계정 닉네임은 필수입니다.")
    private String ownerNickname;

    @NotBlank(message = "게시판 이름은 필수입니다.")
    private String boardName;

    @NotBlank(message = "게시판 설명은 필수입니다.")
    private String boardDescription;

    @NotBlank(message = "게시판 공지는 필수입니다.")
    private String boardNotice;

    public Board toEntity(Account account){
        return Board.builder()
                .account(account)
                .boardName(this.boardName)
                .boardDescription(this.boardDescription)
                .boardNotice(this.boardNotice)
                .build();
    }

}
