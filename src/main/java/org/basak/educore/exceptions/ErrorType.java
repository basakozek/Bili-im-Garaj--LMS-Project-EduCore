package org.basak.educore.exceptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorType {
    INTERNAL_SERVER_ERROR(500, "SUNUCUDA BEKLENMEYEN HATA.", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(400, "Girilen parametreler hatalıdır. Kontrol ediniz.", HttpStatus.BAD_REQUEST),
    JSON_CONVERT_ERROR(300, "Girilen parametreler hatalıdır. Json Dönüşüm Hatası.", HttpStatus.BAD_REQUEST),
    DUPLICATE_KEY(3000, "Benzersiz alan hatası.", HttpStatus.BAD_REQUEST),
    DATA_INTEGRITY_ERROR(3001, "Veri bütünlüğü hatası", HttpStatus.BAD_REQUEST),
    BAD_REQUEST_ERROR(4000, "Parametre hatası", HttpStatus.BAD_REQUEST),

    INVALID_TOKEN(4001, "Geçersiz token bilgisi.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(4002, "Şifre yanlış.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4003, "Kullanıcı bulunamadı.", HttpStatus.NOT_FOUND),
    ROLE_NOT_ASSIGNED(4004, "Kullanıcıya atanmış bir rol bulunamadı.", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN(4005, "Refresh token geçersiz", HttpStatus.BAD_REQUEST),
    EXPIRED_REFRESH_TOKEN(4006, "Refresh tokenın süresi doldu ", HttpStatus.BAD_REQUEST),
    RESET_CODE_ALREADY_SENT(4007, "Şifre sıfırlama kodu zaten gönderilmiş", HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH_ERROR(4008, "Şifreler uyuşmuyor.", HttpStatus.BAD_REQUEST),
    INVALID_RESET_CODE(4009, "Şifre sıfırlama kodunu hatalı girdiniz", HttpStatus.BAD_REQUEST),
    PASSWORD_SAME_AS_OLD(4010, "Yeni şifreniz eski şifrenizle aynı olamaz", HttpStatus.BAD_REQUEST),
    BRAND_ALREADY_EXISTS(4011, "Bu isimde bir marka zaten mevcut.",HttpStatus.BAD_REQUEST),
    BRAND_CODE_ALREADY_EXISTS(4012, "Bu kod başka bir marka tarafından kullanılıyor.",HttpStatus.BAD_REQUEST),
    BRAND_HAS_ORGANIZATIONS(4013, "Bu markaya bağlı organizasyonlar olduğu için silinemez.",HttpStatus.BAD_REQUEST),
    BRAND_NOT_FOUND(4014, "Marka bulunamadı.",HttpStatus.BAD_REQUEST),
    NO_PERMISSION(4015,"Bu işlem için yetkiniz yok." ,HttpStatus.BAD_REQUEST ),
    UNAUTHORIZED(4016,"Yetkisiz erişim. Lütfen geçerli bir oturum açın." ,HttpStatus.BAD_REQUEST ),
    ORGANIZATION_ALREADY_EXISTS(4017, "Bu organizasyon adı zaten kullanılıyor.",HttpStatus.BAD_REQUEST),
    ORGANIZATION_NOT_FOUND(4018, "Organizasyon bulunamadı.",HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS(4019,"Bu email adresi ile kayıtlı bir kullanıcı zaten mevcut.",HttpStatus.BAD_REQUEST),
    ONLY_TEACHER_OR_STUDENT_CAN_BE_CREATED(4020,"Sadece Teacher veya Student profiline sahip kullanıcılar oluşturulabilir.",HttpStatus.BAD_REQUEST),
    CLASSROOM_REQUIRED_FOR_STUDENT(4021,"Öğrenci oluşturmak için sınıf bilgisi zorunludur.",HttpStatus.BAD_REQUEST),
    PROFILE_TYPE_NOT_FOUND(4022,"Profil tipi bulunamadı." ,HttpStatus.BAD_REQUEST ),
    CLASSROOM_NOT_FOUND(4023,"Sınıf bulunamadı" ,HttpStatus.BAD_REQUEST ),
    COURSE_NOT_FOUND(4024, "Ders bulunamadı", HttpStatus.BAD_REQUEST),
    COURSE_ALREADY_ASSIGNED(4025, "Bu ders zaten bu sınıfa atanmış", HttpStatus.BAD_REQUEST),
    USER_IS_NOT_TEACHER(4026, "Yalnızca öğretmenler sınıfa atanabilir", HttpStatus.BAD_REQUEST),
    TEACHER_ALREADY_ASSIGNED(4027, "Bu öğretmen zaten bu sınıfa atanmış", HttpStatus.BAD_REQUEST),
    COURSE_ALREADY_EXISTS(4027, "Bu isimde bir ders zaten mevcut.", HttpStatus.BAD_REQUEST),
    CLASSROOM_ALREADY_EXISTS(4028, "Bu isimde bir sınıf bu organizasyonda zaten mevcut.", HttpStatus.BAD_REQUEST),
    NO_CLASSROOM_ASSIGNED(4029,"Atanmış sınıf bulunamadı" ,HttpStatus.BAD_REQUEST ),
    NO_STUDENT_FOUND(4030,"Öğrenci bulunamadı" ,HttpStatus.BAD_REQUEST ),
    NO_COURSE_ASSIGNED(4031,"Atanmış ders bulunamadı" ,HttpStatus.BAD_REQUEST ),
    NO_COURSE_FOUND(4032,"Öğrencinin kayıtlı olduğu bir ders bulunamadı." ,HttpStatus.BAD_REQUEST ),;

    int code;
    String message;
    HttpStatus httpStatus;

}
