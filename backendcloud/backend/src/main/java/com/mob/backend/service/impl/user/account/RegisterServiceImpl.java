package com.mob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mob.backend.mapper.UserMapper;
import com.mob.backend.pojo.User;
import com.mob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        if(username == null){
            map.put("error_message", "cannot accept null username");
            return map;
        }

        if(password == null || confirmedPassword == null){
            map.put("error_message", "password cannot be empty");
            return map;
        }

        username = username.trim();
        if(username.length() == 0){
            map.put("error_message", "username cannot be empty");
            return map;
        }
        if(password.length() == 0 || confirmedPassword.length() == 0){
            map.put("error_message", "Password cannot be empty");
            return map;
        }

        if(username.length() > 100){
            map.put("error_message", "Username cannot be too long");
            return map;
        }

        if(password.length() > 100 || confirmedPassword.length() > 100){
            map.put("error_message", "Password cannot be too long");
            return map;
        }

        if(!password.equals(confirmedPassword)){
            map.put("error_message", "The password does not match to the confirmed password");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if(!users.isEmpty()){
            map.put("error_message", "The username is existed");
            return map;
        }

        String encodedPassword = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/149990_lg_59674fa471.jpg";
        User user = new User(null, username, encodedPassword, photo, 1500);
        userMapper.insert(user);

        map.put("error_message", "success");
        return map;
    }
}
