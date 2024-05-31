package efub.assignment.community.notice.controller;

import efub.assignment.community.notice.domain.Notice;
import efub.assignment.community.notice.dto.AllNoticeResponseDto;
import efub.assignment.community.notice.dto.NoticeResponseDto;
import efub.assignment.community.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService noticeService;

    // 알림 조회 api
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AllNoticeResponseDto getAllNotice(@RequestParam(name="accountId")Long accountId){
        List<Notice> notices = noticeService.findAllNotices(accountId);
        return new AllNoticeResponseDto(notices.stream().map(NoticeResponseDto::of).collect(Collectors.toList()));
    }
}
