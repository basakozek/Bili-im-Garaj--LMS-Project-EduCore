package org.basak.educore.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.model.dto.request.AssignCourseRequestDto;
import org.basak.educore.model.dto.response.CourseAssignmentResponseDto;
import org.basak.educore.model.entity.Classroom;
import org.basak.educore.model.entity.Course;
import org.basak.educore.model.entity.CourseAssignment;
import org.basak.educore.repository.CourseAssignmentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseAssignmentService {
    private final CourseAssignmentRepository courseAssignmentRepository;
    private final CourseService courseService;
    private final ClassroomService classroomService;

    @Transactional
    public CourseAssignmentResponseDto assignCourseToClassroom(AssignCourseRequestDto dto) {
        Course course = courseService.findById(dto.courseId());
        Classroom classroom = classroomService.findById(dto.classroomId());

        boolean alreadyAssigned = courseAssignmentRepository
                .existsByCourseAndClassroom(course, classroom);

        if (alreadyAssigned) {
            throw new EduCoreException(ErrorType.COURSE_ALREADY_ASSIGNED);
        }

        CourseAssignment assignment = CourseAssignment.builder()
                .course(course)
                .classroom(classroom)
                .build();

        courseAssignmentRepository.save(assignment);
        return null;
    }
}