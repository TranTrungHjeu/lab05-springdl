# Câu trả lời các câu hỏi bắt buộc

## 1. StudentRepository có phải là Bean không? Vì sao?

**Có**, `StudentRepository` là một Bean.

**Lý do:**
- Được đánh dấu với annotation `@Repository`, đây là một loại stereotype annotation của Spring
- Spring tự động tạo implementation cho interface này thông qua Spring Data JPA
- Spring Container quản lý lifecycle của Bean này
- Bean này có thể được inject vào các class khác thông qua DI

## 2. DI được sử dụng ở những class nào?

DI (Dependency Injection) được sử dụng tại:

1. **StudentServiceImpl** (service/StudentServiceImpl.java:15)
   - Inject `StudentRepository` qua Constructor Injection
   ```java
   public StudentServiceImpl(StudentRepository repository) {
       this.repository = repository;
   }
   ```

2. **StudentController** (controller/StudentController.java:14)
   - Inject `StudentService` qua Constructor Injection
   ```java
   public StudentController(StudentService service) {
       this.service = service;
   }
   ```

## 3. Nếu có thêm MongoStudentRepository thì Spring có lỗi không? Vì sao?

**Có**, Spring sẽ báo lỗi.

**Lý do:**
- Spring sẽ có 2 Bean cùng implement interface `StudentRepository` (hoặc cùng kiểu)
- Khi inject vào `StudentServiceImpl`, Spring không biết nên chọn Bean nào
- Lỗi: `NoUniqueBeanDefinitionException`

**Giải pháp:**
- Sử dụng `@Primary` để đánh dấu Bean ưu tiên
- Sử dụng `@Qualifier("beanName")` để chỉ định Bean cụ thể
- Ví dụ:
  ```java
  public StudentServiceImpl(@Qualifier("studentRepository") StudentRepository repository) {
      this.repository = repository;
  }
  ```

## 4. Constructor Injection có lợi ích gì so với Field Injection?

**Lợi ích của Constructor Injection:**

1. **Immutability (Tính bất biến)**
   - Có thể khai báo field là `final`
   - Đảm bảo dependency không bị thay đổi sau khi khởi tạo
   
2. **Testability (Dễ test)**
   - Dễ dàng mock dependencies trong unit test
   - Không cần Spring Container để tạo object
   ```java
   // Dễ test
   StudentRepository mockRepo = mock(StudentRepository.class);
   StudentService service = new StudentServiceImpl(mockRepo);
   ```

3. **Mandatory Dependencies (Bắt buộc phải có)**
   - Constructor đảm bảo tất cả dependencies được cung cấp khi tạo object
   - Không thể tạo object với dependency là null
   
4. **Explicit Dependencies (Rõ ràng)**
   - Dễ nhận biết class phụ thuộc vào những gì
   - Constructor với quá nhiều tham số → cảnh báo class vi phạm Single Responsibility Principle

5. **Framework Independence (Độc lập framework)**
   - Không phụ thuộc vào annotation của Spring
   - Code sạch hơn, tuân thủ POJO

**So sánh với Field Injection:**
```java
// Field Injection - KHÔNG NÊN dùng
@Autowired
private StudentRepository repository; // Không final, khó test, dependency ẩn
```

---

# Hướng dẫn chạy ứng dụng

## 1. Tạo database MySQL
```sql
CREATE DATABASE studentdb;
```

## 2. Cấu hình database
Kiểm tra `application.properties` và điều chỉnh username/password nếu cần

## 3. Chạy ứng dụng
```bash
mvn spring-boot:run
```

## 4. Test API

### Thêm sinh viên
```bash
curl -X POST http://localhost:8080/students \
  -H "Content-Type: application/json" \
  -d '{"name":"Nguyen Van A","email":"a@example.com","age":20}'
```

### Xem danh sách
```bash
curl http://localhost:8080/students
```

### Tìm theo ID
```bash
curl http://localhost:8080/students/1
```
