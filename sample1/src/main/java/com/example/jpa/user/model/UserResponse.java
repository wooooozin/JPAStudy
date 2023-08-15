package com.example.jpa.user.model;

import com.example.jpa.user.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private long id;
    private String email;
    private String userName;
    private String phone;

    public UserResponse(AppUser user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.userName = user.getUserName();
        this.phone = user.getPhone();
    }
}
