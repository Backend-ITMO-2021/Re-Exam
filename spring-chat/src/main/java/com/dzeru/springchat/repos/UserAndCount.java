package com.dzeru.springchat.repos;

import com.dzeru.springchat.entities.User;

public interface UserAndCount {
    String getUser();
    Long getMessageCount();
}
