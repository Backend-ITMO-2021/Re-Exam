package com.dzeru.springchat.repos;

import com.dzeru.springchat.entities.Room;
import com.dzeru.springchat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
@Repository
public interface UserRepo extends JpaRepository<User, Long>
{
	User findByUsername(String email);
	User findByName(String name);
	User findByGoogleUsername(String googleUsername);
	User findByGoogleName(String googleName);

	@Query(value = "SELECT cu.name as user, count(m.id) as messageCount FROM User cu JOIN Message m on cu = m.sender where m.room = :roomId group by cu.name")
	public List<UserAndCount> findTopChattersByMessagesCountInRoom(@Param("roomId") Room roomId);
}
