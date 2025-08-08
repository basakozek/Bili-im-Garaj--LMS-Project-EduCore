package org.basak.educore.service;

import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.mapper.TeacherMapper;
import org.basak.educore.model.dto.response.TeacherClassroomResponseDto;
import org.basak.educore.model.dto.response.TeacherCourseResponseDto;
import org.basak.educore.model.dto.response.TeacherStudentResponseDto;
import org.basak.educore.model.entity.Classroom;
import org.basak.educore.model.entity.Course;
import org.basak.educore.model.entity.User;
import org.basak.educore.repository.TeacherClassroomAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final UserService userService;
    private final TeacherClassroomAssignmentRepository assignmentRepository;
    private final TeacherMapper mapper;

    public List<TeacherClassroomResponseDto> getMyClasses(User teacher) {
        List<Classroom> classrooms = assignmentRepository.findClassroomsByTeacher(teacher);
        if (classrooms.isEmpty()) {
            throw new EduCoreException(ErrorType.NO_CLASSROOM_ASSIGNED);
        }
        return classrooms.stream()
                .map(mapper::toClassroomDto)
                .toList();
    }

    public List<TeacherStudentResponseDto> getMyStudents(User teacher) {
        List<Classroom> classrooms = assignmentRepository.findClassroomsByTeacher(teacher);
        if (classrooms.isEmpty()) {
            throw new EduCoreException(ErrorType.NO_CLASSROOM_ASSIGNED);
        }

        List<User> students = userService.findStudentsByClassrooms(classrooms);
        if (students.isEmpty()) {
            throw new EduCoreException(ErrorType.NO_STUDENT_FOUND);
        }

        return students.stream()
                .map(mapper::toStudentDto)
                .toList();
    }

    public List<TeacherCourseResponseDto> getMyCourses(User teacher) {
        List<Classroom> classrooms = assignmentRepository.findClassroomsByTeacher(teacher);
        if (classrooms.isEmpty()) {
            throw new EduCoreException(ErrorType.NO_CLASSROOM_ASSIGNED);
        }

        List<Course> courses = userService.findCoursesByClassrooms(classrooms);
        if (courses.isEmpty()) {
            throw new EduCoreException(ErrorType.NO_COURSE_ASSIGNED);
        }

        return courses.stream()
                .map(mapper::toCourseDto)
                .toList();
    }
}
