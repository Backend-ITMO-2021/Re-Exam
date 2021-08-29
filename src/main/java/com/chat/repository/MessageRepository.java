package com.chat.repository;

import java.util.List;

import com.chat.domain.Message;
import com.chat.domain.Room;
import com.chat.domain.User;
import com.chat.domain.dto.MessageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author
 */
public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query(value = "SELECT new com.chat.domain.dto.MessageDto(m)" +
            "FROM Message m where m.author=:user", nativeQuery = true)
    List<MessageDto> findAllMessagesByUser(@Param("user") User userId);

    @Query(value = "SELECT new com.chat.domain.dto.MessageDto(m)" +
            "FROM Message m where m.room=:room")
    List<MessageDto> findAllMessagesByRoom(@Param("room") Room room);




//    @Query("select new com.example.sweater.domain.dto.MessageDto(" +
//            "   m, " +
//            "   count(ml), " +
//            "   sum(case when ml = :user then 1 else 0 end) > 0" +
//            ") " +
//            "from Message m left join m.likes ml " +
//            "where m.tag = :tag " +
//            "group by m")
//    Page<MessageDto> findByTag(@Param("tag") String tag, Pageable pageable, @Param("user") User user);
//
//    @Query("select new com.example.sweater.domain.dto.MessageDto(" +
//            "   m, " +
//            "   count(ml), " +
//            "   sum(case when ml = :user then 1 else 0 end) > 0" +
//            ") " +
//            "from Message m left join m.likes ml " +
//            "where m.author = :author " +
//            "group by m")
//    Page<MessageDto> findByUser(Pageable pageable, @Param("author") User author, @Param("user") User user);
}
