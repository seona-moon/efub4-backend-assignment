package efub.assignment.community.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllNoticeResponseDto {
    private List<NoticeResponseDto> notices;
}
