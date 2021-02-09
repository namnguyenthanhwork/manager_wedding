package Product.ProductList;

import JDBC.ConnectSQL;

import java.sql.*;
import java.sql.Statement;
import java.util.Scanner;

public class Food extends Product {
	private boolean kindFood;
	
	// auto create new ID after create new object
	{
		setCode("amount_food", "FOOD");
	}
	
	public Food() {
	}
	
	/**
	 * check vegetarian
	 * @param scanner input data from user
	 * @return true if vegetarian else return false
	 */
	public boolean isKindFood(Scanner scanner) {
		boolean kindFood = false;
		boolean check = false;
		
		do {
			System.out.print("- Là món chay? (Y/N): ");
			String inp = scanner.nextLine();
			if (inp.equalsIgnoreCase("Y")) {
				kindFood = true;
				check = true;
			} else check = inp.equalsIgnoreCase("N");
		} while (!check);
		return kindFood;
	}
	
	/**
	 * add new food from input data user
	 * @param scanner input data from user
	 */
	@Override
	public void inputProduct(Scanner scanner) {
		try {
			ConnectSQL conn = new ConnectSQL();
			Statement st = conn.getConnection().createStatement();
			
			super.inputProduct(scanner);
			
			this.kindFood = isKindFood(scanner);
			PreparedStatement pst =
					conn.getConnection().prepareStatement(
							"insert into food(food_name, food_code, food_cost, food_type, is_vegetarian) values(?,?,?,?,?)");
			
			pst.setString(1, this.productName);
			pst.setString(2, this.productCode);
			pst.setDouble(3, Math.round(this.productCost));
			pst.setString(4, this.productType);
			pst.setInt(5, this.kindFood ? 1 : 0);
			
			int i = pst.executeUpdate();
			if (i != 0) {
				System.out.println("Thêm món ăn thành công!");
			} else {
				System.out.println("<!> Thêm món ăn thất bại!");
			}
			st.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * display food to terminal
	 */
	@Override
	public void displayProduct() {
		try {
			ConnectSQL conn = new ConnectSQL();
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from food");
			
			while (rs.next()) {
				this.productID = rs.getInt("id");
				this.productName = rs.getString("food_name");
				this.productCode = rs.getString("food_code");
				this.productCost = rs.getDouble("food_cost");
				this.productType = rs.getString("food_type");
				
				super.displayProduct();
				System.out.println("\n--------------------------------" +
						"--------------------------------------------");
			}
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return super.toString() +
				String.format("%s",
						"----------------------------------------------------" +
								"---------------------------------------------");
	}
	
	public boolean getKindFood() {
		return kindFood;
	}
	
	public void setKindFood(boolean kindFood) {
		this.kindFood = kindFood;
	}
	
	public boolean isKindFood() {
		return kindFood;
	}
}