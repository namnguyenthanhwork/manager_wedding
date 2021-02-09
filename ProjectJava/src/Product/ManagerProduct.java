package Product;

import JDBC.ConnectSQL;
import Product.ProductList.Drink;
import Product.ProductList.Food;
import Product.ProductList.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Vector;

public class ManagerProduct {
	public ManagerProduct() {
	}
	
	/**
	 * add new product to product list
	 * @param product the product type (drink, food...) value
	 */
	public void addProduct(Product product) {
		Scanner scanner = new Scanner(System.in);
		product.inputProduct(scanner);
	}
	
	/**
	 * update new product name in product list
	 * @param productNewName the new product name value
	 * @param productCode the product code value
	 */
	public void updateProductName(String productNewName, String productCode) {
		boolean flag = false;
		String productCodeTemp;
		productCodeTemp = productCode;
		String[] strSplit = productCodeTemp.split("[0-9]");
		String productType = strSplit[0];
		String productCodeDB;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from %s", productType.toLowerCase()));
			
			while (rs.next()) {
				productCodeDB = rs.getString(productType.toLowerCase() + "_code");
				
				if (productCodeDB.equalsIgnoreCase(productCode)) {
					st.executeUpdate(String.format("update %s set %s_name = '%s' where %s_code = '%s'",
							productType.toLowerCase(), productType.toLowerCase(),
							productNewName, productType.toLowerCase(), productCode));
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
	 * update new product price in product list
	 * @param productNewPrice the new product price value
	 * @param productCode the product code value
	 */
	public void updateProductPrice(double productNewPrice, String productCode) {
		boolean flag = false;
		String productCodeTemp;
		productCodeTemp = productCode;
		String[] strSplit = productCodeTemp.split("[0-9]");
		String productType = strSplit[0];
		String productCodeDB;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(
					String.format("select * from %s", productType.toLowerCase()));
			
			while (rs.next()) {
				productCodeDB = rs.getString(productType.toLowerCase() + "_code");
				
				if (productCodeDB.equalsIgnoreCase(productCode)) {
					st.executeUpdate(String.format("update %s set %s_cost = '%f' where %s_code = '%s'",
							productType.toLowerCase(), productType.toLowerCase(),
							productNewPrice, productType.toLowerCase(), productCode));
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
	 * update new product manufacturer in product list
	 * @param newManufacturer the new manufacturer value
	 * @param productCode the product code value
	 */
	public void updateProductManufacturer(String newManufacturer, String productCode) {
		boolean flag = false;
		String rsProductCode;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from drink");
			
			while (rs.next()) {
				rsProductCode = rs.getString("drink_code");
				if (productCode.equalsIgnoreCase(rsProductCode))    {
					st.executeUpdate(
							String.format("update drink set manufacturer = '%s' where drink_code = '%s'",
									newManufacturer, productCode));
					System.out.println("Update thành công");
					flag = true;
					break;
				}
			}
			if (!flag) {
				System.out.println("<!>Không tìm thấy");
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * check product delete successful or failed
	 * @param productCode the product code value
	 * @return true if delete product successful else return false
	 */
	public boolean deleteProduct(String productCode) {
		String productCodeTemp;
		productCodeTemp = productCode;
		String[] strSplit = productCodeTemp.split("[0-9]");
		String productType = strSplit[0];
		String productCodeDB;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from " + productType.toLowerCase());
			
			while (rs.next()) {
				productCodeDB = rs.getString(productType.toLowerCase() + "_code");
				if (productCodeDB.equalsIgnoreCase(productCode)) {
					// delete product in db
					try {
						st.executeUpdate(String.format("delete from %s where %s = '%s';",
								productType.toLowerCase(),
								productType.toLowerCase() + "_code", productCode.toUpperCase()));
						st.executeUpdate(String.format("alter table %s drop id;",
								productType.toUpperCase()));
						st.executeUpdate(String.format("alter table %s auto_increment = 1;",
								productType.toUpperCase()));
						st.executeUpdate(
								String.format("alter table %s add id int not null auto_increment primary key first;",
										productType.toUpperCase()));
						
						// update amount in table product
						st.executeQuery("select * from product");
						rs = st.getResultSet();
						if (rs.next()) {
							int amount = rs.getInt("amount_" + productType.toLowerCase());
							amount--;
							st.executeUpdate(String.format("update product set amount_%s = %d",
									productType.toLowerCase(), amount));
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
	 * reset product code after delete
	 * @param productType the product type value
	 */
	public void resetProductCode(String productType) {
		int amount = 0;
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from product");
			
			if (rs.next())
				amount = rs.getInt(String.format("amount_%s", productType));
			
			st.executeQuery(String.format("select * from %s", productType));
			for (int i = 1; i <= amount; i++)
				st.executeUpdate(String.format("update %s set %s_code = '%s%03d' where id = '%d'",
						productType.toLowerCase(), productType.toLowerCase(),
						productType.toUpperCase(), i, i));
			
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * keyword-based search
	 * @param keyword the keyword (product name, product code,
	 *                product type, manufacturer) value
	 * @return product list after keyword-based search
	 */
	public Vector<String> search(String keyword) {
		int productID = 0;
		double productCost;
		String productType, productName, productCode, manufacturer;
		Vector<String> v = new Vector<>();
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			mergeTableProduct();
			ResultSet rs = st.executeQuery("select * from product_list");
			
			while (rs.next()) {
				productName = rs.getString("product_name");
				productCode = rs.getString("product_code");
				productCost = rs.getDouble("product_cost");
				productType = rs.getString("product_type");
				manufacturer = rs.getString("manufacturer");
				
				if (productName.contains(keyword) || productCode.contains(keyword) ||
						productType.contains(keyword) || manufacturer.contains(keyword)) {
					String s = String.format("  %-7d %-30s %-12s %,12.0f vnđ %15s %25s\n%s",
							++productID, productName.toUpperCase(),
							productCode, productCost, productType.toUpperCase(), manufacturer,
							"----------------------------------------------------------------" +
									"--------------------------------------------------");
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
	 * search by product price
	 * @param fromPrice the price begin search value
	 * @param toPrice the price end search value
	 * @return product list after search by price
	 */
	public Vector<String> search(double fromPrice, double toPrice) {
		int productID = 0;
		double productCost;
		String productType, productName, productCode, manufacturer;
		Vector<String> v = new Vector<>();
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			mergeTableProduct();
			ResultSet rs = st.executeQuery("select * from product_list");
			
			while (rs.next()) {
				productName = rs.getString("product_name");
				productCode = rs.getString("product_code");
				productCost = rs.getDouble("product_cost");
				productType = rs.getString("product_type");
				manufacturer = rs.getString("manufacturer");
				
				if (fromPrice <= productCost && productCost <= toPrice) {
					String s = String.format("  %-7d %-30s %-12s %,12.0f vnđ %15s %25s\n%s",
							++productID, productName.toUpperCase(),
							productCode, productCost, productType.toUpperCase(), manufacturer,
							"----------------------------------------------------------------" +
									"--------------------------------------------------");
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
	 * Sorts the specified array into ascending numerical order.
	 * @param i Sorts the specified array into ascending numerical order if i > 0
	 *          Sorts the specified array into decreasing numerical order if i<= 0
	 */
	public void sortName(int i) {
		int productID = 0;
		double productCost;
		String productName, productCode, productType, manufacturer;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs;
			
			if (i > 0)
				rs = st.executeQuery("select * from product_list order by product_name");
			else
				rs = st.executeQuery("select * from product_list order by product_name desc");
			
			titleProduct();
			while (rs.next()) {
				productName = rs.getString("product_name");
				productCode = rs.getString("product_code");
				productCost = rs.getDouble("product_cost");
				productType = rs.getString("product_type");
				manufacturer = rs.getString("manufacturer");
				
				System.out.printf("  %-7d %-30s %-12s %,12.0f vnđ %15s %25s\n%s",
						++productID, productName.toUpperCase(),
						productCode, productCost, productType.toUpperCase(), manufacturer,
						"----------------------------------------------------------------" +
								"--------------------------------------------------\n");
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * Sorts the specified array into ascending numerical order.
	 * @param i Sorts the specified array into ascending numerical order if i > 0
	 *         Sorts the specified array into decreasing numerical order if i<= 0
	 */
	public void sortPrice(int i) {
		int productID = 0;
		double productCost;
		String productName, productCode, productType, manufacturer;
		ConnectSQL conn = new ConnectSQL();
		
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs;
			
			if (i > 0)
				rs = st.executeQuery("select * from product_list order by product_cost");
			else
				rs = st.executeQuery("select * from product_list order by product_cost desc");
			
			titleProduct();
			while (rs.next()) {
				productName = rs.getString("product_name");
				productCode = rs.getString("product_code");
				productCost = rs.getDouble("product_cost");
				productType = rs.getString("product_type");
				manufacturer = rs.getString("manufacturer");
				
				System.out.printf("  %-7d %-30s %-12s %,12.0f vnđ %15s %25s\n%s",
						++productID, productName.toUpperCase(),
						productCode, productCost, productType.toUpperCase(), manufacturer,
						"----------------------------------------------------------------" +
								"--------------------------------------------------\n");
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * display product list to terminal
	 */
	public void displayProductList() {
		titleProduct();
		Vector<String> v = getData();
		for (String s : v) System.out.println(s);
	}
	
	/**
	 * merge tables product in database
	 */
	private void mergeTableProduct() {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			
			st.executeUpdate("truncate product_list");
			st.executeUpdate("INSERT INTO product_list " +
					"(product_name,product_code,product_cost,product_type)\n" +
					"  SELECT  " +
					"food_name,food_code,food_cost,food_type FROM food;");
			st.executeUpdate("INSERT INTO product_list " +
					"(product_name,product_code,product_cost,product_type,manufacturer)\n" +
					"  SELECT  " +
					"drink_name,drink_code,drink_cost,drink_type,manufacturer " +
					"FROM drink;");
			
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * this function support function display product
	 * @return product list after merge
	 */
	private Vector<String> getData() {
		int productID = 0;
		double productCost;
		String productType, productName, productCode, manufacturer;
		Vector<String> v = new Vector<>();
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			mergeTableProduct();
			ResultSet rs = st.executeQuery("select * from product_list");
			
			while (rs.next()) {
				productName = rs.getString("product_name");
				productCode = rs.getString("product_code");
				productCost = rs.getDouble("product_cost");
				productType = rs.getString("product_type");
				manufacturer = rs.getString("manufacturer");
				
				String s = String.format("  %-7d %-30s %-12s %,12.0f vnđ %15s %25s\n%s",
						++productID, productName.toUpperCase(),
						productCode, productCost, productType.toUpperCase(), manufacturer,
						"----------------------------------------------------------------" +
								"--------------------------------------------------");
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
	 * show title product (id - product name - product code - product price...)
	 */
	private void titleProduct() {
		System.out.printf("  %-6s %-30s %-15s %14s %20s %20s\n", "ID", "TÊN SẢN PHẨM",
				"MÃ SẢN PHẨM", "GIÁ SẢN PHẨM", "LOẠI SẢN PHẨM", "HÃNG SẢN XUẤT");
		System.out.println("----------------------------------------------------" +
				"--------------------------------------------------------------");
	}
	
	/**
	 * performing user actions (add, display, update, search...)
	 */
	public void productController() {
		Scanner scanner = new Scanner(System.in);
		ManagerProduct l = new ManagerProduct();
		int choice;
		String newName, productCode, manufacturer, keyword;
		double newPrice, fromPrice, toPrice;
		
		do {
			System.out.println("===========================MENU==========================");
			System.out.println("1. Hiện thị danh sách sản phẩm");
			System.out.println("2. Thêm sản phẩm");
			System.out.println("3. Xoá sản phẩm");
			System.out.println("4. Sửa thông tin (tên sản phẩm)");
			System.out.println("5. Sửa thông tin (giá sản phẩm)");
			System.out.println("6. Sửa thông tin (hãng sản phẩm - chỉ dành cho thức uống)");
			System.out.println("7. Tìm kiếm sản phẩm theo từ khoá");
			System.out.println("8. Tìm kiếm sản phẩm theo giá");
			System.out.println("9. Sắp xếp sản phẩm theo tên");
			System.out.println("10. Sắp xếp sản phẩm theo giá");
			System.out.println("0. Thoát");
			System.out.println("=========================================================");
			System.out.print("=> Nhập lựa chọn: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1 -> l.displayProductList();
				case 2 -> {
					int choiceProduct;
					
					System.out.println("======== MENU =======");
					System.out.println("1. Thêm thực phẩm");
					System.out.println("2. Thêm thức uống");
					System.out.println("3. Thoát");
					System.out.println("=====================");
					System.out.print("=> Nhập lựa chọn: ");
					choiceProduct = scanner.nextInt();
					scanner.nextLine();
					
					switch (choiceProduct) {
						case 1 -> l.addProduct(new Food());
						case 2 -> l.addProduct(new Drink());
						default -> System.out.println("=> Bạn đã chọn thoát!");
					}
				}
				case 3 -> {
					System.out.print("- Nhập mã sản phẩm cần xoá: ");
					productCode = scanner.nextLine();
					
					if (l.deleteProduct(productCode)) {
						resetProductCode(productCode.split("[0-9]")[0]);
						System.out.println("=> Xoá thành công!");
					} else System.out.println("<!> Xoá thất bại");
				}
				case 4 -> {
					System.out.print("- Nhập mã sản phẩm: ");
					productCode = scanner.nextLine();
					
					System.out.print("- Nhập tên sản phẩm mới: ");
					newName = scanner.nextLine();
					
					l.updateProductName(newName, productCode);
				}
				case 5 -> {
					System.out.print("- Nhập mã sản phẩm: ");
					productCode = scanner.nextLine();
					
					System.out.print("- Nhập giá mới: ");
					newPrice = scanner.nextDouble();
					scanner.nextLine();
					
					l.updateProductPrice(newPrice, productCode);
				}
				case 6 -> {
					System.out.print("- Nhập mã sản phẩm: ");
					productCode = scanner.nextLine();
					
					System.out.print("- Nhập hãng sản xuất:");
					manufacturer = scanner.nextLine();
					
					l.updateProductManufacturer(manufacturer, productCode);
				}
				case 7 -> {
					Vector<String> v;
					System.out.print("- Nhập từ khoá: ");
					keyword = scanner.nextLine();
					
					v = l.search(keyword);
					l.titleProduct();
					for (String s : v) System.out.println(s);
				}
				case 8 -> {
					Vector<String> v;
					
					System.out.print("- Nhập giá bắt đầu: ");
					fromPrice = scanner.nextDouble();
					
					System.out.print("- Nhập giá kết thúc: ");
					toPrice = scanner.nextDouble();
					scanner.nextLine();
					
					l.titleProduct();
					v = l.search(fromPrice, toPrice);
					for (String s : v) System.out.println(s);
				}
				case 9 -> {
					int i;
					
					System.out.print("- Sắp xếp tăng (nhấn 1), giảm (nhấn -1): ");
					i = scanner.nextInt();
					scanner.nextLine();
					
					l.sortName(i);
				}
				case 10 -> {
					int i;
					
					System.out.print("- Sắp xếp tăng (nhấn 1), giảm (nhấn -1): ");
					i = scanner.nextInt();
					scanner.nextLine();
					
					l.sortPrice(i);
				}
				default -> System.out.println("- Bạn đã chọn thoát!");
			}
		} while (choice != 0);
	}
}