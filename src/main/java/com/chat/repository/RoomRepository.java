package com.chat.repository;

import com.chat.domain.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author
 */
public interface RoomRepository extends CrudRepository<Room, Long> {

}
