package org.basak.educore.mapper;
import org.basak.educore.model.dto.response.UserResponseDto;
import org.basak.educore.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfileType().getName(),
                user.getOrganization().getName(),
                user.getClassroom() != null ? user.getClassroom().getName() : null
        );
    }
}