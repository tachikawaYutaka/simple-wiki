package com.wakabatimes.simplewiki.app.domain.service.user;

import com.wakabatimes.simplewiki.app.domain.model.user.*;

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

    /**
     * ユーザー一覧の取得
     * @return
     */
    Users list();
}
