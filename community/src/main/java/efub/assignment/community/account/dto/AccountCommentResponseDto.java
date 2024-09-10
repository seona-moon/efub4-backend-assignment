package efub.assignment.community.account.dto;


import efub.assignment.community.account.domain.Account;
import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountCommentResponseDto {
    private String writerNickname;
    private List<CommentResponseDto> accountCommentList;
    private Long count;

    public static AccountCommentResponseDto of(Account account, List<Comment> commentList){
        return AccountCommentResponseDto.builder()
                .writerNickname(account.getNickname())
                .accountCommentList(commentList.stream().map(CommentResponseDto::of).collect(Collectors.toList()))
                .count((long) commentList.size())
                .build();
    }
}
