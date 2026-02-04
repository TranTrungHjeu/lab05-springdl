# Hướng dẫn Deploy lên Render

## Các file đã chuẩn bị

- `Dockerfile` - Build và chạy ứng dụng
- `render.yaml` - Cấu hình Render
- `application-prod.properties` - Cấu hình production

---

## Các bước Deploy

### Bước 1: Push code lên GitHub

```bash
git add .
git commit -m "Add Render deployment config"
git push origin main
```

### Bước 2: Tạo MySQL Database trên Render

1. Truy cập [render.com](https://render.com) và đăng nhập
2. Click **New +** → **MySQL** (hoặc dùng [PlanetScale](https://planetscale.com), [Railway](https://railway.app) cho MySQL free)
3. Lưu lại thông tin connection:
   - Host
   - Database name
   - Username
   - Password

### Bước 3: Deploy ứng dụng

1. Trên Render Dashboard, click **New +** → **Web Service**
2. Kết nối repository GitHub của bạn
3. Cấu hình:
   - **Name**: `student-management`
   - **Runtime**: `Docker`
   - **Branch**: `main`
4. Thêm **Environment Variables**:

   | Key | Value |
   |-----|-------|
   | `SPRING_PROFILES_ACTIVE` | `prod` |
   | `SPRING_DATASOURCE_URL` | `jdbc:mysql://HOST:3306/DATABASE` |
   | `SPRING_DATASOURCE_USERNAME` | `your_username` |
   | `SPRING_DATASOURCE_PASSWORD` | `your_password` |

5. Click **Create Web Service**

### Bước 4: Chờ deploy hoàn tất

- Render sẽ build Docker image và deploy
- Thời gian: ~5-10 phút lần đầu
- Sau khi hoàn tất, bạn sẽ có URL dạng: `https://student-management.onrender.com`

---

## Lưu ý

- **Free tier**: Ứng dụng sẽ "ngủ" sau 15 phút không hoạt động
- **Database**: Render không có MySQL free, cân nhắc dùng:
  - [PlanetScale](https://planetscale.com) - MySQL serverless miễn phí
  - [Railway](https://railway.app) - $5 free credit
  - [Aiven](https://aiven.io) - MySQL free tier
