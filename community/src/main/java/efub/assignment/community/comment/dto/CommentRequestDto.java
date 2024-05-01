package efub.assignment.community.comment.dto;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class CommentRequestDto {
    private Long accountId;
    private String content;
}
