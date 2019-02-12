package com.wakabatimes.simplewiki.app.domain.model.user;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Users {
    List<User> users;

    public Users () {
        users = new ArrayList<>();
    }

    public Users(List<User> users) {
        this.users = new ArrayList<>(users);
    }

    /**
     * user add
     * @param user
     * @throws RuntimeException
     */
    public void add(@NonNull User user) throws RuntimeException {
        if(this.containsName(user)) {
            throw new DuplicatedUserException(user.toString());
        }
        this.users.add(user);
    }

    /**
     * users count
     * @return Integer
     */
    public Integer size() {
        return this.users.size();
    }

    /**
     * containName
     * @param compare_user
     * @return Boolean
     */
    public boolean containsName(User compare_user) {
        for(User user : this.users) {
            if (user.getUserName().equals(compare_user.getUserName()) && !user.getUserId().equals(compare_user.getUserId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * get list
     *
     * @return List<User>
     */
    public List<User> list() {
        return Collections.unmodifiableList(this.users);
    }
}
