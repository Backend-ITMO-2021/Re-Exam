package com.dzeru.springchat.controllers;

import com.dzeru.springchat.entities.Room;
import com.dzeru.springchat.entities.User;
import com.dzeru.springchat.repos.RoomRepo;
import com.dzeru.springchat.repos.UserRepo;
import com.dzeru.springchat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class RoomsController
{
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoomRepo roomRepo;

	@GetMapping("/rooms")
	public String rooms(Principal principal, Model model)
	{
		User user = (User) userService.loadUserByUsername(principal.getName());
		Set<Room> userRooms = user.getRooms();
		model.addAttribute("user_rooms", userRooms);
		model.addAttribute("user", user);

		List<Room> allRooms = roomRepo.findAllByRestrictedFalse();
		List<Room> otherRooms = new ArrayList<>();
		for (Room room : allRooms) {
			if (!userRooms.contains(room)) otherRooms.add(room);
		}
		model.addAttribute("other_rooms", otherRooms);

		return "rooms";
	}

	@PostMapping("/addroom")
	public String addRoom(Principal principal, String name, boolean isPrivate) {
		User user = (User) userService.loadUserByUsername(principal.getName());
		Room newRoom = new Room();
		newRoom.setName(name);
		newRoom.setRestricted(isPrivate);
		newRoom.setUser(user);
		roomRepo.save(newRoom);
		user.addRoom(newRoom);
		userRepo.save(user);

		return "redirect:/rooms";
	}

	@PostMapping("/change-name")
	public String changeName(Principal principal, String newName) {
		User user = (User) userService.loadUserByUsername(principal.getName());
		user.setName(newName);
		userRepo.save(user);

		return "redirect:/rooms";
	}
}
