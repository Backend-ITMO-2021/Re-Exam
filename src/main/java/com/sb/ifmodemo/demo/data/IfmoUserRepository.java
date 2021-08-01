package com.sb.ifmodemo.demo.data;

import com.sb.ifmodemo.demo.data.IfmoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IfmoUserRepository extends JpaRepository<IfmoUser, Long> {
    List<IfmoUser> findByNameIs(String name);
    List<IfmoUser> findBySessionStartingWithAndGameIsNot(String strWith, Long notCnt);
    List<IfmoUser> findAllByGameIs(long game);
    Optional<IfmoUser> findFirstByNameIsAndPasswordIs(String name, String password);
    Optional<IfmoUser> findFirstBySessionIs(String session);
}
