package efub.assignment.community.account.controller;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.account.dto.AccountResponseDto;
import efub.assignment.community.account.dto.AccountUpdateRequestDto;
import efub.assignment.community.account.dto.SignUpRequestDto;
import efub.assignment.community.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AccountResponseDto signUp(@RequestBody @Valid final SignUpRequestDto requestDto){
        Long id = accountService.signUp(requestDto);
        Account findAccount = accountService.findAccountById(id);
        return AccountResponseDto.from(findAccount);
    }

    @GetMapping("/{member_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public AccountResponseDto getAccount(@PathVariable Long member_id){
        Account findAccount = accountService.findAccountById(member_id);
        return AccountResponseDto.from(findAccount);
    }

    @PatchMapping("/profile/{member_id}")
    @ResponseStatus(value = HttpStatus.OK)
    public AccountResponseDto update(@PathVariable Long member_id, @RequestBody @Valid final AccountUpdateRequestDto requestDto){
        Long id = accountService.update(member_id,requestDto);
        Account findAccount = accountService.findAccountById(id);
        return AccountResponseDto.from(findAccount);
    }
}
