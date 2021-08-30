package com.sb.ifmo.reexam.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findById(long id);
    Set<Room> findAllByUsersContainsOrIsPrivate(CustomUser user, boolean isPrivate);
    List<Room> findAll();

    //Used for initialising
    Room findFirstByNameIs(String name);
}
