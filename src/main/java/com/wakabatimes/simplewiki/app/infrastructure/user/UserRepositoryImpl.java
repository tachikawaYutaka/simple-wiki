package com.wakabatimes.simplewiki.app.infrastructure.user;

import com.wakabatimes.simplewiki.app.domain.model.user.*;
import com.wakabatimes.simplewiki.app.infrastructure.user.dto.UserDto;
import com.wakabatimes.simplewiki.app.infrastructure.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByUserName(String userName) {
        UserDto input = new UserDto();
        input.setName(userName);

        UserDto userDto = userMapper.getUserByUserName(input);

        UserId userId = new UserId(userDto.getId());
        UserName userName1 = new UserName(userDto.getName());
        UserPassword userPassword = new UserPassword(userDto.getPassword());
        User user = new User(userId,userName1,userPassword);
        return user;
    }

    @Override
    public void save(User user) {
        UserDto input = new UserDto();
        input.setId(user.getUserId().getValue());
        input.setName(user.getUserName().getValue());
        input.setPassword(user.getUserPassword().getValue());

        List<UserDto> userList = userMapper.list();
        Users users = new Users();
        for(UserDto userDto : userList) {
            UserId userId = new UserId(userDto.getId());
            UserName userName = new UserName(userDto.getName());
            UserPassword userPassword = new UserPassword(userDto.getPassword());
            User thisUser = new User(userId,userName,userPassword);
            users.add(thisUser);
        }

        if(!users.containsName(user)) {
            userMapper.save(input);
        }else {
            throw new RuntimeException("ユーザー名が既に登録されています。");
        }
    }

    @Override
    public void updatePassword(UserId userId, UserPassword userPassword) {
        UserDto userDto = new UserDto();
        userDto.setId(userId.getValue());
        userDto.setPassword(userPassword.getValue());
        userMapper.updateUserPassword(userDto);
    }

    @Override
    public void delete(UserId userId) {
        UserDto userDto = new UserDto();
        userDto.setId(userId.getValue());
        userMapper.delete(userDto);
    }

    @Override
    public Long countUserByIdAndName(UserId userId, UserName userName) {
        UserDto userDto = new UserDto();
        userDto.setId(userId.getValue());
        userDto.setName(userName.getValue());
        return userMapper.countUserByIdAndName(userDto);
    }

    @Override
    public void updateUserName(User user, UserName newUserName) {
        Users users = new Users();
        List<UserDto> userDtoList = userMapper.list();
        for(UserDto userDto : userDtoList) {
            UserId userId = new UserId(userDto.getId());
            UserName userName = new UserName(userDto.getName());
            UserPassword userPassword = new UserPassword(userDto.getPassword());
            User user1 = new User(userId,userName,userPassword);
            users.add(user1);
        }

        UserId userId = new UserId(user.getUserId().getValue());
        UserName userName = new UserName(newUserName.getValue());
        UserPassword userPassword = new UserPassword(user.getUserPassword().getValue());
        User updatedUser = new User(userId,userName,userPassword);

        if(!users.containsName(updatedUser)) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getUserId().getValue());
            userDto.setName(newUserName.getValue());
            userMapper.updateUserName(userDto);
        }else {
            throw new RuntimeException("ユーザー名が既に登録されています。");
        }
    }
}

