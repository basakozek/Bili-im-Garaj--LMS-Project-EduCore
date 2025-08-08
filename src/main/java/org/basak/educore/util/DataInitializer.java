package org.basak.educore.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.basak.educore.model.entity.*;
import org.basak.educore.repository.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final ProfileTypeRepository profileTypeRepository;
    private final BrandRepository brandRepository;
    private final OrganizationRepository organizationRepository;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final CourseRepository courseRepository;
    private final CourseAssignmentRepository courseAssignmentRepository;
    private final TeacherClassroomAssignmentRepository teacherClassroomAssignmentRepository;

    @PostConstruct
    public void init() {
        // ProfileTypes
        ProfileType superAdminType = profileTypeRepository.save(ProfileType.builder().name("SUPERADMIN").build());
        ProfileType teacherType = profileTypeRepository.save(ProfileType.builder().name("TEACHER").build());
        ProfileType studentType = profileTypeRepository.save(ProfileType.builder().name("STUDENT").build());

        // Brand
        Brand brand1 = brandRepository.save(Brand.builder().name("EduCore Inc.").code("edc1").build());

        // Organization
        Organization org1 = organizationRepository.save(Organization.builder()
                .name("Ankara Academy")
                .brand(brand1)
                .build());

        // Classroom
        Classroom classroom1 = classroomRepository.save(Classroom.builder()
                .name("Class A")
                .organization(org1)
                .build());

        // Users
        User admin= userRepository.save(User.builder()
                .firstName("Basak")
                .lastName("Ozek")
                .email("admin@educore.com")
                .password(passwordEncoder.encode("admin123"))
                .profileType(superAdminType)
                .organization(org1)
                .build());

        User teacher1= userRepository.save(User.builder()
                .firstName("Doga")
                .lastName("Aydin")
                .email("teacher@educore.com")
                .password(passwordEncoder.encode("teacher123"))
                .profileType(teacherType)
                .organization(org1)
                .build());

        User student1= userRepository.save(User.builder()
                .firstName("Dilek")
                .lastName("Kara")
                .email("student@educore.com")
                .password(passwordEncoder.encode("student123"))
                .profileType(studentType)
                .organization(org1)
                .classroom(classroom1)
                .build());
        // Course
        Course mathCourse = courseRepository.save(Course.builder()
                .name("Mathematics")
                .imageUrl("math.jpg")
                .build());

        // Assign course to classroom
        courseAssignmentRepository.save(CourseAssignment.builder()
                .classroom(classroom1)
                .course(mathCourse)
                .build());

        // Assign teacher to classroom
        teacherClassroomAssignmentRepository.save(TeacherClassroomAssignment.builder()
                .teacher(teacher1)
                .classroom(classroom1)
                .build());

        // Permissions
        List<String> resources = List.of("BRAND", "ORGANIZATION", "USER",
                "CLASSROOM", "COURSE", "COURSE_ASSIGNMENT", "TEACHER_ASSIGNMENT");

        // SuperAdmin → full access
        for (String resource : resources) {
            permissionRepository.save(Permission.builder()
                    .role(superAdminType)
                    .resourceName(resource)
                    .canCreate(true)
                    .canRead(true)
                    .canUpdate(true)
                    .canDelete(true)
                    .build());
        }

        // Teacher → read-only
        List<String> teacherReadableResources = List.of("TEACHER");
        for (String resource : teacherReadableResources) {
            permissionRepository.save(Permission.builder()
                    .role(teacherType)
                    .resourceName(resource)
                    .canCreate(false)
                    .canRead(true)
                    .canUpdate(false)
                    .canDelete(false)
                    .build());
        }

        // Student → read-only
        List<String> studentReadableResources = List.of("STUDENT");
        for (String resource : studentReadableResources) {
            permissionRepository.save(Permission.builder()
                    .role(studentType)
                    .resourceName(resource)
                    .canCreate(false)
                    .canRead(true)
                    .canUpdate(false)
                    .canDelete(false)
                    .build());
        }
    }
}
