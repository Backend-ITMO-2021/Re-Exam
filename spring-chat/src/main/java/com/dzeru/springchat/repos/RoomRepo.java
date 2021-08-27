package com.dzeru.springchat.repos;

import com.dzeru.springchat.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    List<Room> findByUserId(Long userId);
    Optional<Room> findById(Long roomId);
    List<Room> findAllByRestrictedFalse();
}
