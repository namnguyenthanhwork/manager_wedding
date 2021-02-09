package Lobby;

import JDBC.ConnectSQL;

import java.sql.*;
import java.util.Scanner;

public class LobbyPriceList {
	public LobbyPriceList() {
	}
	
	/**
	 * <p>check time of day already exist or no</p>
	 * <p>if time of day no exist, create new field and input price
	 * (from monday to sunday) in time of day</p>
	 * @param timeOfDay the time of day value
	 */
	public void inputPriceTimeOfDay(String timeOfDay) {
		Scanner scanner = new Scanner(System.in);
		double[] price = new double[7];
		int amount = 0;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from lobby_price_list " +
					"where rent_time = '%s'", timeOfDay.toLowerCase()));
			
			int flag = rs.next() ? 1 : 0; // if flag == 0, create new field and input price
			if (flag == 0) {
				st.executeQuery("select * from lobby_price_list");
				
				for (int i = 1; i <= 7; i++) {
					if (i == 1) {
						System.out.printf("- Nhập giá buổi %s ngày chủ nhật: ", timeOfDay.toLowerCase());
					} else {
						System.out.printf("- Nhập giá buổi %s ngày thứ %d: ", timeOfDay.toLowerCase(), i);
					}
					price[amount++] = scanner.nextDouble();
				}
				scanner.nextLine();
				
				PreparedStatement pst =
						conn.getConnection().prepareStatement(
								"insert into lobby_price_list " +
										"(rent_time, sunday, monday, tuesday, wednesday, thursday, friday, saturday) " +
										"values(?,?,?,?,?,?,?,?)");
				int a = 0;
				pst.setString(1, timeOfDay);
				for (int i = 2; i <= 8; i++) {
					pst.setDouble(i, price[a++]);
				}
				
				int i = pst.executeUpdate();
				if (i != 0) {
					System.out.println("Thêm thành công!");
				} else {
					System.out.println("<!> Thêm thất bại!");
				}
				
				st.close();
			} else
				System.out.println("<!> Đã tồn tại, xin nhập lại");
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * display lobby's list price in time of day
	 */
	public void displayLobbyPriceList() {
		String timeOfDay;
		double priceSun, priceMon, priceTue, priceWed, priceThur, priceFri, priceSat;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from lobby_price_list");
			
			titlePriceList();
			
			while (rs.next()) {
				timeOfDay = rs.getString("rent_time");
				priceSun = rs.getDouble("sunday");
				priceMon = rs.getDouble("monday");
				priceTue = rs.getDouble("tuesday");
				priceWed = rs.getDouble("wednesday");
				priceThur = rs.getDouble("thursday");
				priceFri = rs.getDouble("friday");
				priceSat = rs.getDouble("saturday");
				
				System.out.printf(" %-5s %-15s %-15.0f %-15.0f %-15.0f %-15.0f %-15.0f %-15.0f %-15.0f\n%s\n", "",
						timeOfDay.toUpperCase(), priceSun, priceMon, priceTue, priceWed, priceThur, priceFri, priceSat,
						"------------------------------------------------------------------" +
								"----------------------------------------------------------" +
								"-----");
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * show title price list (rent time - monday - tuesday...)
	 */
	public void titlePriceList() {
		System.out.printf("  %-20s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n%s\n", "THỜI GIAN THUÊ",
				"CHỦ NHẬT", "THỨ 2", "THỨ 3", "THỨ 4", "THỨ 5", "THỨ 6", "THỨ 7", "-------------" +
						"--------------------------------------------------------------------" +
						"-----------------------------------------------");
	}
	
	/**
	 * update new price from input data user
	 * @param newPrice the value new price
	 * @param timeOfDay the time of day value (morning, afternoon, evening...)
	 * @param dayOfWeek the day of week value (monday, tuesday... , sunday)
	 */
	public void updatePrice(double newPrice, String timeOfDay, String dayOfWeek) {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			int rows = st.executeUpdate(String.format("update lobby_price_list set %s = %.0f " +
					"where rent_time = '%s'", dayOfWeek, newPrice, timeOfDay));
			if (rows != 0)
				System.out.println("Update thành công!");
			else System.out.println("Update thất bại!");
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * update new time of day from input data user
	 * @param newTimeOfDay the new time of day value
	 * @param timeOfDay the new time of day value
	 */
	public void updateTimeOfDay(String newTimeOfDay, String timeOfDay) {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from lobby_price_list " +
					"where rent_time = '%s'", timeOfDay.toLowerCase()));
			
			int flag = rs.next() ? 1 : 0;
			if (flag == 1) {
				st.executeUpdate(String.format("update lobby_price_list set " +
								"rent_time = '%s' where rent_time = '%s'",
						newTimeOfDay.toLowerCase(), timeOfDay.toLowerCase()));
				System.out.println("Update thành công!");
			} else
				System.out.println("Update thất bại!");
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * delete time of day in field
	 * @param timeOfDay the time of day value
	 * @return true if delete successful else return false if delete failed
	 */
	public boolean deleteTimeOfDay(String timeOfDay) {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from lobby_price_list " +
					"where rent_time = '%s'", timeOfDay.toLowerCase()));
			
			int flag = rs.next() ? 1 : 0; // if search found => delete
			
			if (flag == 1) {
				st.executeUpdate(String.format("delete from lobby_price_list " +
						"where rent_time = '%s'", timeOfDay.toLowerCase()));
				st.close();
				conn.close();
				return true;
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
		return false;
	}
	
	/**
	 * convert day of week (vietnamese) to day of week (english)
	 * @param dayOfWeek the day of week value
	 * @return day of week (english)
	 */
	private String convertDayToEng(String dayOfWeek) {
		if (dayOfWeek.equalsIgnoreCase("chủ nhật")) return "sunday";
		if (dayOfWeek.equalsIgnoreCase("thứ 2") ||
				dayOfWeek.equalsIgnoreCase("thứ hai")) return "monday";
		if (dayOfWeek.equalsIgnoreCase("thứ 3") ||
				dayOfWeek.equalsIgnoreCase("thứ ba")) return "tuesday";
		if (dayOfWeek.equalsIgnoreCase("thứ 4") ||
				dayOfWeek.equalsIgnoreCase("thứ tư")) return "wednesday";
		if (dayOfWeek.equalsIgnoreCase("thứ 5") ||
				dayOfWeek.equalsIgnoreCase("thứ năm")) return "thursday";
		if (dayOfWeek.equalsIgnoreCase("thứ 6") ||
				dayOfWeek.equalsIgnoreCase("thứ sáu")) return "friday";
		if (dayOfWeek.equalsIgnoreCase("thứ 7") ||
				dayOfWeek.equalsIgnoreCase("thứ bảy")) return "saturday";
		return null;
	}
	
	/**
	 * get price from lobby list price
	 * @param timeOfDay the time of day value
	 * @param dayOfWeek the day of week value
	 * @return price's time of day and day of week
	 */
	public double getPriceLobby(String timeOfDay, String dayOfWeek) {
		double price = 0;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from lobby_price_list" +
					" where rent_time = '%s'", timeOfDay));
			if (rs.next())
				price = rs.getDouble(dayOfWeek);
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
		return price;
	}
	
	/**
	 * performing user actions (add, display, update, delete...)
	 */
	public void priceListController() {
		LobbyPriceList l = new LobbyPriceList();
		
		Scanner scanner = new Scanner(System.in);
		int choice;
		double newPrice;
		String timeOfDay, newTimeOfDay, dayOfWeek;
		
		do {
			System.out.println("==============MENU===============");
			System.out.println("1. Hiện thị bảng giá");
			System.out.println("2. Thêm thời điểm thuê trong ngày");
			System.out.println("3. Xoá thời điểm thuê trong ngày");
			System.out.println("4. Sửa thông tin (thời gian thuê)");
			System.out.println("5. Sửa thông tin (giá thuê)");
			System.out.println("0. Thoát");
			System.out.println("=================================");
			System.out.print("=> Nhập lựa chọn: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1 -> l.displayLobbyPriceList();
				case 2 -> {
					System.out.print("- Nhập thời gian thuê: ");
					timeOfDay = scanner.nextLine();
					l.inputPriceTimeOfDay(timeOfDay);
				}
				case 3 -> {
					System.out.print("- Nhập thời điểm cần xoá: ");
					timeOfDay = scanner.nextLine();
					System.out.println(l.deleteTimeOfDay(timeOfDay) ?
							"=> Xoá thành công!" : "<!> Xoá thất bại");
				}
				case 4 -> {
					System.out.print("- Nhập thời điểm cần thay thế: ");
					timeOfDay = scanner.nextLine();
					System.out.print("- Nhập thời điểm mới trong ngày: ");
					newTimeOfDay = scanner.nextLine();
					l.updateTimeOfDay(newTimeOfDay, timeOfDay);
				}
				case 5 -> {
					System.out.print("- Nhập thời gian thuê: ");
					timeOfDay = scanner.nextLine();
					
					do {
						System.out.print("- Nhập ngày thuê: ");
						dayOfWeek = scanner.nextLine();
						if (convertDayToEng(dayOfWeek) == null)
							System.out.println("<!> Nhập sai ngày trong tuần");
					} while (convertDayToEng(dayOfWeek) == null);
					
					System.out.print("- Nhập giá thuê mới: ");
					newPrice = scanner.nextDouble();
					scanner.nextLine();
					l.updatePrice(newPrice, timeOfDay, convertDayToEng(dayOfWeek));
				}
				default -> System.out.println("- Bạn đã chọn thoát!");
			}
		} while (choice != 0);
	}
}