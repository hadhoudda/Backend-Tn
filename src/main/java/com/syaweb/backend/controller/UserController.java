package com.syaweb.backend.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syaweb.backend.model.UserModel;
//import com.syaweb.backend.requests.AgeRequest;
import com.syaweb.backend.requests.FirstNameAndLastNameRequest;
import com.syaweb.backend.service.UserService;

@RestController
@RequestMapping("/users") //localhost:9000/users
public class UserController {
	
	@Autowired
	private UserService userService; 
	
	
	@GetMapping() //localhost:9000/users
	public List<UserModel> getAllUtilisateur() {
		return userService.getAllUsers();
	}
	
	//***** premier méthode de verbe get id à partir de l'url  ****//
	@GetMapping(path="/{id}") //localhost:9000/users/2
	public ResponseEntity<UserModel> getUtilisateur(@PathVariable Long id) {
		UserModel userModel = userService.findUserById(id);
		if(userModel == null) {
			return new ResponseEntity<UserModel>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<UserModel>(userModel , HttpStatus.OK);
		}
	}
	
	
	@GetMapping(path="/firstName/{firstName}") //localhost:9000/users/firstName/Houda
	public ResponseEntity<List<UserModel>> getUtilisateurFirstName(@PathVariable String firstName) {
		List<UserModel> usersModel = userService.findByFirstName(firstName);
		if(usersModel.isEmpty()) {
			return new ResponseEntity<List<UserModel>>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<UserModel>>(usersModel , HttpStatus.OK);
		}
	}
	
	@GetMapping(path="/firstNameAndlasName") //localhost:9000/users/firstNameAndlasName
	//le MVC prend le charge de convertir le body json en requet
	public ResponseEntity<List<UserModel>> getFindByFirstNameAndLastName(@RequestBody FirstNameAndLastNameRequest firstNameAndLastNameRequest) {
		List<UserModel> usersModel = userService.findByFirstNameAndLastName(firstNameAndLastNameRequest.getFirstName(), firstNameAndLastNameRequest.getLastName());
		if(usersModel.isEmpty() ){
			return new ResponseEntity<List<UserModel>>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<UserModel>>(usersModel , HttpStatus.OK);
		}
	}
	
	@GetMapping(path="/firstNameAndlasNameJPQL") //localhost:9000/users/firstNameAndlasNameJPQL
	public ResponseEntity<List<UserModel>> getFindByFirstNameAndLastNameJPQL(@RequestBody FirstNameAndLastNameRequest  firstNameAndLastNameRequest) {
		List<UserModel> usersModel = userService.findByFirstNameAndLastNameJPQL(firstNameAndLastNameRequest.getFirstName(), firstNameAndLastNameRequest.getLastName());
		if(usersModel.isEmpty() ){
			return new ResponseEntity<List<UserModel>>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<List<UserModel>>(usersModel , HttpStatus.OK);
		}
	}
	
	
	
//	//***** dexieme méthode de verbe get id à partir de request  plus ancienne****//
//	@GetMapping(path="/findidReqParams") //localhost:8080/utilisateur/findidReqParams?id=2
//	public ResponseEntity<UserModel> getUtilisateurId(@RequestParam Long id) {
//		UserModel userModel = userService.findUserById(id);
//		if(userModel == null) {
//			return new ResponseEntity<UserModel>(HttpStatus.NO_CONTENT);
//		}
//		else {
//			return new ResponseEntity<UserModel>(userModel , HttpStatus.OK);
//		}
//	}
	
	@PostMapping
	public UserModel createUtilisateur(@RequestBody UserModel user) {
		return userService.createUser(user);
	}
	
	@PutMapping
	public UserModel updateUtilisateur(@RequestBody UserModel user) {
		return userService.updateUser(user);
	}
	
//	
//	@DeleteMapping("/{id}")
//	public void deleteUtilisateur(@PathVariable Long id) {
//		userService.deleteUser(id);
//	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UserModel> deleteUtilisateur(@PathVariable Long id) {
		UserModel userModel = userService.findUserById(id);
		if(userModel == null) {
			return new ResponseEntity<UserModel>(HttpStatus.NO_CONTENT);
		}
		else {
			userService.deleteUser(id);
			return new ResponseEntity<UserModel>( HttpStatus.OK);
		}
	}
		

	
	
}


