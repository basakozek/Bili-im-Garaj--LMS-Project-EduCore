
Proje Adı: EduCore 

Projeyi geliştiren: Başak Özek

Projeyi Bitirme Tarihi: 08.08.2025 20.00
Teslim Tarihi: 09.08.2025 00.30

Projeyi geliştirirken şehir dışında olduğumdan ve kişisel bilgisayarım yanımda olmadığından geçici olarak babamın iş bilgisayarını kullanmak durumunda kaldım. Ancak cihazın kurumsal güvenlik duvarları nedeniyle projeyi harici bir platforma (GitHub, WhatsApp, Google Drive vb.) aktarmam mümkün olmadı. Bu nedenle, proje dosyalarını manuel olarak farklı bir bilgisayara aktarıp yeniden yapılandırmam gerekti.

Bu durum, kısıtlı zaman ve imkânlarım nedeniyle projenin GitHub’a yüklenme sürecinde gecikmeye sebep olmuştur. Anlayışınız için teşekkür ederim.



EduCore, eğitim kurumları için kullanıcı (öğrenci, öğretmen, yönetici) ve içerik yönetimini sağlayan bir backend servisidir. Bu proje Spring Boot, JPA, Hibernate, PostgreSQL, Spring Security ve JWT teknolojileri ile geliştirilmiştir.

🚀 Kurulum

-Proje sayfasında <>Code butonuna tıklayarak projeyi zip dosyası olarak indirebilirsiniz.

📚 Swagger UI

-Tüm endpoint dökümantasyonuna buradan ulaşabilirsiniz:

🔗 http://localhost:8080/swagger-ui/index.html

🔐 Yetkilendirme

-Tüm endpointlere erişim JWT (Bearer Token) ile sağlanmaktadır.

-Swagger UI'da yetkili işlem yapmak için sağ üstte kilit şeklindeki “Authorize” butonuna tıklayıp JWT token'ınızı girin. Token almak için önce giriş yapmalısınız. Her farklı rol için deneme yapmanız gerektiğinde işlemi tekrarlamalısınız.

-Lazım olan örnek verileri DataInitializer sınıfında bulabilir, endpointleri deneyerek yeni veriler oluşturabilirsiniz.
