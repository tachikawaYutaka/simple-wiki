package com.wakabatimes.simplewiki.app.domain.service.user;

import com.wakabatimes.simplewiki.app.domain.model.user.User;
import com.wakabatimes.simplewiki.app.domain.model.user.UserId;
import com.wakabatimes.simplewiki.app.domain.model.user.UserName;
import com.wakabatimes.simplewiki.app.domain.model.user.UserPassword;

public interface UserService {
    /**
     * ユーザー情報の保存
     * @param user
     */
    void save(User user);

    /**
     * ユーザーの退会
     * @param userId
     */
    void delete(UserId userId, UserName userName);

    /**
     * パスワード更新
     * @param userName
     * @param userPassword
     */
    void passwordUpdate(UserName userName, UserPassword userPassword);

    /**
     * userName更新
     * @param userName
     * @param newUserName
     */
    void nameUpdate(UserName userName, UserName newUserName);

    /**
     * useの照会
     * @param userName
     * @return
     */
    User get(UserName userName);
}
