package Product.ProductList;

import JDBC.ConnectSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public abstract class Product {
	protected int productID;
	protected String productCode;
	protected String productName;
	protected double productCost;
	protected String productType;
	
	/**
	 * update amount product type
	 * @param col the column field value
	 * @param kindProduct the kind product value (drink, food...)
	 */
	protected void setCode(String col, String kindProduct) {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			st.executeQuery("select * from product");
			ResultSet rs = st.getResultSet();
			
			if (rs.next()) {
				int amount = rs.getInt(col);
				amount++;
				st.executeUpdate(String.format("update product set %s = %d", col, amount));
				this.productCode = String.format("%s%03d", kindProduct, amount);
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	 * add new product
	 * @param scanner input data from user
	 */
	public void inputProduct(Scanner scanner) {
		boolean isExist = false;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			do {
				System.out.print("- Nhập tên sản phẩm: ");
				this.productName = scanner.nextLine();
				System.out.print("- Nhập loại sản phẩm: ");
				this.productType = scanner.nextLine();
				
				// check product is exist in db
				ResultSet rs = st.executeQuery("select * from " + this.productType.toLowerCase());
				
				String productName;
				
				while (rs.next()) {
					productName = rs.getString(this.productType.toLowerCase() + "_name");
					
					if (this.productName.equalsIgnoreCase(productName)) {
						System.out.println("<!> Sản phẩm đã tồn tại, vui lòng nhập sản phẩm khác");
						isExist = true;
						break;
					} else isExist = false;
				}
			} while (isExist);
			
			do {
				System.out.print("- Nhập giá sản phẩm: ");
				this.productCost = scanner.nextDouble();
				
				if (this.productCost < 0)
					System.out.println("<!> Giá sản phẩm không thể âm.");
			} while (this.productCost < 0);
			scanner.nextLine();
			
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * display product to terminal
	 */
	public void displayProduct() {
		System.out.printf("  %-7d %-30s %-12s %,12.0f vnđ %18s",
				this.productID, this.productName.toUpperCase(),
				this.productCode, this.productCost, this.productType.toUpperCase());
	}
	
	@Override
	public String toString() {
		return String.format("  %-7d %-30s %-12s %,8.0f vnđ %15s\n",
				this.productID, this.productName.toUpperCase(),
				this.productCode, this.productCost, this.productType.toUpperCase());
		
	}
	
	public int getProductID() {
		return productID;
	}
	
	public void setProductID(int productID) {
		this.productID = productID;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public double getProductCost() {
		return productCost;
	}
	
	public void setProductCost(double productCost) {
		this.productCost = productCost;
	}
	
	public String getProductType() {
		return productType;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getProductCode() {
		return productCode;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
