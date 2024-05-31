package efub.assignment.community.notice.service;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.account.service.AccountService;
import efub.assignment.community.board.domain.Board;
import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.notice.domain.Notice;
import efub.assignment.community.notice.repository.NoticeRepository;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.PostRequestDto;
import efub.assignment.community.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final AccountService accountService;
    private final PostService postService;

    //notice 생성
    public void createCommentNotice(Long postId, String content ){
        Post post = postService.findPostById(postId);
        Notice notice = Notice.builder()
                .account(post.getAccount())
                .type("댓글")
                .content("새로운 댓글이 달렸어요: "+content)
                .boardName(post.getBoard().getBoardName())
                .build();
        noticeRepository.save(notice);
    }

    // 쪽지방 생성 알림 생성
    public void createMessageRoomNotice(Long accountId){
        Account account = accountService.findAccountById(accountId);
        Notice notice = Notice.builder()
                .account(account)
                .type("쪽지방")
                .content("새로운 쪽지방이 생겼어요")
                .build();
        noticeRepository.save(notice);
    }

    // 모든 알림 조회
    @Transactional(readOnly = true)
    public List<Notice> findAllNotices(Long accountId) {
        Account account = accountService.findAccountById(accountId);
        return noticeRepository.findByAccount(account);
    }
}
