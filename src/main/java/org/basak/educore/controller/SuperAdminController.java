package org.basak.educore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.basak.educore.exceptions.EduCoreException;
import org.basak.educore.exceptions.ErrorType;
import org.basak.educore.model.dto.request.AssignCourseRequestDto;
import org.basak.educore.model.dto.request.AssignTeacherRequestDto;
import org.basak.educore.model.dto.request.CreateBrandRequestDto;
import org.basak.educore.model.dto.request.CreateClassroomRequestDto;
import org.basak.educore.model.dto.request.CreateCourseRequestDto;
import org.basak.educore.model.dto.request.CreateOrganizationRequestDto;
import org.basak.educore.model.dto.request.CreateUserRequestDto;
import org.basak.educore.model.dto.request.UpdateBrandRequestDto;
import org.basak.educore.model.dto.response.BrandResponseDto;
import org.basak.educore.model.dto.response.ClassroomResponseDto;
import org.basak.educore.model.dto.response.CourseAssignmentResponseDto;
import org.basak.educore.model.dto.response.CourseResponseDto;
import org.basak.educore.model.dto.response.OrganizationResponseDto;
import org.basak.educore.model.dto.response.TeacherClassroomAssignmentResponseDto;
import org.basak.educore.model.dto.response.UserResponseDto;
import org.basak.educore.model.entity.ProfileType;
import org.basak.educore.model.dto.response.BaseResponse;
import org.basak.educore.model.entity.User;
import org.basak.educore.service.BrandService;
import org.basak.educore.service.ClassroomService;
import org.basak.educore.service.CourseAssignmentService;
import org.basak.educore.service.CourseService;
import org.basak.educore.service.OrganizationService;
import org.basak.educore.service.PermissionService;
import org.basak.educore.service.TeacherClassroomAssignmentService;
import org.basak.educore.service.UserService;
import org.basak.educore.util.JwtManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final BrandService brandService;
    private final PermissionService permissionService;
    private final UserService userService;
    private final OrganizationService organizationService;
    private final ClassroomService classroomService;
    private final CourseService courseService;
    private final CourseAssignmentService courseAssignmentService;
    private final TeacherClassroomAssignmentService  teacherAssignmentService;


    @PostMapping("/brands")
    public ResponseEntity<BaseResponse<BrandResponseDto>> createBrand(@RequestBody @Valid CreateBrandRequestDto dto) {
        checkPermission("BRAND", "create");

        BrandResponseDto created = brandService.createBrand(dto);
        return ResponseEntity.ok(BaseResponse.<BrandResponseDto>builder()
                .code(200)
                .success(true)
                .message("Marka oluşturuldu.")
                .data(created)
                .build());
    }

    @GetMapping("/brands")
    public ResponseEntity<BaseResponse<List<BrandResponseDto>>> findAllBrands() {
        checkPermission("BRAND", "read");

        List<BrandResponseDto> list = brandService.findAllBrand();
        return ResponseEntity.ok(BaseResponse.<List<BrandResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Markalar listelendi.")
                .data(list)
                .build());
    }

    @PutMapping("/brands/{id}")
    public ResponseEntity<BaseResponse<BrandResponseDto>> updateBrand(@PathVariable UUID id,
                                                                      @RequestBody @Valid UpdateBrandRequestDto dto) {
        checkPermission("BRAND", "update");

        BrandResponseDto updated = brandService.updateBrand(id, dto);
        return ResponseEntity.ok(BaseResponse.<BrandResponseDto>builder()
                .code(200)
                .success(true)
                .message("Marka güncellendi.")
                .data(updated)
                .build());
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteBrand(@PathVariable UUID id) {
        checkPermission("BRAND", "delete");

        brandService.deleteBrand(id);
        return ResponseEntity.ok(BaseResponse.<Void>builder()
                .code(200)
                .success(true)
                .message("Marka silindi.")
                .data(null)
                .build());
    }
    @PostMapping("/organizations")
    public ResponseEntity<BaseResponse<OrganizationResponseDto>> createOrganization(
            @RequestBody @Valid CreateOrganizationRequestDto dto) {

        checkPermission("ORGANIZATION", "create");

        OrganizationResponseDto created = organizationService.createOrganization(dto);
        return ResponseEntity.ok(BaseResponse.<OrganizationResponseDto>builder()
                .code(200)
                .success(true)
                .message("Organizasyon oluşturuldu.")
                .data(created)
                .build());
    }

    @GetMapping("/organizations")
    public ResponseEntity<BaseResponse<List<OrganizationResponseDto>>> getAllOrganizations() {

        checkPermission("ORGANIZATION", "read");

        List<OrganizationResponseDto> list = organizationService.findAllOrganization();
        return ResponseEntity.ok(BaseResponse.<List<OrganizationResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Organizasyonlar listelendi.")
                .data(list)
                .build());
    }
    @PostMapping("/users")
    public ResponseEntity<BaseResponse<UserResponseDto>> createUser(
            @RequestBody @Valid CreateUserRequestDto dto) {
        checkPermission("USER", "create");

        UserResponseDto created = userService.createUser(dto);
        return ResponseEntity.ok(BaseResponse.<UserResponseDto>builder()
                .code(200)
                .success(true)
                .message("Kullanıcı oluşturuldu.")
                .data(created)
                .build());
    }

    @GetMapping("/users")
    public ResponseEntity<BaseResponse<List<UserResponseDto>>> findAllUsers() {
        checkPermission("USER", "read");

        List<UserResponseDto> list = userService.findAllUsers();
        return ResponseEntity.ok(BaseResponse.<List<UserResponseDto>>builder()
                .code(200)
                .success(true)
                .message("Kullanıcılar listelendi.")
                .data(list)
                .build());
    }
    @PostMapping("/classrooms")
    public ResponseEntity<BaseResponse<ClassroomResponseDto>> createClassroom(
            @RequestBody CreateClassroomRequestDto dto) {
        checkPermission("CLASSROOM", "create");
        ClassroomResponseDto created = classroomService.createClassroom(dto);
        return ResponseEntity.ok(BaseResponse.<ClassroomResponseDto>builder()
                .code(200)
                .success(true)
                .message("Sınıf oluşturuldu.")
                .data(created)
                .build());
    }

    @PostMapping("/courses")
    public ResponseEntity<BaseResponse<CourseResponseDto>> createCourse(
            @RequestBody CreateCourseRequestDto dto) {
        checkPermission("COURSE", "create");
        CourseResponseDto created = courseService.createCourse(dto);
        return ResponseEntity.ok(BaseResponse.<CourseResponseDto>builder()
                .code(200)
                .success(true)
                .message("Kurs oluşturuldu.")
                .data(created)
                .build());
    }

    @PostMapping("/courses/assign")
    public ResponseEntity<BaseResponse<CourseAssignmentResponseDto>> assignCourseToClassroom(
            @RequestBody AssignCourseRequestDto dto) {
        checkPermission("COURSE_ASSIGNMENT", "create");
        CourseAssignmentResponseDto created = courseAssignmentService.assignCourseToClassroom(dto);
        return ResponseEntity.ok(BaseResponse.<CourseAssignmentResponseDto>builder()
                .code(200)
                .success(true)
                .message("Kurs sınıfa atandı.")
                .data(created)
                .build());
    }

    @PostMapping("/teachers/assign-classroom")
    public ResponseEntity<BaseResponse<TeacherClassroomAssignmentResponseDto>> assignTeacherToClassroom(
            @RequestBody AssignTeacherRequestDto dto) {
        checkPermission("TEACHER_ASSIGNMENT", "create");
        TeacherClassroomAssignmentResponseDto created = teacherAssignmentService.assignTeacherToClassroom(dto);
        return ResponseEntity.ok(BaseResponse.<TeacherClassroomAssignmentResponseDto>builder()
                .code(200)
                .success(true)
                .message("Öğretmen sınıfa atandı.")
                .data(created)
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


}