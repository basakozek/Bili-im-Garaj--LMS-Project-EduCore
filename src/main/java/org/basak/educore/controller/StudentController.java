package org.basak.educore.controller;
import lombok.RequiredArgsConstructor;
import org.basak.educore.model.dto.response.BaseResponse;
import org.basak.educore.model.dto.response.StudentCourseResponseDto;
import org.basak.educore.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/my-courses")
    public ResponseEntity<BaseResponse<List<StudentCourseResponseDto>>> getMyCourses() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        List<StudentCourseResponseDto> courses = studentService.getMyCourses(email);

        return ResponseEntity.ok(BaseResponse.<List<StudentCourseResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Öğrencinin dersleri listelendi.")
                .data(courses)
                .build());
    }
}
