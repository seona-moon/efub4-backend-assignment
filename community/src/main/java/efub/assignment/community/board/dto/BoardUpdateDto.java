package efub.assignment.community.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardUpdateDto {

    @NotBlank(message = "계정 닉네임은 필수입니다.")
    private String ownerNickname;

    @Builder
    public BoardUpdateDto(String name){
        this.ownerNickname = name;
    }
}
