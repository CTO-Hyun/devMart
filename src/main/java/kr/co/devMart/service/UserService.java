package kr.co.devMart.service;

import kr.co.devMart.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void signup(Map<String, Object> params) {
        String username = (String) params.get("username");
        if (userMapper.selectUserByUsername(username) != null) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        String rawPw = (String) params.get("password");
        String encPw = new BCryptPasswordEncoder().encode(rawPw);
        params.put("password", encPw);
        params.put("email", params.get("email"));
        userMapper.insertUser(params);
    }

    public Map<String, Object> getUserInfo(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> user = userMapper.selectUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }
        String password = (String) user.get("password");
        return User.builder()
                .username(username)
                .password(password)
                .authorities(Collections.singletonList(() -> "ROLE_USER"))
                .build();
    }
}
