package com.dzeru.springchat.controllers;

import com.dzeru.springchat.entities.Role;
import com.dzeru.springchat.entities.User;
import com.dzeru.springchat.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController
{
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/registration")
	public String registration()
	{
		return "registration";
	}

	@PostMapping("/registration")
	public String addUser(String name, String username, String password)
	{
		User user = new User();
		user.setName(name);
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setActive(true);
		user.setRoles(Collections.singleton(Role.USER));

		userRepo.save(user);

		return "redirect:/login";
	}
}
