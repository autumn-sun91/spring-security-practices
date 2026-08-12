package com.example.securitypractices.account;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository; // rdbms, nosql 등등 구현하는거에따라 다름.
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username).orElse(null);

        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        // spring security 에서 미리 만들어놓은 User 클래스로 쉽게 변환 가능
        // Principal 을 받는것이 아닌 최대한 도메인의 대한 정보로 맵핑 해서 가져올때 별도 Adapter 클래스 생성
        return new UserAccount(account);
    }

    @Transactional
    public void createNew(Account account) {
        String encode = passwordEncoder.encode(account.getPassword());
        account.setPassword(encode);

        accountRepository.save(account);
    }
}
