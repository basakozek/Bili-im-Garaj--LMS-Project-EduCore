package org.basak.educore.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.mapper.CourseMapper;
import org.basak.educore.model.dto.request.CreateCourseRequestDto;
import org.basak.educore.model.dto.response.CourseResponseDto;
import org.basak.educore.model.entity.Course;
import org.basak.educore.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional
    public CourseResponseDto createCourse(CreateCourseRequestDto dto) {
        if (courseRepository.existsByNameIgnoreCase(dto.name())) {
            throw new EduCoreException(ErrorType.COURSE_ALREADY_EXISTS);
        }
        Course course = Course.builder()
                .name(dto.name())
                .imageUrl(dto.imageUrl())
                .build();

        course = courseRepository.save(course);
        return courseMapper.toResponseDto(course);
    }

    public Course findById(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EduCoreException(ErrorType.COURSE_NOT_FOUND));
    }
}