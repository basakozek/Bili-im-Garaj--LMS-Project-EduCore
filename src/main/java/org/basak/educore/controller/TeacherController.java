package org.basak.educore.controller;

import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.model.dto.response.BaseResponse;
import org.basak.educore.model.dto.response.TeacherClassroomResponseDto;
import org.basak.educore.model.dto.response.TeacherCourseResponseDto;
import org.basak.educore.model.dto.response.TeacherStudentResponseDto;
import org.basak.educore.model.dto.response.UserResponseDto;
import org.basak.educore.model.entity.ProfileType;
import org.basak.educore.model.entity.User;
import org.basak.educore.service.PermissionService;
import org.basak.educore.service.TeacherService;
import org.basak.educore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final UserService userService;
    private final PermissionService permissionService;

    @GetMapping("/my-classes")
    public ResponseEntity<BaseResponse<List<TeacherClassroomResponseDto>>> getMyClasses() {
        User teacher = getAuthenticatedUser();
        checkPermission("TEACHER", "read");
        List<TeacherClassroomResponseDto> list = teacherService.getMyClasses(teacher);

        return ResponseEntity.ok(BaseResponse.<List<TeacherClassroomResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Sınıflar listelendi.")
                .data(list)
                .build());
    }
    @GetMapping("/my-students")
    public ResponseEntity<BaseResponse<List<TeacherStudentResponseDto>>> getMyStudents() {
        User teacher = getAuthenticatedUser();
        List<TeacherStudentResponseDto> list = teacherService.getMyStudents(teacher);

        return ResponseEntity.ok(BaseResponse.<List<TeacherStudentResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Öğrenciler listelendi.")
                .data(list)
                .build());
    }

    @GetMapping("/my-courses")
    public ResponseEntity<BaseResponse<List<TeacherCourseResponseDto>>> getMyCourses() {
        User teacher = getAuthenticatedUser();
        List<TeacherCourseResponseDto> list = teacherService.getMyCourses(teacher);

        return ResponseEntity.ok(BaseResponse.<List<TeacherCourseResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Dersler listelendi.")
                .data(list)
                .build());
    }

    private void checkPermission(String resource, String action) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new EduCoreException(ErrorType.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetails userDetails)) {
            throw new EduCoreException(ErrorType.UNAUTHORIZED);
        }

        String email = userDetails.getUsername();
        User user = userService.findByEmail(email);

        ProfileType role = user.getProfileType();
        boolean hasAccess = permissionService.hasPermission(role, resource, action);

        if (!hasAccess) {
            throw new EduCoreException(ErrorType.NO_PERMISSION);
        }
    }
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new EduCoreException(ErrorType.UNAUTHORIZED);
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetails userDetails)) {
            throw new EduCoreException(ErrorType.UNAUTHORIZED);
        }

        return userService.findByEmail(userDetails.getUsername());
    }
}
