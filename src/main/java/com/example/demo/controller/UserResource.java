package com.example.demo.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Photos;
import com.example.demo.entity.User;
import com.example.demo.repository.PhotoRepository;
import com.example.demo.repository.UserRepository;

@RestController
public class UserResource {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PhotoRepository photoRepository;
	
	@PostMapping(value="/users")
	public ResponseEntity<Object> createUser(
			@Valid @RequestBody User user) {
		return new ResponseEntity<Object>(userRepository.save(user),HttpStatus.CREATED);
	}
	
	@PostMapping(value="/users/{userId}/photos")
	public ResponseEntity<Object> addPhotoToUser(@Valid @RequestBody Photos photos, @PathVariable Integer userId) {
		Optional<User> optnl = userRepository.findById(userId);
		if(optnl.isPresent()) {
			User usr = optnl.get();
			if(Objects.nonNull(usr.getPhotos())){
				usr.getPhotos().add(photos);
			}else {
				List<Photos> photoss = new ArrayList<>();
				photoss.add(photos);
				usr.setPhotos(photoss);
			}
			return new ResponseEntity<Object>(userRepository.save(usr),HttpStatus.CREATED);
		}else {
			return new ResponseEntity<Object>("User not found",HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping(value="/users")
	public List<User> users() {
		return  userRepository.findAll();
	}
	
	@GetMapping(value="/users/{userId}")
	public Resource<User> findById(@PathVariable Integer userId) {
		Optional<User> optnl = userRepository.findById(userId);
			
		Resource<User> resource = new Resource<User>(optnl.get());

		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).users());

		resource.add(linkTo.withRel("all-Users"));

		return resource;
	}
	
	@GetMapping(value="/users/{userId}/photos")
	public ResponseEntity<Object> findUserAdress(@PathVariable Integer userId) {
		Optional<?> optnl = userRepository.findById(userId);
		if(optnl.isPresent()) {
			User user = (User) optnl.get();
			return new ResponseEntity<Object>(user.getPhotos(), HttpStatus.OK);
		}
		else
			return new ResponseEntity<Object>("User not found",HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value="/users/{userId}/photos/{photoId}")
	public ResponseEntity<Object> findByIddd(@PathVariable Integer userId, 
			@PathVariable Integer photoId) {
		Optional<?> optnl = userRepository.findById(userId);
		if(optnl.isPresent()) {
			User user = (User) optnl.get();
			Optional<Photos> adrs = user.getPhotos().stream().filter(obj ->photoId.equals(obj.getId())).findFirst();
			return new ResponseEntity<Object>(adrs.get(), HttpStatus.OK);
		}
			
		else
			return new ResponseEntity<Object>("User not found",HttpStatus.NOT_FOUND);
	}
	
	
	@DeleteMapping("/users/{userId}")
	public void deleteById(@PathVariable Integer userId) {
		userRepository.deleteById(userId);
	}
	
	@DeleteMapping("/users/{userId}/photos/{photoId}")
	public ResponseEntity<Object> deleteUsersPhotoById(@PathVariable Integer userId,
			@PathVariable Integer photoId) {
		if(userRepository.existsById(userId)) {
			photoRepository.deleteById(photoId);
			return new ResponseEntity<Object>("User ("+userId+ ") photo ("+photoId+") has been deleted successfully", HttpStatus.OK);
		}
		else
			return new ResponseEntity<Object>("User Not Found", HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/users/{userId}")
	public ResponseEntity<?> updateUserById(@Valid @RequestBody User user, @PathVariable Integer userId) {
		Optional<User> optnl= userRepository.findById(userId);
		if(optnl.isPresent()) {
			user.setId(userId);
			userRepository.save(user);
			return ResponseEntity.noContent().build();
		}else
			return ResponseEntity.notFound().build();
		
	} 
}
