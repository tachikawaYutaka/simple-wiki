package com.wakabatimes.simplewiki.app.application.user;

import com.wakabatimes.simplewiki.app.domain.model.user.*;
import com.wakabatimes.simplewiki.app.domain.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        Long count = userRepository.countUserByIdAndName(user.getUserName());
        if(count > 0) {
            if(!user.getUserRole().name().equals(UserRole.ROLE_ADMIN.name())){
                userRepository.delete(user);
            }else {
                throw new RuntimeException("管理者ユーザーは削除できません。");
            }
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
    public User getById(UserId userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public Users list() {
        return userRepository.list();
    }
}
