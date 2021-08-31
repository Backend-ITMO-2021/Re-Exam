package com.chatty.repository;

import com.chatty.model.Room;
import com.chatty.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface RoomRepository extends JpaRepository<Room, String> {
    Optional<Room> findAllByOwner(User owner);

//    @Query(value = " SELECT name FROM Room ", nativeQuery = true)
//    Set<Room> getAllRoomNames();
}
