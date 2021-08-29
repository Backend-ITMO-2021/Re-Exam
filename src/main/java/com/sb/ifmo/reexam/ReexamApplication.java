package com.sb.ifmo.reexam;

import com.sb.ifmo.reexam.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ReexamApplication {
    private static final Logger logger = LoggerFactory.getLogger(ReexamApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ReexamApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo(CustomUserRepository customUserRepository, RoomRepository roomRepository, MessageRepository messageRepository) {
        return (args) -> {
            logger.info("Creating Users");
            for (int i = 1; i <= 11; i++) {
                customUserRepository.save(new CustomUser("testUser" + i, "testUser" + i + "@example.com"));
            }
            logger.info("Users created");

            logger.info("Creating Rooms");
            for (int i = 1; i <= 3; i++) {
                CustomUser admin = customUserRepository.findByUsernameIs("testUser" + i);
                if (admin == null) {
                    logger.error("Couldn't find user \"testUser"  + i + "\"");
                }
                roomRepository.save(new Room("testRoom" + i, false,  admin));
            }
            CustomUser admin = customUserRepository.findByUsernameIs("testUser4");
            if (admin == null) {
                logger.error("Couldn't find user \"testUser4\"");
            }
            roomRepository.save(new Room("testRoom4", true,  admin));
            logger.info("Rooms created");

            // Added user to rooms if their number divides by room number
            // room1 - users{1, 2, .., 11}
            // room2 - users{2, 4, .., 10}
            // room3 - users{3, 6, 9}
            // room4 - users{4, 8}
            logger.info("Inviting Users to Rooms");
            for (int i = 1; i <= 4; i++) {
                Room room = roomRepository.findFirstByNameIs("testRoom" + i);
                if (room == null) {
                    logger.error("Couldn't find Room \"testRoom"  + i + "\"");
                }
                for (int j = 1; j <= 11; j++) {
                    if (j % i == 0 && j != i) {
                        CustomUser user = customUserRepository.findByUsernameIs("testUser" + j);
                        if (user == null) {
                            logger.error("Couldn't find user \"testUser"  + j + "\" with i = " + i);
                        }
                        room.addUser(user);
                    }
                }
                roomRepository.save(room);
            }
            logger.info("Users invited to Rooms");

            // In every room user will post {roomNumber + userNumber} messages like
            // "Test message {messageNumber} in room {roomNumber} by user {userNumber}"
            logger.info("Creating messages");
            for (int i = 1; i <= 4; i++) {
                Room room = roomRepository.findFirstByNameIs("testRoom" + i);
                if (room == null) {
                    logger.error("Couldn't find Room \"testRoom"  + i + "\"");
                }
                for (int j = 1; j <= 11; j++) {
                    if (j % i == 0) {
                        CustomUser user = customUserRepository.findByUsernameIs("testUser" + j);
                        if (user == null) {
                            logger.error("Couldn't find user \"testUser"  + j + "\" with i = " + i);
                        }
                        for (int k = 1; k <= i + j; k++) {
                            // TODO: add delay so message.time would be different
                            messageRepository.save(new Message("Test message " + k + " in room " + i + " by user " + j,
                                    room, user));
                        }
                    }
                }
            }
            logger.info("Messages Created");
            logger.info("Initialization complete");
        };
    }
}
