package org.basak.educore.mapper;

import org.basak.educore.model.dto.response.CourseResponseDto;
import org.basak.educore.model.dto.response.StudentCourseResponseDto;
import org.basak.educore.model.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public CourseResponseDto toResponseDto(Course course) {
        return new CourseResponseDto(
                course.getId(),
                course.getName(),
                course.getImageUrl()
        );
    }
    public StudentCourseResponseDto toStudentCourseDto(Course course) {
        return new StudentCourseResponseDto(
                course.getId(),
                course.getName(),
                course.getImageUrl()
        );
    }
}
