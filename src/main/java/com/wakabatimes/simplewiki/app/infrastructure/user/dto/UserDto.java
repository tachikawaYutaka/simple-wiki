package com.wakabatimes.simplewiki.app.infrastructure.user.dto;

import com.wakabatimes.simplewiki.app.domain.model.user.User;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String name;
    private String password;
    private Integer role;

    public UserDto (){

    }

    public UserDto(User user) {
        this.id = user.getUserId().getValue();
        this.name = user.getUserName().getValue();
        this.password = user.getUserPassword().getValue();
        this.role = user.getUserRole().getId();
    }
}
