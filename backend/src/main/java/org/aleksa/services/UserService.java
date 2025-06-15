package org.aleksa.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.aleksa.dtos.AuthDTO;
import org.aleksa.dtos.UserDTO;
import org.aleksa.security.util.JwtUtil;
import org.aleksa.entities.User;
import org.aleksa.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO createUser(AuthDTO user) {
        return userToDto(userRepo.save(userFromDto(user)));
    }

    public UserDTO refreshUser(AuthDTO user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb == null)
            return null;
        else if (isCorrectPassword(user.getPassword(), userFromDb.getPassword()))
            return userToDto(userFromDb);
        else
            return null;
    }

    public UserDTO updateUser(UserDTO userDto) {
        User user = userRepo.findById(userDto.getId()).orElse(null);
        if (user != null) {
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setPhone(userDto.getPhone());
            user.setAddress(userDto.getAddress());
            user.setFavoriteGenres(userDto.getFavoriteGenres());
            return userToDto(userRepo.save(user));
        }
        return null;
    }

    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream().map(this::userToDtoNoToken).collect(Collectors.toList());
    }
    
    public UserDTO findByUsername(String username) {
        User user = userRepo.findByUsername(username);
        return user != null ? userToDto(user) : null;
    }

    private UserDTO userToDto(User user) {
        UserDTO userDto = new UserDTO(user);
        
        // Create claims for JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().toString());
        
        // Generate JWT token
        userDto.setToken(JwtUtil.createJWT(userDto.getUsername(), claims));
        return userDto;
    }

    private UserDTO userToDtoNoToken(User user) {
        UserDTO userDto = new UserDTO(user);
        userDto.setToken("????");
        return userDto;
    }

    private User userFromDto(AuthDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setRole(User.Role.USER);
        user.setEmail(dto.getEmail());
        user.setPassword(BCrypt.withDefaults().hashToString(12, dto.getPassword().toCharArray()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setFavoriteGenres(dto.getFavoriteGenres());
        return user;
    }

    private boolean isCorrectPassword(String password, String hashedPassword) {
        return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword.toCharArray()).verified;
    }
}
