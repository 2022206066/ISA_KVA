package org.aleksa.mappers;

import org.aleksa.dtos.UserDTO;
import org.aleksa.entities.User;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between User entity and UserDTO.
 */
@Component
public class UserMapper {

    /**
     * Convert User entity to UserDTO.
     *
     * @param user The user entity to convert
     * @return UserDTO representation
     */
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setAdmin(user.getRole() == User.Role.ADMIN);
        // We don't map password for security reasons
        
        return dto;
    }

    /**
     * Convert UserDTO to User entity.
     * Note: This doesn't set the ID as that's typically managed by the database.
     *
     * @param dto The DTO to convert
     * @return User entity
     */
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getAdmin() ? User.Role.ADMIN : User.Role.USER);
        // Password should be handled separately with proper encryption
        
        return user;
    }
} 