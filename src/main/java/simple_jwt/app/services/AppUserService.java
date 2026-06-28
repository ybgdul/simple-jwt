package simple_jwt.app.services;

import java.util.List;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import simple_jwt.app.entities.AppUser;
import simple_jwt.app.repositories.AppUserRepo;
import simple_jwt.app.utilities.DTOs.UserRequestDto;
import simple_jwt.app.utilities.DTOs.UserResponseDto;
import simple_jwt.app.utilities.Enums.AppUserRoles;
import simple_jwt.app.utilities.Exceptions.UserAlreadyExistsException;

@Service
@RequiredArgsConstructor
public class AppUserService {
    
    private final AppUserRepo userRepo;
    private final PasswordEncoder encoder;
    private final JwtTokenService jwtService;
    private static Logger log = LoggerFactory.getLogger(AppUserService.class);
    private final AuthenticationManager authenticationManager;

    @Transactional
    public UserResponseDto signup(UserRequestDto request) { 
        
        if(userRepo.existsByUsername(request.username())) throw new UserAlreadyExistsException("User already exists: " + request.username());

        AppUser user = new AppUser();
        user.setEmail(request.email());
        user.setUsername(request.username());
        user.setHashedPassword(encoder.encode(request.password()));
        user.setAppUserRoles(List.of(AppUserRoles.USER));

        userRepo.saveAndFlush(user);

        String token = jwtService.generateToken(new User(user.getUsername(), user.getHashedPassword(), user.getAppUserRoles()));

        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), token);
    }

    @Transactional
    public UserResponseDto login(UserRequestDto request) { 

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        AppUser user = userRepo.findByUsername(request.username()).orElseThrow(() -> new UsernameNotFoundException("Username not found: " + request.username()));

        String token = jwtService.generateToken(new User(user.getUsername(), user.getHashedPassword(), user.getAppUserRoles()));

        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail(), token);
    }


}
