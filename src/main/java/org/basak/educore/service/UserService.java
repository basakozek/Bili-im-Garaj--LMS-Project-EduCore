package org.basak.educore.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.mapper.UserMapper;
import org.basak.educore.model.dto.request.CreateUserRequestDto;
import org.basak.educore.model.dto.response.UserResponseDto;
import org.basak.educore.model.entity.Classroom;
import org.basak.educore.model.entity.Course;
import org.basak.educore.model.entity.Organization;
import org.basak.educore.model.entity.ProfileType;
import org.basak.educore.model.entity.User;
import org.basak.educore.repository.CourseAssignmentRepository;
import org.basak.educore.repository.OrganizationRepository;
import org.basak.educore.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper  userMapper;
    private final OrganizationService organizationService;
    private final ClassroomService classroomService;
    private final ProfileTypeService profileTypeService;
    private final PasswordEncoder passwordEncoder;
    private final CourseAssignmentRepository courseAssignmentRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new EduCoreException(ErrorType.USER_NOT_FOUND));
    }
    public User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EduCoreException(ErrorType.USER_NOT_FOUND));
    }

    @Transactional
    public UserResponseDto createUser(CreateUserRequestDto dto) {
        if (userRepository.existsByEmailIgnoreCase(dto.email())) {
            throw new EduCoreException(ErrorType.USER_ALREADY_EXISTS);
        }

        // Sadece Teacher (2) ve Student (3) yaratılabilir
        if (dto.profileId() != 2 && dto.profileId() != 3) {
            throw new EduCoreException(ErrorType.ONLY_TEACHER_OR_STUDENT_CAN_BE_CREATED);
        }

        ProfileType profileType = profileTypeService.findById(dto.profileId());
        Organization organization = organizationService.findById(dto.organizationId());

        Classroom classroom = null;
        if (dto.profileId() == 3) { // Student ise sınıf zorunlu
            if (dto.classroomId() == null) {
                throw new EduCoreException(ErrorType.CLASSROOM_REQUIRED_FOR_STUDENT);
            }
            classroom = classroomService.findById(dto.classroomId());
        }

        User user = User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .organization(organization)
                .profileType(profileType)
                .classroom(classroom)
                .build();

        user = userRepository.save(user);
        return userMapper.toResponseDto(user);
    }


    public List<UserResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new EduCoreException(ErrorType.USER_NOT_FOUND);
        }

        return users.stream()
                .map(userMapper::toResponseDto)
                .toList();
    }
    public List<User> findStudentsByClassrooms(List<Classroom> classrooms) {
        return userRepository.findByClassroomInAndProfileType_Id(classrooms, 3); // 3 = Student
    }

    public List<Course> findCoursesByClassrooms(List<Classroom> classrooms) {
        return courseAssignmentRepository.findDistinctCoursesByClassrooms(classrooms);
    }

}