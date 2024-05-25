package efub.assignment.community.notice.service;

import efub.assignment.community.notice.domain.Notice;
import efub.assignment.community.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    // commentNotice 찾기
    @Transactional(readOnly = true)
    public List<Notice> findAllCommentNotices() {
        return noticeRepository.findByTypeIs("comment");
    }

    //messageRoomNotice 찾기
    @Transactional(readOnly = true)
    public List<Notice> findAllMessageRoomNotices() {
        return noticeRepository.findByTypeIs("messageRoom");
    }
}
