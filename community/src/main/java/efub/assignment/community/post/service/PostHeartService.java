package efub.assignment.community.post.service;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.account.service.AccountService;
import efub.assignment.community.post.PostHeartRepository;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.domain.PostHeart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostHeartService {

    private final PostHeartRepository postHeartRepository;
    private final PostService postService;
    private final AccountService accountService;

    // post 좋아요 생성
    public void create(Long postId, Long accountId){
        Account account = accountService.findAccountById(accountId);
        Post post = postService.findPostById(postId);

        if(isExistsByWriterAndPost(account, post)){
            throw new RuntimeException("이미 좋아요를 누른 게시물입니다.");
        }

        PostHeart postHeart = PostHeart.builder()
                .post(post)
                .account(account)
                .build();

        postHeartRepository.save(postHeart);
    }

    // post 좋아요 삭제
    public void delete(Long postId, Long accountId){
        Post post = postService.findPostById(postId);
        Account account = accountService.findAccountById(accountId);
        PostHeart postLike = postHeartRepository.findByWriterAndPost(account, post)
                .orElseThrow(()-> new RuntimeException("좋아요가 존재하지 않습니다."));
        postHeartRepository.delete(postLike);
    }

    // accountId로 account 찾아서 post좋아요 찾기
    public boolean isHeart(Long accountId, Post post){
        Account account = accountService.findAccountById(accountId);
        return isExistsByWriterAndPost(account, post);
    }

    // 작성자와 post로 post좋아요를 찾아서 있으면 true, 없으면 false 반환
    @Transactional(readOnly = true)
    public boolean isExistsByWriterAndPost(Account account, Post post){
        return postHeartRepository.existsByWriterAndPost(account, post);
    }

    // 해당 post의 좋아요 개수
    @Transactional(readOnly = true)
    public Integer countPostHeart(Post post){
        Integer count = postHeartRepository.countByPost(post);
        return count;
    }
}
