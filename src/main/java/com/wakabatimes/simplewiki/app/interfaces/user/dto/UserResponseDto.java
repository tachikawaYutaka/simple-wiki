package com.wakabatimes.simplewiki.app.interfaces.user.dto;

import com.wakabatimes.simplewiki.app.domain.model.user.User;
import lombok.Data;

@Data
public class UserResponseDto {
    String id;
    String name;
    String role;

    public UserResponseDto(User user) {
        this.id = user.getUserId().getValue();
        this.name = user.getUserName().getValue();
        this.role = user.getUserRole().name();
    }
}
