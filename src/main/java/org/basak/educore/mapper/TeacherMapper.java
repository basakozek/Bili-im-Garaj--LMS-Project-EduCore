package org.basak.educore.mapper;

import org.basak.educore.model.dto.response.TeacherClassroomResponseDto;
import org.basak.educore.model.dto.response.TeacherCourseResponseDto;
import org.basak.educore.model.dto.response.TeacherStudentResponseDto;
import org.basak.educore.model.entity.Classroom;
import org.basak.educore.model.entity.Course;
import org.basak.educore.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
    public TeacherClassroomResponseDto toClassroomDto(Classroom classroom) {
        return new TeacherClassroomResponseDto(
                classroom.getId(),
                classroom.getName()
        );
    }

    public TeacherStudentResponseDto toStudentDto(User student) {
        return new TeacherStudentResponseDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()
        );
    }

    public TeacherCourseResponseDto toCourseDto(Course course) {
        return new TeacherCourseResponseDto(
                course.getId(),
                course.getName(),
                course.getImageUrl()
        );
    }
}
