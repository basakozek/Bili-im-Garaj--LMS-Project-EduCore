package org.basak.educore.service;

import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.mapper.CourseMapper;
import org.basak.educore.model.dto.response.StudentCourseResponseDto;
import org.basak.educore.model.entity.Classroom;
import org.basak.educore.model.entity.Course;
import org.basak.educore.model.entity.User;
import org.basak.educore.repository.CourseAssignmentRepository;
import org.basak.educore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserRepository userRepository;
    private final CourseAssignmentRepository courseAssignmentRepository;
    private final CourseMapper courseMapper;

    public List<StudentCourseResponseDto> getMyCourses(String email) {
        User student = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new EduCoreException(ErrorType.USER_NOT_FOUND));

        Classroom classroom = student.getClassroom();
        if (classroom == null) {
            throw new EduCoreException(ErrorType.CLASSROOM_NOT_FOUND);
        }

        List<Course> courses = courseAssignmentRepository.findCoursesByClassroom(classroom);

        if (courses.isEmpty()) {
            throw new EduCoreException(ErrorType.NO_COURSE_FOUND);
        }

        return courses.stream()
                .map(courseMapper::toStudentCourseDto)
                .toList();
    }

}