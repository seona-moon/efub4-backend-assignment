package efub.assignment.community.post.service;


import efub.assignment.community.account.domain.Account;
import efub.assignment.community.account.service.AccountService;
import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.service.BoardService;
import efub.assignment.community.exception.CustomDeleteException;
import efub.assignment.community.exception.ErrorCode;
import efub.assignment.community.post.PostRepository;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.PostRequestDto;
import efub.assignment.community.post.dto.PostUpdateDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static efub.assignment.community.exception.ErrorCode.PERMISSION_REJECTED_USER;

@Service
@Transactional //해당 범위의 작업들을 하나의 트랜잭션으로 처리
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AccountService accountService;
    private final BoardService boardService;

    public Post createNewPost(PostRequestDto dto){ //새로운 글을 생성하는 메소드
        Account account = accountService.findAccountByNickname(dto.getWriterNickname()); //dto의 accountId로 계정찾기
        Board board = boardService.findBoardById(Long.parseLong(dto.getBoardId())); //dto의 boardId로 board찾기
        Post post = dto.toEntity(board,account);
        Post savedPost = postRepository.save(post);  // post 정보를 DB에 저장
        return savedPost; // post 반환
    }

    @Transactional(readOnly = true)
    public List<Post> findAllPosts(){
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    @Transactional(readOnly = true)
    public long countAllPosts(){
        return postRepository.count();
    }

    @Transactional(readOnly = true)
    public Post findPostById(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new EntityNotFoundException("해당 id를 가진 Post를 찾을 수 없습니다.id="+postId));
        return post;
    }

    public Long updatePost(Long post_id, PostUpdateDto dto){
        Post post = findPostById(post_id);
        post.update(dto);
        return post.getPostId();
    }

    public void deletePost(Long post_id, Long account_id){
        Post post = findPostById(post_id);
        if(account_id!=post.getAccount().getAccountId()){
            throw new CustomDeleteException(PERMISSION_REJECTED_USER);
        }
        postRepository.delete(post);
    }


}
