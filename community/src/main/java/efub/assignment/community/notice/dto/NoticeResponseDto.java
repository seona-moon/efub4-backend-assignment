package efub.assignment.community.notice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import efub.assignment.community.notice.domain.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoticeResponseDto {

    private String type;
    private String content;
    private String boardName;
    private LocalDateTime createdDate;


    public static NoticeResponseDto of(Notice notice){
        // 쪽지방알림
        if(notice.getBoardName() == null){
            return NoticeResponseDto.builder()
                    .type(notice.getType())
                    .content(notice.getContent())
                    .createdDate(notice.getCreatedDate())
                    .build();
        }
        // 댓글알림
        else{
            return NoticeResponseDto.builder()
                    .type(notice.getType())
                    .boardName(notice.getBoardName())
                    .content(notice.getContent())
                    .createdDate(notice.getCreatedDate())
                    .build();
        }
    }
}
