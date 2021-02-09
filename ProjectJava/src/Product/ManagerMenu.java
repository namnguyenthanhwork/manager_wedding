package Product;

import JDBC.ConnectSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

public class ManagerMenu {
	public ManagerMenu() {
	}
	
	/**
	 * show title menu (id - product name - product price)
	 */
	private void titleMenu() {
		System.out.printf("  %-6s %-30s %17s\n", "ID", "TÊN SẢN PHẨM",
				"GIÁ SẢN PHẨM");
		System.out.println("------------------------" +
				"-----------------------------------");
	}
	
	/**
	 * add new menu into menu list
	 */
	public void addMenu() {
		Menu m = new Menu();
		m.inputMenu();
	}
	
	/**
	 * display menu list to terminal
	 */
	public void displayMenuList() {
		Vector<String> vTables = new Vector<>();
		
		ConnectSQL conn = new ConnectSQL();
		try {
			// collect all menu
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("show tables");
			while (rs.next()) {
				if (rs.getString(1).contains("menu")
						&& rs.getString(1).length() > 4)
					vTables.add(rs.getString(1));
			}
			
			// show items in menu
			int productID;
			String productName;
			double productCost;
			for (String mCode : vTables) {
				System.out.printf("%32s: %s\n", "MÃ THỰC ĐƠN", mCode);
				titleMenu();
				rs = st.executeQuery(String.format("select * from %s", mCode));
				while (rs.next()) {
					productID = rs.getInt("id");
					productName = rs.getString("product_name");
					productCost = rs.getDouble("product_cost");
					System.out.printf("  %-7d %-30s %,12.0f vnđ \n%18s\n",
							productID, productName.toUpperCase(),
							productCost, "----------------------------" +
									"-------------------------------");
				}
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * add new product to menu
	 * @param menuCode the menu code value
	 * @param productCode the product code value
	 */
	public void addProductToMenu(String menuCode, String productCode) {
		Menu m = new Menu();
		m.addProductToMenu(menuCode, productCode);
	}
	
	/**
	 * remove product from menu
	 * @param menuCode the menu code value
	 * @param productID the product id value
	 */
	public void removeProductInMenu(String menuCode, int productID) {
		Menu m = new Menu();
		m.removeProductInMenu(menuCode, productID);
	}
	
	/**
	 * delete menu from menu list
	 * @param menuCode the menu code value
	 */
	public void deleteMenu(String menuCode) {
		int amount = 0;
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			st.executeUpdate(String.format("drop table %s", menuCode));
			ResultSet rs = st.executeQuery("select * from menu");
			
			if (rs.next())
				amount = rs.getInt("amount_menu");
			st.executeUpdate(String.format("update menu set amount_menu = '%d';", --amount));
			System.out.println("=> Xoá menu thành công!");
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * reset menu code after delete menu from menu list
	 */
	public void resetMenuCode() {
		int amount = 0;
		String productName;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("show tables");
			
			while (rs.next()) {
				if (rs.getString(1).contains("menu")
						&& rs.getString(1).length() > 4) {
					++amount;
					if (!rs.getString(1).equalsIgnoreCase(String.format("" +
							"menu%03d", amount))) {
						productName = rs.getString(1);
						st.executeUpdate(String.format("rename table %s to menu%03d",
								productName, amount));
					}
				}
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			System.out.println("=> Reset mã thực đơn thành công!");
		}
		conn.close();
	}
	
	/**
	 * calculator sum price of products in menu
	 * @param menuCode the menu code value
	 * @return sum price of products in menu
	 */
	public double totalPrice(String menuCode) {
		double total = 0;
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from %s", menuCode));
			
			while (rs.next()) {
				total += rs.getDouble("product_cost");
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
		return total;
	}
	
	/**
	 * performing user actions (add, display, delete...)
	 */
	public void menuController() {
		Scanner scanner = new Scanner(System.in);
		String productCode, menuCode;
		int choice = 0, productID;
		
		do {
			System.out.println("===========================MENU==========================");
			System.out.println("1. Hiện thị danh sách menu");
			System.out.println("2. Thêm sản phẩm vào menu");
			System.out.println("3. Thêm 1 menu vào danh sách");
			System.out.println("4. Xoá sản phẩm từ menu");
			System.out.println("5. Xoá 1 menu từ danh sách");
			System.out.println("0. Thoát");
			System.out.println("=========================================================");
			System.out.print("=> Nhập lựa chọn: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1 -> {
					displayMenuList();
				}
				case 2 -> {
					System.out.print("- Nhập mã thực đơn: ");
					menuCode = scanner.nextLine();
					System.out.print("- Nhập mã sản phẩm: ");
					productCode = scanner.nextLine();
					addProductToMenu(menuCode, productCode);
				}
				case 3 -> {
					addMenu();
				}
				case 4 -> {
					System.out.print("- Nhập mã thực đơn: ");
					menuCode = scanner.nextLine();
					System.out.print("- Nhập ID sản phẩm: ");
					productID = scanner.nextInt();
					scanner.nextLine();
					removeProductInMenu(menuCode, productID);
				}
				case 5 -> {
					System.out.print("- Nhập mã thực đơn cần xoá: ");
					menuCode = scanner.nextLine();
					deleteMenu(menuCode);
					resetMenuCode();
				}
				default -> System.out.println("=> Bạn đã chọn thoát!");
			}
		} while (choice != 0);
	}
}