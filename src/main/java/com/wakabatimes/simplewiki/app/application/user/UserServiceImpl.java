package com.wakabatimes.simplewiki.app.application.user;

import com.wakabatimes.simplewiki.app.domain.model.user.*;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(UserId userId, UserName userName) {
        Long count = userRepository.countUserByIdAndName(userName);
        if(count > 0) {
            userRepository.delete(userId);
        }else {
            throw new RuntimeException("指定したユーザーは存在しません。");
        }
    }

    @Override
    public void passwordUpdate(UserName userName, UserPassword userPassword) {
        Long count = userRepository.countUserByIdAndName(userName);
        if(count > 0) {
            User user = userRepository.getUserByUserName(userName.getValue());
            UserId userId = user.getUserId();
            userRepository.updatePassword(userId, userPassword);
        }else {
            throw new RuntimeException("不正なアクセスです。");
        }
    }

    @Override
    public void nameUpdate(UserName userName, UserName newUserName) {
        Long count = userRepository.countUserByIdAndName(userName);
        if(count > 0) {
            User user = userRepository.getUserByUserName(userName.getValue());
            UserId userId = user.getUserId();
            userRepository.updateUserName(user, newUserName);
        }else {
            throw new RuntimeException("不正なアクセスです。");
        }
    }

    @Override
    public User get(UserName userName) {
        return userRepository.getUserByUserName(userName.getValue());
    }

    @Override
    public Users list() {
        return userRepository.list();
    }
}
