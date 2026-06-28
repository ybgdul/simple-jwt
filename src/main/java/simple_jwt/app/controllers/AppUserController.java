package simple_jwt.app.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import simple_jwt.app.services.AppUserService;
import simple_jwt.app.utilities.DTOs.UserRequestDto;
import simple_jwt.app.utilities.DTOs.UserResponseDto;

@RestController
@RequiredArgsConstructor
public class AppUserController {
    
    private final AppUserService userService;
    private final ModelMapper mapper;

    @PostMapping("/users/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid UserRequestDto request) { 
        
        return ResponseEntity.ok(userService.login(request));
    }

        @PostMapping("/users/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody @Valid UserRequestDto request) { 
        
        return ResponseEntity.ok(userService.signup(request));
    }
}
