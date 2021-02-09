# download bản hoàn chỉnh ở Releases nhaaaa, thank you
- Project name: Manager_wedding (quản lý nhà hàng tiệc cưới)
- Language programming: Java
- Database: MySQL Workbench 8.0 CE
- User interface (UI): console (terminal)
- IDEA: IntelliJ IDEA Community ver 2020
- UML:  Astah UML (ver community for student)
- Hướng dẫn sử dụng: 
1. Download IntelliJ Community bản mới nhất
2. Download và cài đặt MySQL bản mới nhất (link hướng dẫn download: https://openplanning.net/10221/cai-dat-co-so-du-lieu-mysql-tren-windows) nếu link die thì search google (keyword: Cài đặt cơ sở dữ liệu MySQL trên Windows)
3. Download mysql-connector-java (Nếu không down được thì trong file có sẵn thư mục đó rồi, không cần phải down, chỉ cần import vào IntelliJ là được)
4. Tạo 1 folder để chứa project or copy nguyên project (ProjectJava) trong thư mục 1951052126_NguyenThanhNam-Project vào IntelliJ
5. Kết nối MySQL với IntelliJ (project nào muốn connnect với MySQL đều phải làm bước này, chỉ làm 1 lần duy nhất trong 1 project, qua project khác thì làm lại tương tự)
5.1. Add file mysql-connector-java trong file 1951052126_NguyenThanhNam-Project vào IntelliJ (Mở IntelliJ xong add file đó vào library), nếu không biết thì link tutorial: https://www.youtube.com/watch?v=T5Hey0e2Y_g or keyword: Add MySQL Connector/J in IntelliJ IDEA)
*NOTE: Vì có sẵn data của project này rồi nên chỉ cần import data là xong. Để import được data ta làm các bước sau
5.2.1 Trong MySQL sau khi cài thì tạo 1 schemas tên là manager_wedding
5.2.2 Sau khi tạo xong 1 schemas thì ở thanh Navigator phía dưới cùng ấn Adminstration -> Data import/Restore
5.2.3 Ở Import from Disk, chọn folder Manager_wedding có trong thư mục 1951052126_NguyenThanhNam-Project, sau đó tích chọn hết (nếu nó không tự động tích sẵn). Sau đó, ấn Import Progress chọn Start Import là đã import data thành công
5.3 Về phần IntelliJ, sau khi add thư viện mysql-connector-java, add thư mục ProjectJava thành công, ở class JDBC thay đổi tên schemas (your_schemas -> manager_wedding), đổi your_password thành password khi cài đặt mysql (account:root, password: bạn đã cài trước đó)
5.4 Mình đã chia thành từng class riêng biệt và viết docs cho nó, bạn chỉ cần hover vào nó sẽ chỉ chức năng của function đó (mình viết docs rất cùi, thông cảm nha >=< )
5.5 Sử dụng or thay đổi tuỳ biến. 
P/s: project hơi cùi, hy vọng bạn thông cảm (trong file có UML, chỉ cần mở file UML đó, click chuột phải vào JavaDiagram -> Auto create class diagram -> Unpack subpackages -> Detailed) -> Done! Sơ đồ lớp chi tiết sẽ hiện thị ra.
(Cảm ơn bạn đã đọc tới đây, thank you so much, i love u 3k)
