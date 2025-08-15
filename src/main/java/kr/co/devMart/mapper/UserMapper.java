package kr.co.devMart.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    Map<String, Object> selectUserByUsername(String username);
    
    List<Map<String, Object>> selectUserList(Map<String, Object> params);
    
    void insertUser(Map<String, Object> params);
    
    int updateUser(Map<String, Object> params);
    
    int deleteUser(Map<String, Object> params);
    
    int selectUserCount(Map<String, Object> params);
}
