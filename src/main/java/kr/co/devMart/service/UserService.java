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
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 회원가입 처리
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

    // 사용자 정보 조회
    public Map<String, Object> getUserInfo(String username) {
        return userMapper.selectUserByUsername(username);
    }

    // 회원 목록 조회 (관리자)
    public List<Map<String, Object>> getUserList(Map<String, Object> params) {
        return userMapper.selectUserList(params);
    }

    // 회원 등록 (관리자)
    public int createUser(Map<String, Object> params) {
        // 비밀번호 암호화 처리
        if (params.containsKey("password")) {
            String rawPw = (String) params.get("password");
            String encPw = new BCryptPasswordEncoder().encode(rawPw);
            params.put("password", encPw);
        }
        userMapper.insertUser(params);
        return 1;
    }

    // 회원 수정 (관리자)
    public int updateUser(Map<String, Object> params) {
        return userMapper.updateUser(params);
    }

    // 회원 삭제 (관리자)
    public int deleteUser(Map<String, Object> params) {
        return userMapper.deleteUser(params);
    }

    // 회원 상세 조회 (관리자)
    public Map<String, Object> getUserDetail(Map<String, Object> params) {
        String username = (String) params.get("username");
        return userMapper.selectUserByUsername(username);
    }

    // Spring Security 사용자 인증 정보 로드
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
