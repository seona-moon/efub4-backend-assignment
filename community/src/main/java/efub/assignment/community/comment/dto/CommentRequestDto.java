package efub.assignment.community.comment.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private Long accountId;
    private String content;

    @Builder
    public CommentRequestDto(Long accountId, String content){
        this.accountId = accountId;
        this.content = content;
    }
}
