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
    public boolean existsByNickname(String nickname){
        return accountRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public Account findAccountById(Long id){
        return accountRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("해당 id를 가진 Account를 찾을 수 없습니다. id="+id));
    }

    @Transactional(readOnly = true)
    public Account findAccountByEmail(String email){
        return accountRepository.findByEmail(email)
                .orElseThrow(()-> new EntityNotFoundException("해당 email을 가진 Account를 찾을 수 없습니다. email="+email));
    }

    public Long update(Long account_id, AccountUpdateRequestDto requestDto){
        if(existsByEmail(requestDto.getEmail())){ // 변경하려는 이메일이 이미 등록되어 있는지
            if(!account_id.equals(findAccountByEmail(requestDto.getEmail()).getAccountId())){  // 등록되어있는 이메일이 자신의 계정 이메일이 아니면 IllegalArgumentException "이미 존재하는 email입니다."
                throw new IllegalArgumentException("이미 존재하는 email입니다."+requestDto.getEmail());
            }
        }
        Account account = findAccountById(account_id);
        if(account.getNickname() != requestDto.getNickname()){
            if(existsByNickname(requestDto.getNickname())){
                throw new IllegalArgumentException("이미 존재하는 nickname입니다."+requestDto.getNickname());
            }
        }
        account.updateAccount(requestDto.getEmail(),requestDto.getNickname(),requestDto.getPassword());
        return account.getAccountId();
    }

    public void withdraw(Long account_id){
        Account account = findAccountById(account_id);
        account.withdrawAccount();
    }

    @Transactional(readOnly = true) //닉네임으로 해당 계정 찾는 메소드
    public Account findAccountByNickname(String name){
        return accountRepository.findByNickname(name)
                .orElseThrow(()-> new EntityNotFoundException("해당 nickname를 가진 Account를 찾을 수 없습니다. nickname="+name));
    }
}
