package Service;

import JDBC.ConnectSQL;

import Service.ServiceList.Decoration;
import Service.ServiceList.Karaoke;
import Service.ServiceList.Service;
import Service.ServiceList.Singer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

public class ManagerService {
	public ManagerService() {
	}
	
	/**
	 * add new service to product list
	 * @param service the service type (singer, decoration...) value
	 */
	public void addService(Service service) {
		Scanner scanner = new Scanner(System.in);
		service.inputService(scanner);
	}
	
	/**
	 * update new service in service list
	 * @param serviceNewName the new service name value
	 * @param serviceCode the service code value
	 */
	public void updateServiceName(String serviceNewName, String serviceCode) {
		boolean flag = false;
		String serviceCodeTemp;
		serviceCodeTemp = serviceCode;
		String serviceType = serviceCodeTemp.split("[0-9]")[0];
		String serviceCodeDB;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from %s",
					serviceType.toLowerCase()));
			
			while (rs.next()) {
				serviceCodeDB = rs.getString(serviceType.toLowerCase() + "_code");
				if (serviceCodeDB.equalsIgnoreCase(serviceCode)) {
					st.executeUpdate(String.format("update %s set %s_name = '%s' where %s_code = '%s'",
							serviceType.toLowerCase(), serviceType.toLowerCase(),
							serviceNewName, serviceType.toLowerCase(), serviceCode));
					System.out.println("Update thành công");
					flag = true;
					break;
				}
			}
			
			if (!flag)
				System.out.println("<!>Không tìm thấy");
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * update new service price in service list
	 * @param serviceNewPrice the new service price value
	 * @param serviceCode the service code value
	 */
	public void updateServicePrice(double serviceNewPrice, String serviceCode) {
		boolean flag = false;
		String serviceCodeTemp;
		serviceCodeTemp = serviceCode;
		String serviceType = serviceCodeTemp.split("[0-9]")[0];
		String serviceCodeDB;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(
					String.format("select * from %s", serviceType.toLowerCase()));
			
			while (rs.next()) {
				serviceCodeDB = rs.getString(serviceType.toLowerCase() + "_code");
				
				if (serviceCodeDB.equalsIgnoreCase(serviceCode)) {
					st.executeUpdate(String.format("update %s set %s_cost = '%f' where %s_code = '%s'",
							serviceType.toLowerCase(), serviceType.toLowerCase(),
							serviceNewPrice, serviceType.toLowerCase(), serviceCode));
					System.out.println("Update thành công");
					flag = true;
					break;
				}
			}
			if (!flag)
				System.out.println("<!>Không tìm thấy");
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * check service delete successful or failed
	 * @param serviceCode the service code value
	 * @return true if delete successful else return false
	 */
	public boolean deleteService(String serviceCode) {
		String serviceCodeTemp;
		serviceCodeTemp = serviceCode;
		String serviceType = serviceCodeTemp.split("[0-9]")[0];
		String serviceCodeDB;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from " + serviceType.toLowerCase());
			
			while (rs.next()) {
				serviceCodeDB = rs.getString(serviceType.toLowerCase() + "_code");
				if (serviceCodeDB.equalsIgnoreCase(serviceCode)) {
					// delete product in db
					try {
						st.executeUpdate(String.format("delete from %s where %s = '%s';",
								serviceType.toLowerCase(),
								serviceType.toLowerCase() + "_code", serviceCode.toUpperCase()));
						st.executeUpdate(String.format("alter table %s drop id;",
								serviceType.toUpperCase()));
						st.executeUpdate(String.format("alter table %s auto_increment = 1;",
								serviceType.toUpperCase()));
						st.executeUpdate(
								String.format("alter table %s add id int not null auto_increment primary key first;",
										serviceType.toUpperCase()));
						
						// update amount in table service
						st.executeQuery("select * from service");
						rs = st.getResultSet();
						if (rs.next()) {
							int amount = rs.getInt("amount_" + serviceType.toLowerCase());
							amount--;
							st.executeUpdate(String.format("update service set amount_%s = %d",
									serviceType.toLowerCase(), amount));
						}
						st.close();
					} catch (SQLException exception) {
						exception.printStackTrace();
					}
					conn.close();
					return true;
				}
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
		return false;
	}
	
	/**
	 * reset service code after delete service in service list
	 * @param serviceType the service type value
	 */
	public void resetServiceCode(String serviceType) {
		int amount = 0;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from service");
			
			if (rs.next())
				amount = rs.getInt(String.format("amount_%s", serviceType));
			
			st.executeQuery(String.format("select * from %s", serviceType));
			for (int i = 1; i <= amount; i++)
				st.executeUpdate(String.format("update %s set %s_code = '%s%03d' where id = '%d'",
						serviceType.toLowerCase(), serviceType.toLowerCase(),
						serviceType.toUpperCase(), i, i));
			
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * keyword-based search
	 * @param keyword the keyword (service name, service code, service type) value
	 * @return service list after keyword-based search
	 */
	public Vector<String> search(String keyword) {
		keyword = keyword.toLowerCase();
		int serviceID = 0;
		double serviceCost;
		String serviceType, serviceName, serviceCode, rentTime;
		Vector<String> v = new Vector<>();
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			mergeTableProduct();
			ResultSet rs = st.executeQuery("select * from service_list");
			
			while (rs.next()) {
				serviceName = rs.getString("service_name").toLowerCase();
				serviceCode = rs.getString("service_code").toLowerCase();
				serviceCost = rs.getDouble("service_cost");
				serviceType = rs.getString("service_type").toLowerCase();
				rentTime = rs.getString("service_rent_time");
				
				if (serviceName.contains(keyword) || serviceCode.contains(keyword) ||
						serviceType.contains(keyword)) {
					String s = String.format("  %-7d %-30s %-13s %,20.0f vnđ %17s %18s\n%s",
							++serviceID, serviceName.toUpperCase(),
							serviceCode, serviceCost, serviceType.toUpperCase(), rentTime,
							"----------------------------------------------------------" +
									"--------------------------------------------------" +
									"---------------");
					v.add(s);
				}
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
		return v;
	}
	
	/**
	 * display service list to terminal
	 */
	public void displayServiceList() {
		titleProduct();
		Vector<String> v = getData();
		for (String s : v) System.out.println(s);
	}
	
	/**
	 * merge tables service in database
	 */
	private void mergeTableProduct() {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			// clean table
			st.executeUpdate("truncate service_list");
			// copy data from karaoke to service_list
			st.executeUpdate("INSERT INTO service_list (service_name, service_code, " +
					"service_cost, service_type, service_rent_time)" +
					"  SELECT  karaoke_name, karaoke_code, " +
					"karaoke_cost, karaoke_type, rent_time FROM karaoke;");
			// copy data from decoration to service_list
			st.executeUpdate("INSERT INTO service_list (service_name, " +
					"service_code, service_cost, service_type)" +
					"  SELECT  decoration_name, decoration_code, decoration_cost" +
					", decoration_type FROM decoration;");
			// copy data from singer to service_list
			st.executeUpdate("INSERT INTO service_list (service_name, " +
					"service_code, service_cost, service_type)" +
					"  SELECT  singer_name, singer_code, singer_cost" +
					", singer_type FROM singer;");
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * this function support function display product
	 * @return service list after merge
	 */
	public Vector<String> getData() {
		int serviceID = 0;
		double serviceCost;
		String serviceType, serviceName, serviceCode, rentTime;
		Vector<String> v = new Vector<>();
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			mergeTableProduct();
			ResultSet rs = st.executeQuery("select * from service_list");
			
			while (rs.next()) {
				serviceName = rs.getString("service_name");
				serviceCode = rs.getString("service_code");
				serviceCost = rs.getDouble("service_cost");
				serviceType = rs.getString("service_type");
				rentTime = rs.getString("service_rent_time");
				
				String s = String.format("  %-7d %-30s %-13s %,20.0f vnđ %17s %18s\n%s",
						++serviceID, serviceName.toUpperCase(),
						serviceCode, serviceCost, serviceType.toUpperCase(), rentTime,
						"----------------------------------------------------------" +
								"--------------------------------------------------" +
								"---------------");
				v.add(s);
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
		return v;
	}
	
	/**
	 * show title service (id - service name - service code...)
	 */
	private void titleProduct() {
		System.out.printf("  %-7s %-30s %-17s %20s %20s %20s\n", "ID", "TÊN DỊCH VỤ",
				"MÃ DỊCH VỤ", "GIÁ DỊCH VỤ", "LOẠI DỊCH VỤ", "THỜI GIAN THUÊ");
		System.out.println("--------------------------------------------------------" +
				"-------------------------------------------------------------------");
	}
	
	/**
	 * get price from service list by service code
	 * @param serviceCode the service code value
	 * @return price's service code
	 */
	public double getPriceService(String serviceCode) {
		double total = 0;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from service_list " +
					"where service_code = '%s'", serviceCode));
			
			if (rs.next()) {
				total += rs.getDouble("service_cost");
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
		return total;
	}
	
	/**
	 * performing user actions (add, display, update, search...)
	 */
	public void serviceController() {
		ManagerService l = new ManagerService();
		
		Scanner scanner = new Scanner(System.in);
		int choice;
		String newName, serviceCode, keyword;
		double newPrice;
		
		do {
			System.out.println("==============MENU===============");
			System.out.println("1. Hiện thị danh sách dịch vụ");
			System.out.println("2. Thêm dịch vụ");
			System.out.println("3. Xoá dịch vụ");
			System.out.println("4. Sửa thông tin (tên dịch vụ)");
			System.out.println("5. Sửa thông tin (giá dịch vụ)");
			System.out.println("6. Tìm kiếm sản phẩm theo từ khoá");
			System.out.println("0. Thoát");
			System.out.println("=================================");
			System.out.print("=> Nhập lựa chọn: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1 -> l.displayServiceList();
				case 2 -> {
					int choiceService;
					
					System.out.println("================= MENU ================");
					System.out.println("1. Thêm dịch vụ Karaoke");
					System.out.println("2. Thêm dịch vụ Trang trí phối cảnh");
					System.out.println("3. Thêm Ca sĩ");
					System.out.println("0. Thoát");
					System.out.println("=======================================");
					System.out.print("=> Nhập lựa chọn: ");
					choiceService = scanner.nextInt();
					scanner.nextLine();
					
					switch (choiceService) {
						case 1 -> l.addService(new Karaoke());
						case 2 -> l.addService(new Decoration());
						case 3 -> l.addService(new Singer());
						default -> System.out.println("=> Bạn đã chọn thoát!");
					}
				}
				case 3 -> {
					System.out.print("- Nhập mã dịch vụ cần xoá: ");
					serviceCode = scanner.nextLine();
					
					if (l.deleteService(serviceCode)) {
						resetServiceCode(serviceCode.split("[0-9]")[0]);
						System.out.println("=> Xoá thành công!");
					} else System.out.println("<!> Xoá thất bại");
				}
				case 4 -> {
					System.out.print("- Nhập mã dịch vụ: ");
					serviceCode = scanner.nextLine();
					
					System.out.print("- Nhập tên dịch vụ mới: ");
					newName = scanner.nextLine();
					
					l.updateServiceName(newName, serviceCode);
				}
				case 5 -> {
					System.out.print("- Nhập mã dịch vụ: ");
					serviceCode = scanner.nextLine();
					
					System.out.print("- Nhập giá mới: ");
					newPrice = scanner.nextDouble();
					scanner.nextLine();
					
					l.updateServicePrice(newPrice, serviceCode);
				}
				case 6 -> {
					System.out.print("- Nhập từ khoá: ");
					keyword = scanner.nextLine();
					
					l.titleProduct();
					l.search(keyword).forEach(System.out::println);
				}
				default -> System.out.println("- Bạn đã chọn thoát!");
			}
		} while (choice != 0);
	}
}