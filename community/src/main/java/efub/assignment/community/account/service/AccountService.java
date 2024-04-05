package efub.assignment.community.account.service;

import efub.assignment.community.account.AccountRepository;
import efub.assignment.community.account.domain.Account;
import efub.assignment.community.account.dto.AccountUpdateRequestDto;
import efub.assignment.community.account.dto.SignUpRequestDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Long signUp(SignUpRequestDto requestDto){
        if(existsByEmail(requestDto.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 email입니다."+requestDto.getEmail());
        }
        Account account = accountRepository.save(requestDto.toEntity());
        return account.getAccountId();
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email){
        return accountRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public Account findAccountById(Long id){
        return accountRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 id를 가진 Account를 찾을 수 없습니다. id="+id));
    }

    @Transactional(readOnly = true)
    public Account findAccountByEmail(String email){
        return accountRepository.findByEmail(email);
    }

    public Long update(Long member_id, AccountUpdateRequestDto requestDto){
        if(existsByEmail(requestDto.getEmail())){ // 변경하려는 이메일이 이미 등록되어 있는지
            if(!member_id.equals(findAccountByEmail(requestDto.getEmail()).getAccountId())){  // 등록되어있는 이메일이 자신의 계정 이메일이 아니면 IllegalArgumentException "이미 존재하는 email입니다."
                throw new IllegalArgumentException("이미 존재하는 email입니다."+requestDto.getEmail());
            }
        }
        Account account = findAccountById(member_id);
        if(requestDto.getEmail()==null || requestDto.getPassword()==null) { // requestbody에 닉네임만 입력한 경우
            account.updateAccount(requestDto.getNickname());
        }
        else{ // requestbody에 이메일, 닉네임, 비밀번호 모두 입력한 경우
            account.updateAccount(requestDto.getEmail(),requestDto.getNickname(),requestDto.getPassword());
        }
        return account.getAccountId();
    }

    public void withdraw(Long member_id){
        Account account = findAccountById(member_id);
        account.withdrawAccount();
    }
}
