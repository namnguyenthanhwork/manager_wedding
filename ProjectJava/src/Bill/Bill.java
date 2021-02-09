package Bill;

import JDBC.ConnectSQL;
import Lobby.LobbyPriceList;
import Lobby.ManagerLobby;
import Product.ManagerMenu;
import Service.ManagerService;

import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Bill {
	private String partyName;
	private String lobbyCode;
	private String lobbyLocation;
	private int lobbyCapacity;
	private String billCode;
	private double lobbyCost = 0;
	private String timeOfDay;
	private String date;
	private String menuCode;
	private double menuCost = 0;
	private String serviceCode;
	private double serviceCost = 0;
	private final ManagerLobby ml = new ManagerLobby();
	private final LobbyPriceList lbl = new LobbyPriceList();
	private final ManagerMenu mm = new ManagerMenu();
	private final ManagerService ms = new ManagerService();
	
	public Bill() {
	}
	
	/**
	 * create contract
	 * @param scanner input values from user
	 */
	@SuppressWarnings("MagicConstant")
	public void createContract(Scanner scanner) {
		int amountTables = 0, id = 0;
		String inpMenu, inpService;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from bill");
			
			while (rs.next())
				id = rs.getInt("id");
			this.billCode = String.format("BILL%03d", ++id); // get last id in id field
			
			System.out.print("- Nhập tên buổi tiệc: ");
			this.partyName = scanner.nextLine();
			
			// connect to lobby get capacity
			ml.displayLobby();
			
			System.out.print("- Nhập mã sảnh cần thuê: ");
			this.lobbyCode = scanner.nextLine();
			
			if (ml.statusLobby(this.lobbyCode)) {
				rs = st.executeQuery(String.format(
						"select * from lobby where lobby_code = '%s'", this.lobbyCode));
				
				if (rs.next()) {
					amountTables = rs.getInt("lobby_capacity");
					this.lobbyLocation = rs.getString("lobby_location");
					this.lobbyCapacity = rs.getInt("lobby_capacity");
				}
				// connect to lobby_price_list get price (time of day && day of week)
				System.out.print("- Nhập ngày thuê: ");
				this.date = scanner.nextLine();
				
				String[] arrDate = this.date.split("[\\s/-]+");
				GregorianCalendar gc = new GregorianCalendar(parseInt(arrDate[2]),
						parseInt(arrDate[1]) - 1, parseInt(arrDate[0]));
				
				lbl.displayLobbyPriceList();
				
				System.out.print("- Nhập thời điểm thuê: ");
				this.timeOfDay = scanner.nextLine();
				
				this.lobbyCost += lbl.getPriceLobby(timeOfDay, convertToStringDayOfWeek(gc));
				
				// connect to menu get price
				mm.displayMenuList();
				
				StringBuilder arrMenuCode = new StringBuilder();
				for (int i = 0; i < amountTables; i++) {
					System.out.printf("- Nhập mã thực đơn cho bàn %d: ", i + 1);
					inpMenu = scanner.nextLine();
					
					arrMenuCode.append(String.format("%d-", i + 1));
					arrMenuCode.append(inpMenu);
					
					if (i < amountTables - 1)
						arrMenuCode.append(",");
					
					this.menuCost += mm.totalPrice(inpMenu);
				}
				this.menuCode = arrMenuCode.toString();
				
				// connect to service get price
				int exit;
				StringBuilder arrServiceCode = new StringBuilder();
				
				ms.displayServiceList();
				
				do {
					System.out.print("- Nhập mã dịch vụ cần thêm: ");
					inpService = scanner.nextLine();
					
					arrServiceCode.append(inpService);
					this.serviceCost += ms.getPriceService(inpService);
					
					System.out.print("Bạn có muốn nhập thêm nữa không? CÓ(1), KHÔNG(0): ");
					exit = scanner.nextInt();
					scanner.nextLine();
					
					if (exit != 0)
						arrServiceCode.append(",");
				} while (exit != 0);
				this.serviceCode = arrServiceCode.toString();
				
				// save input data into database
				PreparedStatement pst =
						conn.getConnection().prepareStatement(
								"insert into bill(bill_code, bill_name, lobby_code, " +
										"lobby_location, lobby_capacity, lobby_cost, " +
										"bill_time, bill_date, menu_code, menu_cost, " +
										"service_code, service_cost, sum_price) " +
										"values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				pst.setString(1, this.billCode);
				pst.setString(2, this.partyName);
				pst.setString(3, this.lobbyCode);
				pst.setString(4, this.lobbyLocation);
				pst.setInt(5, this.lobbyCapacity);
				pst.setDouble(6, this.lobbyCost);
				pst.setString(7, this.timeOfDay);
				Object param = new Timestamp(gc.getTime().getTime());
				pst.setObject(8, param);
				pst.setString(9, this.menuCode);
				pst.setDouble(10, this.menuCost);
				pst.setString(11, this.serviceCode);
				pst.setDouble(12, this.serviceCost);
				pst.setDouble(13, this.lobbyCost + this.serviceCost + this.menuCost);
				
				int i = pst.executeUpdate();
				if (i != 0) {
					System.out.println("Tạo bill thành công!");
					ml.changeStatusLobby(this.lobbyCode); // after book change status lobby
					checkYearDB(arrDate[2]); // check year already exist or no in database
					moveDataToStatisticsBill(arrDate[2],
							convertToStringMonth(arrDate[1]),
							this.lobbyCost + this.serviceCost + this.menuCost);
				} else {
					System.out.println("<!> Tạo bill thất bại!");
				}
			} else
				System.out.println("<!> Sảnh đã được đặt, vui lòng chọn sảnh khác");
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * export bill to terminal
	 */
	public void exportBill() {
		System.out.println("===================================================");
		System.out.println("- Mã tiệc cưới: " + this.billCode);
		System.out.println("- Tên tiệc cưới: " + this.partyName);
		System.out.println("- Mã sảnh thuê: " + this.lobbyCode);
		System.out.println("- Vị trí sảnh thuê: " + this.lobbyLocation);
		System.out.println("- Số lượng bàn: " + this.lobbyCapacity);
		System.out.printf("- Giá sảnh thuê: %,.0f vnđ\n", this.lobbyCost);
		System.out.println("- Ngày thuê: " + this.date);
		System.out.println("- Thời điểm thuê: " + this.timeOfDay);
		System.out.print("- Danh sách thực đơn từng bàn:");
		
		String[] arrMenu = this.menuCode.split("[,-]+");
		for (int i = 0; i < arrMenu.length; i++) {
			if (i % 2 == 0) {
				if (i % 4 == 0)
					System.out.printf("\n%20s", "");
				System.out.printf("bàn %s", arrMenu[i]);
			} else if (i < arrMenu.length - 1)
				System.out.printf("(%s),", arrMenu[i]);
			else System.out.printf("(%s).", arrMenu[i]);
		}
		
		System.out.printf("\n- Tổng giá thực đơn: %,.0f vnđ \n", this.menuCost);
		System.out.println("- Danh sách dịch vụ: " + this.serviceCode);
		System.out.printf("- Tổng giá dịch vụ: %,.0f vnđ \n", this.serviceCost);
		System.out.printf("=> Tổng tiền thanh toán: %,.0f vnđ \n\n",
				(this.lobbyCost + this.menuCost + this.serviceCost));
	}
	
	/**
	 * move sum price of month in this year to statistics bill
	 * (move to statistics bill in database)
	 * @param year the year value
	 * @param month the month value
	 * @param price the price value
	 */
	private void moveDataToStatisticsBill(String year, String month, double price) {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select %s from statistics_bill " +
					"where year = '%s'", month, year));
			
			if (rs.next()) {
				price += rs.getDouble(month);
				st.executeUpdate(String.format("update statistics_bill set %s = '%f' " +
						"where year = '%s'", month, price, year));
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * <p>check year field already exist or no</p>
	 * <p>if year field no exist (year field auto create)</p>
	 * <p>else year field exist (no create new this field)</p>
	 * @param year the year value you need check
	 */
	private void checkYearDB(String year) {
		String yearDB;
		boolean flag = false;
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select year from statistics_bill");
			
			while (rs.next()) {
				yearDB = rs.getString("year");
				if (yearDB.equalsIgnoreCase(year)) {
					flag = false;
					break;
				} else
					flag = true;
			}
			if (flag)
				st.executeUpdate(String.format("insert into statistics_bill " +
						" (year) values ('%s');", year));
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	/**
	 * convert the string carries the value to the number (month) to string carries
	 * the value to the string (month)
	 * <pre>Example: String "01" after convert "january"
	 * "02" after convert "february"</pre>
	 * @param month the month value
	 * @return string carries the value to the string (month)
	 */
	private String convertToStringMonth(String month) {
		if (month.equalsIgnoreCase("01")) return "january";
		if (month.equalsIgnoreCase("02")) return "february";
		if (month.equalsIgnoreCase("03")) return "march";
		if (month.equalsIgnoreCase("04")) return "april";
		if (month.equalsIgnoreCase("05")) return "may";
		if (month.equalsIgnoreCase("06")) return "june";
		if (month.equalsIgnoreCase("07")) return "july";
		if (month.equalsIgnoreCase("08")) return "august";
		if (month.equalsIgnoreCase("09")) return "september";
		if (month.equalsIgnoreCase("10")) return "october";
		if (month.equalsIgnoreCase("11")) return "november";
		if (month.equalsIgnoreCase("12")) return "december";
		return null;
	}
	
	/**
	 * convert type int day of week to type string's day of week
	 * <pre>Example: Date 11/01/2021 day of week is 2,
	 * after convert is "monday"</pre>
	 * @param gregorianCalendar the type date value
	 * @return string have value is day of week
	 */
	private String convertToStringDayOfWeek(GregorianCalendar gregorianCalendar) {
		if (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) return "sunday";
		if (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) return "monday";
		if (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) return "tuesday";
		if (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) return "wednesday";
		if (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) return "thursday";
		if (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) return "friday";
		if (gregorianCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) return "saturday";
		return null;
	}
	
	public String getPartyName() {
		return partyName;
	}
	
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	
	public String getLobbyCode() {
		return lobbyCode;
	}
	
	public void setLobbyCode(String lobbyCode) {
		this.lobbyCode = lobbyCode;
	}
	
	public double getLobbyCost() {
		return lobbyCost;
	}
	
	public void setLobbyCost(double lobbyCost) {
		this.lobbyCost = lobbyCost;
	}
	
	public String getBillCode() {
		return billCode;
	}
	
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	
	public String getTimeOfDay() {
		return timeOfDay;
	}
	
	public void setTimeOfDay(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getMenuCode() {
		return menuCode;
	}
	
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	
	public double getMenuCost() {
		return menuCost;
	}
	
	public void setMenuCost(double menuCost) {
		this.menuCost = menuCost;
	}
	
	public String getServiceCode() {
		return serviceCode;
	}
	
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public double getServiceCost() {
		return serviceCost;
	}
	
	public void setServiceCost(double serviceCost) {
		this.serviceCost = serviceCost;
	}
	
	public String getLobbyLocation() {
		return lobbyLocation;
	}
	
	public void setLobbyLocation(String lobbyLocation) {
		this.lobbyLocation = lobbyLocation;
	}
	
	public int getLobbyCapacity() {
		return lobbyCapacity;
	}
	
	public void setLobbyCapacity(int lobbyCapacity) {
		this.lobbyCapacity = lobbyCapacity;
	}
}
