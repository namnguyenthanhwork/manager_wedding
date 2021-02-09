package Service.ServiceList;

import JDBC.ConnectSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Decoration extends Service {
	public Decoration() {
	}
	
	// auto create new ID after create new object
	{
		setCode("amount_decoration", "DECORATION");
	}
	
	/**
	 * add new service
	 * @param scanner input data from user
	 */
	@Override
	public void inputService(Scanner scanner) {
		try {
			ConnectSQL conn = new ConnectSQL();
			Statement st = conn.getConnection().createStatement();
			
			super.inputService(scanner);
			
			PreparedStatement pst =
					conn.getConnection().prepareStatement(
							"insert into decoration(decoration_name, decoration_code, " +
									"decoration_cost, decoration_type) values(?,?,?,?)");
			
			pst.setString(1, this.serviceName);
			pst.setString(2, this.serviceCode);
			pst.setDouble(3, Math.round(this.serviceCost));
			pst.setString(4, this.serviceType);
			
			int i = pst.executeUpdate();
			if (i != 0) {
				System.out.println("Thêm thành công!");
			} else {
				System.out.println("<!> Thêm thất bại!");
			}
			st.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * display service to terminal
	 */
	@Override
	public void displayService() {
		try {
			ConnectSQL conn = new ConnectSQL();
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from decoration");
			
			while (rs.next()) {
				this.serviceID = rs.getInt("id");
				this.serviceName = rs.getString("decoration_name");
				this.serviceCode = rs.getString("decoration_code");
				this.serviceCost = rs.getDouble("decoration_cost");
				this.serviceType = rs.getString("decoration_type");
				
				super.displayService();
				System.out.println("\n----------------------------------------" +
						"----------------------------------------------------");
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
						"-----------------------------------------------" +
								"--------------------------------------" +
								"--------------------");
	}
}