package Product;

import JDBC.ConnectSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Menu {
	private String menuCode;
	
	public Menu() {
	}
	
	/**
	 * create new menu
	 */
	public void inputMenu() {
		int amount = 0;
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			st.executeQuery("select * from menu");
			ResultSet rs = st.getResultSet();
			if (rs.next()) {
				amount = rs.getInt("amount_menu");
				st.executeUpdate(String.format("update menu set amount_menu = %d", ++amount));
			}
			String sql = String.format("CREATE TABLE menu%03d " +
					"(id INTEGER NOT NULL AUTO_INCREMENT, " +
					" product_name VARCHAR(255), " +
					" product_cost VARCHAR(255), " +
					" PRIMARY KEY ( id ))", amount);
			st.executeUpdate(sql);
			System.out.println("- Thêm thành công!");
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * display menu to terminal
	 * @param menuID the menu id value
	 */
	public void displayMenu(String menuID) {
		String productName;
		int productID;
		double productCost;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from %s", menuID));
			
			while (rs.next()) {
				productID = rs.getInt("id");
				productName = rs.getString("product_name");
				productCost = rs.getDouble("product_cost");
				
				System.out.printf("  %-7d %-30s %,12.0f vnđ \n%18s\n",
						productID, productName.toUpperCase(),
						productCost, "----------------------------" +
								"-------------------------------");
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * add product into menu
	 * @param menuCode the menu code value
	 * @param productCode the product code value
	 */
	public void addProductToMenu(String menuCode, String productCode) {
		String productCodeTemp = "";
		productCodeTemp = productCode;
		String[] strSplit = productCodeTemp.split("[0-9]");
		String productType = strSplit[0];
		String productCodeDB;
		String productName = "";
		double productCost = 0.0;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from " + productType.toLowerCase());
			
			while (rs.next()) {
				productCodeDB = rs.getString(productType.toLowerCase() + "_code");
				if (productCodeDB.equalsIgnoreCase(productCode)) {
					productName = rs.getString(productType.toLowerCase() + "_name");
					productCost = rs.getDouble(productType.toLowerCase() + "_cost");
					st.executeUpdate(String.format("insert into %s (product_name, product_cost) " +
							"values ('%s', '%.0f')", menuCode, productName, productCost));
					System.out.println("=> Thêm thành công!");
					break;
				}
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * remove product from menu
	 * @param menuCode the menu code value
	 * @param productID the product id value
	 */
	public void removeProductInMenu(String menuCode, int productID) {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from %s", menuCode));
			
			st.executeUpdate(String.format("delete from %s where id = '%d';",
					menuCode, productID));
			st.executeUpdate(String.format("alter table %s drop id;", menuCode));
			st.executeUpdate(String.format("alter table %s auto_increment = 1;", menuCode));
			st.executeUpdate(
					String.format("alter table %s add id int not null auto_increment primary key first;",
							menuCode));
			
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	public String getMenuCode() {
		return menuCode;
	}
	
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
}
