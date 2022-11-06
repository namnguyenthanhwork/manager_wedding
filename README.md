# Quản lý nhà hàng tiệc cưới - Java

<b>`Mô tả:`</b> Bài tập lớn giữa kỳ môn Lập trình hướng đối tượng - OOP và chạy giao diện bằng console.

🕵️‍♀️ Tác giả: Thành Nam Nguyễn

## 📑 Các ứng dụng sử dụng trong bài tập lớn

- <b>Database</b>: MySQL Workbench 8.0 CE.
- <b>IDEA</b>: IntelliJ IDEA Community (phiên bản 2020).
- <b>UML</b>: Astah UML (phiên bản education).

## 📝 Các tính năng

- Quản lý sảnh.
- Quản lý thực phẩm.
- Quản lý dịch vụ.
- Quản lý bill.
- Quản lý danh sách các menu.
- Quản lý bảng giá sảnh.
- Thống kê.

## ⚡ Hướng dẫn cài đặt

<b>📌 Bước 1: Công cụ lập trình</b>

Sử dụng 1 IDEA bất kỳ để lập trình Java như: [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows), [Apache NetBeans](https://netbeans.apache.org/download/index.html), [Eclipse IDE](https://www.eclipse.org/downloads/packages/release/kepler/sr2/eclipse-ide-java-developers) hoặc các công cụ để lập trình ngôn ngữ Java khác.

Trong bài tập lớn này sử dụng công cụ `IntelliJ IDEA` phiên bản 2020 để lập trình.

👉 Link tải: https://www.jetbrains.com/idea/download/#section=windows

<b>📌 Bước 2: Cơ sở dữ liệu (Database)</b>

Trong bài tập lớn này sử dụng database `MySQL` phiên bản `MySQL Workbench 8.0 CE` để lưu trữ dữ liệu.

👉 Link tải MySQL: https://dev.mysql.com/downloads/mysql/

👉 Hướng dẫn cài đặt MySQL: https://openplanning.net/10221/cai-dat-co-so-du-lieu-mysql-tren-windows (tham khảo)

<b>📌 Bước 3: Tải bộ kết nối IDE hoặc IDEA với Database</b>

Sử dụng `mysql-connector-java` để kết nối giữa code Java với MySQL (dùng để thao tác và truy vấn dữ liệu).

<p><b>🔍 Hướng dẫn tải</b></p>

Đến trang Microsoft và chọn Platform Independent (tải file zip).

![download mysql-connector-java](https://github.com/namnguyenthanhwork/manager_wedding/blob/main/img%20docs/download%20mysql.png?raw=true)

👉 Link tải: https://dev.mysql.com/downloads/connector/j/

👉 Tham khảo hướng dẫn tải, cài đặt vả kết nối tại: https://viettuts.vn/java-jdbc/ket-noi-java-voi-mysql

<b>📌 Bước 4: Tạo database và thêm dữ liệu mẫu</b>

Kết nối MySQL với IntelliJ bằng `mysql-connector-java` 1 lần duy nhất, nếu chuyển qua các project khác cũng làm tương tự.

👉 Cách kết nối: Mở IntelliJ và thêm `mysql-connector-java` vào trong library. Xem hướng dẫn tại: https://www.youtube.com/watch?v=T5Hey0e2Y_g

👉 <b>Thêm dữ liệu mẫu:</b>

1. Trong MySQL sau khi cài thì tạo 1 database tên là manager_wedding (chuột phải vào phần bên trái - SCHEMA để ấn nút tạo sau đó đặt tên và ấn apply)

![create db](https://github.com/namnguyenthanhwork/manager_wedding/blob/main/img%20docs/create%20db.png?raw=true)

2. Sau khi tạo xong 1 database thì ở thanh Navigator phía dưới cùng ấn Adminstration -> Data import/Restore để phục hồi dữ liệu mẫu

![restore db](https://github.com/namnguyenthanhwork/manager_wedding/blob/main/img%20docs/restore%20db%201.png?raw=true)

3. Folder Manager_wedding (ở trên github) có các file sql để phục hồi dữ liệu mẫu.

![restore db](https://github.com/namnguyenthanhwork/manager_wedding/blob/main/img%20docs/db.png?raw=true)

Tiến hành download về sau đó ở tab Import from Disk (bên phía MySQL) ấn chọn dấu `...`

![restore db](https://github.com/namnguyenthanhwork/manager_wedding/blob/main/img%20docs/restore%20db%202.png?raw=true)

chọn folder chứa tất cả các file .sql để phục hồi dữ liệu

![restore db](https://github.com/namnguyenthanhwork/manager_wedding/blob/main/img%20docs/restore%20db%203.png?raw=true)

các file sẽ được phục hồi

![restore db](https://github.com/namnguyenthanhwork/manager_wedding/blob/main/img%20docs/restore%20db%204.png?raw=true)

Sau đó, ở tab Import Progress chọn Start Import.

![restore db](https://github.com/namnguyenthanhwork/manager_wedding/blob/main/img%20docs/restore%20db%205.png?raw=true)

impport thành công

![restore db](https://github.com/namnguyenthanhwork/manager_wedding/blob/main/img%20docs/restore%20db%206.png?raw=true)

kiểm tra database có đã có các bảng dữ liệu

![restore db](https://github.com/namnguyenthanhwork/manager_wedding/blob/main/img%20docs/restore%20db%207.png?raw=true)

<b>📌 Bước 5: Kết nối IDE hoặc IDEA với Database</b>

Mở thư mục `ProjectJava` tìm đến thư mục `JDBC` và trong file `ConnectSQL.java` thay đổi `DB_URL (your_schemas)` và `PASSWORD` trùng với tên `db & password` trong `MySQL`

```java
public ConnectSQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String DB_URL = "jdbc:mysql://localhost:3306/your_schemas?autoReconnect=true&useSSL=false";
			String USER_NAME = "root";
			String PASSWORD = "your password";
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		} catch (Exception ex) {
			System.out.println("Connect failure!");
			ex.printStackTrace();
		}
	}
```

👉 Tham khảo hướng dẫn tải, cài đặt vả kết nối tại: https://viettuts.vn/java-jdbc/ket-noi-java-voi-mysql

<b>📌 Bước 6: Chạy chương trình</b>

Sau khi tạo database, thêm dữ liệu và kết nối MySQL với IntelliJ. Mở thư mục `ProjectJava` với `IntelliJ IDEA` để chạy chương trình.

--- HẾT ---
