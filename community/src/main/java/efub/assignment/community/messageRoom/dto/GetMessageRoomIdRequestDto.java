package efub.assignment.community.messageRoom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetMessageRoomIdRequestDto {

    @NotNull(message = "조회하는 사람 accountId는 필수입니다.")
    private Long veiwAccountId;

    @NotNull(message = "받는 사람 accountId는 필수입니다.")
    private Long receiveAccountId;

    @NotNull(message = "시작된 글 postId는 필수입니다.")
    private Long startPostId;

}
