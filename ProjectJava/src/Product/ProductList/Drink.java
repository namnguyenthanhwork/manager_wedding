package Product.ProductList;

import JDBC.ConnectSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Drink extends Product {
	private String manufacturer;
	
	// auto create new ID after create new object
	{
		setCode("amount_drink", "DRINK");
	}
	
	public Drink() {
	}
	
	/**
	 * add new drink from input data user
	 * @param scanner input data from user
	 */
	@Override
	public void inputProduct(Scanner scanner) {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			
			super.inputProduct(scanner);
			System.out.print("- Nhập hãng sản xuất: ");
			this.manufacturer = scanner.nextLine();
			
			PreparedStatement pst =
					conn.getConnection().prepareStatement(
							"insert into drink" +
									"(drink_name, drink_code, drink_cost, drink_type, manufacturer) " +
									"values(?,?,?,?,?)");
			pst.setString(1, this.productName);
			pst.setString(2, this.productCode);
			pst.setDouble(3, Math.round(this.productCost));
			pst.setString(4, this.productType);
			pst.setString(5, this.manufacturer);
			
			int i = pst.executeUpdate();
			if (i != 0)
				System.out.println("Thêm thức uống thành công!");
			else
				System.out.println("<!> Thêm thức uống thất bại!");
			st.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		conn.close();
	}
	
	/**
	 * display drink to terminal
	 */
	@Override
	public void displayProduct() {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from drink");
			
			while (rs.next()) {
				this.productID = rs.getInt("id");
				this.productName = rs.getString("drink_name");
				this.productCode = rs.getString("drink_code");
				this.productCost = rs.getDouble("drink_cost");
				this.productType = rs.getString("drink_type");
				this.manufacturer = rs.getString("manufacturer");
				
				super.displayProduct();
				System.out.printf("%26s\n", this.manufacturer.toUpperCase());
				System.out.println("--------------------------------------------" +
						"-------------------------------------------------------");
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.close();
	}
	
	@Override
	public String toString() {
		return super.toString() +
				String.format("%24s\n%s", this.manufacturer.toUpperCase(),
						"----------------------------------------------------" +
								"---------------------------------------------");
	}
	
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
}
