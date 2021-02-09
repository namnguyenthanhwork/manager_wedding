package Lobby;

import JDBC.ConnectSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

public class ManagerLobby {
	public ManagerLobby() {
	}
	
	/**
	 * add new lobby into lobby list
	 * @param scanner input value from user
	 */
	public void addLobby(Scanner scanner) {
		Lobby lb = new Lobby();
		try {
			lb.inputLobby(scanner);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * check status lobby (free or busy)
	 * @param lobbyCode the lobby code value
	 * @return true if lobby free else return false if lobby busy
	 */
	public boolean statusLobby(String lobbyCode) {
		String empty = null;
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format(
					"select * from lobby where lobby_code = '%s'", lobbyCode));
			
			if (rs.next())
				empty = rs.getString("status");
			if (empty != null && empty.equalsIgnoreCase("rảnh")) return true;
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
		return false;
	}
	
	/**
	 * <p>change status lobby (free <-> busy)</p>
	 * <pre>Example: lobby free -> lobby busy
	 * lobby busy -> lobby free</pre>
	 * @param lobbyCode the lobby code value
	 */
	public void changeStatusLobby(String lobbyCode) {
		String status = null;
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from lobby " +
					"where lobby_code = '%s'", lobbyCode.toUpperCase()));
			
			if (rs.next())
				status = rs.getString("status");
			if ("Rảnh".equalsIgnoreCase(status))
				st.executeUpdate(String.format
						("update lobby set status = '%s' where lobby_code = '%s'",
								"Đầy", lobbyCode));
			else
				st.executeUpdate(String.format
						("update lobby set status = '%s' where lobby_code = '%s'",
								"Rảnh", lobbyCode));
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * show title lobby (id - lobby name - lobby code...)
	 */
	public void titleLobby() {
		System.out.printf("  %-10s %-15s %-15s %-15s %10s %15s \n%s\n",
				"ID", "TÊN SẢNH", "MÃ SẢNH", "VỊ TRÍ SẢNH", "SỨC CHỨA", "TRẠNG THÁI",
				"----------------------------------------------" +
						"------------------------------------------");
	}
	
	/**
	 * display lobby in terminal
	 */
	public void displayLobby() {
		Lobby lb = new Lobby();
		titleLobby();
		lb.displayLobby();
	}
	
	/**
	 * update new lobby name from input data user
	 * @param lobbyNewName the new lobby name value
	 * @param lobbyCode the new lobby code value
	 */
	public void updateLobbyName(String lobbyNewName, String lobbyCode) {
		boolean flag = false;
		String resLobbyCode;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from lobby");
			
			while (rs.next()) {
				resLobbyCode = rs.getString("lobby_code");
				if (resLobbyCode.equalsIgnoreCase(lobbyCode)) {
					st.executeUpdate(String.format
							("update lobby set lobby_name = '%s' where lobby_code = '%s'",
									lobbyNewName, lobbyCode));
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
	 * update new lobby location from input data user
	 * @param lobbyNewLocation the new lobby location value
	 * @param lobbyCode the new lobby code value
	 */
	public void updateLobbyLocation(String lobbyNewLocation, String lobbyCode) {
		boolean flag = false;
		String resLobbyCode;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from lobby");
			
			while (rs.next()) {
				resLobbyCode = rs.getString("lobby_code");
				if (resLobbyCode.equalsIgnoreCase(lobbyCode)) {
					st.executeUpdate(String.format
							("update lobby set lobby_location = '%s' where lobby_code = '%s'",
									lobbyNewLocation, lobbyCode));
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
	 * update new lobby capacity from input data user
	 * @param lobbyNewCapacity the new lobby capacity value
	 * @param lobbyCode the new lobby code value
	 */
	public void updateLobbyCapacity(int lobbyNewCapacity, String lobbyCode) {
		boolean flag = false;
		String resLobbyCode;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from lobby");
			
			while (rs.next()) {
				resLobbyCode = rs.getString("lobby_code");
				if (resLobbyCode.equalsIgnoreCase(lobbyCode)) {
					st.executeUpdate(String.format
							("update lobby set lobby_capacity = '%s' where lobby_code = '%s'",
									lobbyNewCapacity, lobbyCode));
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
	 * check lobby delete successful or failed
	 * @param lobbyCode the lobby code value
	 * @return true if delete successful or return false if delete failed
	 */
	public boolean deleteLobby(String lobbyCode) {
		String resLobbyCode;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			
			ResultSet rs = st.executeQuery("select * from lobby");
			
			while (rs.next()) {
				resLobbyCode = rs.getString("lobby_code");
				if (resLobbyCode.equalsIgnoreCase(lobbyCode)) {
					// delete product in db
					try {
						st.executeUpdate(String.format("delete from lobby where " +
								"lobby_code = '%s';", lobbyCode));
						st.executeUpdate("alter table lobby drop id;");
						st.executeUpdate("alter table lobby auto_increment = 1;");
						st.executeUpdate("alter table lobby add id int " +
								"not null auto_increment primary key first;");
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
	 * keyword-based search
	 * @param keyword the keyword value (lobby name, lobby code, lobby location)
	 * @return list lobby after keyword-based search
	 */
	public Vector<String> search(String keyword) {
		keyword = keyword.toLowerCase();
		int lobbyID = 0, lobbyCapacity;
		String lobbyName, lobbyCode, lobbyLocation, lobbyStatus;
		Vector<String> v = new Vector<>();
		
		ConnectSQL conn = new ConnectSQL();
		try {
			keyword = "%" + keyword + "%";
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from lobby where lobby_name like '%s' or " +
					"lobby_location like '%s' or lobby_code like '%s' " +
					"or status like '%s'", keyword, keyword, keyword, keyword));
			
			while (rs.next()) {
				lobbyName = rs.getString("lobby_name").toLowerCase();
				lobbyCode = rs.getString("lobby_code").toLowerCase();
				lobbyLocation = rs.getString("lobby_location").toLowerCase();
				lobbyCapacity = rs.getInt("lobby_capacity");
				lobbyStatus = rs.getString("status");
				
				String s = String.format("  %-10d %-15s %-15s %-10s %10d %15s \n%s",
						++lobbyID, lobbyName.toUpperCase(),
						lobbyCode, lobbyLocation.toUpperCase(),
						lobbyCapacity, lobbyStatus, "----------------" +
								"----------------------------------------------" +
								"--------------------------");
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
	 * search by capacity
	 * @param capacity the lobby's capacity value
	 * @return list lobby after search by capacity
	 */
	public Vector<String> search(int capacity) {
		int lobbyID = 0, lobbyCapacity;
		String lobbyName, lobbyCode, lobbyLocation, lobbyStatus;
		Vector<String> v = new Vector<>();
		ConnectSQL conn = new ConnectSQL();
		
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format
					("select * from lobby where lobby_capacity = %d", capacity));
			
			while (rs.next()) {
				lobbyName = rs.getString("lobby_name").toLowerCase();
				lobbyCode = rs.getString("lobby_code").toLowerCase();
				lobbyLocation = rs.getString("lobby_location").toLowerCase();
				lobbyCapacity = rs.getInt("lobby_capacity");
				lobbyStatus = rs.getString("status");
				
				
				String s = String.format("  %-10d %-15s %-15s %-10s %10d %15s \n%s",
						++lobbyID, lobbyName.toUpperCase(),
						lobbyCode.toUpperCase(), lobbyLocation.toUpperCase(),
						lobbyCapacity, lobbyStatus, "----------------" +
								"----------------------------------------------" +
								"--------------------------");
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
	 * reset lobby code after update, delete...
	 */
	private void resetLobbyCode() {
		int amount = 0;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from lobby");
			
			while (rs.next())
				amount = rs.getInt("id");
			
			st.executeQuery("select * from lobby");
			for (int i = 1; i <= amount; i++)
				st.executeUpdate(String.format("update lobby set lobby_code = 'S%03d' where id = '%d'", i, i));
			
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * performing user actions (add, display, update, search...)
	 */
	public void lobbyController() {
		Scanner scanner = new Scanner(System.in);
		ManagerLobby l = new ManagerLobby();
		int choice, newCapacity, capacity;
		String lobbyCode, lobbyLocation, keyword, newLocation;
		
		do {
			System.out.println("==============MENU===============");
			System.out.println("1. Hiện thị danh sách sảnh");
			System.out.println("2. Thêm sảnh");
			System.out.println("3. Xoá sảnh");
			System.out.println("4. Sửa thông tin (tên sảnh)");
			System.out.println("5. Sửa thông tin (vị trí sảnh)");
			System.out.println("6. Sửa thông tin (số lượng bàn)");
			System.out.println("7. Tìm kiếm sảnh theo từ khoá");
			System.out.println("8. Tìm kiếm sảnh theo số lượng");
			System.out.println("0. Thoát");
			System.out.println("=================================");
			System.out.print("=> Nhập lựa chọn: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1 -> l.displayLobby();
				case 2 -> l.addLobby(scanner);
				case 3 -> {
					System.out.print("- Nhập mã sảnh cần xoá: ");
					lobbyCode = scanner.nextLine();
					if (l.deleteLobby(lobbyCode)) {
						System.out.println("=> Xoá thành công!");
						resetLobbyCode();
					} else System.out.println("<!> Xoá thất bại");
				}
				case 4 -> {
					System.out.print("- Nhập mã sảnh: ");
					lobbyCode = scanner.nextLine();
					System.out.print("- Nhập tên sảnh mới: ");
					newLocation = scanner.nextLine();
					l.updateLobbyName(newLocation, lobbyCode);
				}
				case 5 -> {
					System.out.print("- Nhập mã sảnh: ");
					lobbyCode = scanner.nextLine();
					System.out.print("- Nhập vị trí sảnh mới: ");
					lobbyLocation = scanner.nextLine();
					l.updateLobbyLocation(lobbyLocation, lobbyCode);
				}
				case 6 -> {
					System.out.print("- Nhập mã sảnh: ");
					lobbyCode = scanner.nextLine();
					System.out.print("- Nhập số bàn mới: ");
					newCapacity = scanner.nextInt();
					scanner.nextLine();
					l.updateLobbyCapacity(newCapacity, lobbyCode);
				}
				case 7 -> {
					System.out.print("- Nhập từ khoá cần tìm kiếm: ");
					keyword = scanner.nextLine();
					l.titleLobby();
					l.search(keyword).forEach(System.out::println);
				}
				case 8 -> {
					System.out.print("- Nhập số bàn: ");
					capacity = scanner.nextInt();
					l.titleLobby();
					l.search(capacity).forEach(System.out::println);
				}
				default -> System.out.println("- Bạn đã chọn thoát!");
			}
		} while (choice != 0);
	}
}