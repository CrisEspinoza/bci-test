package bci.test.bcitest.controller;

import bci.test.bcitest.dto.user.CreateUserDto;
import bci.test.bcitest.dto.user.UpdateUserDto;
import bci.test.bcitest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@ControllerAdvice
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?> get(@RequestBody CreateUserDto user){
        return userService.login(user);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> get(@PathVariable String email){
        return userService.get(email);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserDto user){
        return userService.create(user);
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> update(@Valid @PathVariable String email, @RequestBody UpdateUserDto user){
        return userService.update(email, user);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> delete(@PathVariable String email){
        return userService.delete(email);
    }
}
