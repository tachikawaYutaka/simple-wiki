package com.wakabatimes.simplewiki.app.domain.model.user;

public interface UserRepository {
    /**
     *
     * @param userName
     * @return User
     */
    User getUserByUserName(String userName);

    /**
     *
     * @param user
     */
    void save(User user);

    /**
     *
     * @param userId
     * @param userPassword
     */
    void updatePassword(UserId userId, UserPassword userPassword);

    /**
     *
     * @param userId
     */
    void delete(UserId userId);

    /**
     *
     * @param userName
     */
    Long countUserByIdAndName(UserName userName);

    /**
     *
     * @param user
     * @param newUserName
     */
    void updateUserName(User user, UserName newUserName);
}
