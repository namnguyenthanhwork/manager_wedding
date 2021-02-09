package Bill;

import JDBC.ConnectSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ManagerBill {
	public ManagerBill() {
	
	}
	
	/**
	 * add new bill from user
	 * @param scanner input value from user
	 */
	public void addBill(Scanner scanner) {
		Bill b = new Bill();
		b.createContract(scanner);
	}
	
	/**
	 * display list bill to terminal
	 */
	public void displayBillList() {
		Bill b = new Bill();
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from bill");
			
			while (rs.next()) {
				b.setBillCode(rs.getString("bill_code"));
				b.setPartyName(rs.getString("bill_name"));
				b.setLobbyCode(rs.getString("lobby_code"));
				b.setLobbyLocation(rs.getString("lobby_location"));
				b.setLobbyCapacity(rs.getInt("lobby_capacity"));
				b.setLobbyCost(rs.getDouble("lobby_cost"));
				b.setTimeOfDay(rs.getString("bill_time"));
				b.setDate(rs.getDate("bill_date").toString());
				b.setMenuCode(rs.getString("menu_code"));
				b.setMenuCost(rs.getDouble("menu_cost"));
				b.setServiceCode(rs.getString("service_code"));
				b.setServiceCost(rs.getDouble("service_cost"));
				
				b.exportBill();
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * keyword-based search
	 * @param keyword the (bill code or bill name) value
	 * @return object type Bill if search found else return null
	 */
	public Bill search(String keyword) {
		Bill b = new Bill();
		keyword = "%" + keyword + "%";
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from bill " +
					"where bill_code like '%s' or bill_name like '%s'", keyword, keyword));
			
			if (rs.next()) {
				b.setBillCode(rs.getString("bill_code"));
				b.setPartyName(rs.getString("bill_name"));
				b.setLobbyCode(rs.getString("lobby_code"));
				b.setLobbyLocation(rs.getString("lobby_location"));
				b.setLobbyCapacity(rs.getInt("lobby_capacity"));
				b.setLobbyCost(rs.getDouble("lobby_cost"));
				b.setTimeOfDay(rs.getString("bill_time"));
				b.setDate(rs.getDate("bill_date").toString());
				b.setMenuCode(rs.getString("menu_code"));
				b.setMenuCost(rs.getDouble("menu_cost"));
				b.setServiceCode(rs.getString("service_code"));
				b.setServiceCost(rs.getDouble("service_cost"));
				
				return b;
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
		return null;
	}
	
	/**
	 * performing user actions (add, display, search)
	 */
	public void billController() {
		ManagerBill mb = new ManagerBill();
		
		Scanner scanner = new Scanner(System.in);
		int choice;
		do {
			System.out.println("==============MENU===============");
			System.out.println("1. Hiện thị danh sách bill");
			System.out.println("2. Thêm bill");
			System.out.println("3. Tìm kiếm");
			System.out.println("0. Thoát");
			System.out.println("=================================");
			System.out.print("=> Nhập lựa chọn: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1 -> mb.displayBillList();
				case 2 -> mb.addBill(scanner);
				case 3 -> {
					String keyword;
					System.out.print("- Nhập tên bill hoặc mã bill: ");
					keyword = scanner.nextLine();
					Bill b = mb.search(keyword);
					if (b != null)
						b.exportBill();
					else
						System.out.println("Không tìm thấy!!!");
				}
				default -> System.out.println("- Bạn đã chọn thoát!");
			}
		} while (choice != 0);
	}
}