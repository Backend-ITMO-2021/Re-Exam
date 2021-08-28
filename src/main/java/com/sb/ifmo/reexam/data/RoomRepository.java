package com.sb.ifmo.reexam.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findById(long id);

    Set<Room> findAllByIsPrivate(boolean isPrivate);
}
