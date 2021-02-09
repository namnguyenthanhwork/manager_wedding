package Service.ServiceList;

import JDBC.ConnectSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Karaoke extends Service {
	private int rentTime;
	
	// auto create ID after create new object
	{
		setCode("amount_karaoke", "KARAOKE");
	}
	
	public Karaoke() {
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
			
			System.out.print("- Nhập thời gian muốn thuê: ");
			this.rentTime = scanner.nextInt();
			scanner.nextLine();
			
			PreparedStatement pst =
					conn.getConnection().prepareStatement(
							"insert into karaoke(karaoke_name, karaoke_code, rent_time, " +
									"karaoke_cost, karaoke_type) values(?,?,?,?,?)");
			
			pst.setString(1, this.serviceName);
			pst.setString(2, this.serviceCode);
			pst.setInt(3, this.rentTime);
			pst.setDouble(4, Math.round(this.serviceCost));
			pst.setString(5, this.serviceType);
			
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
			ResultSet rs = st.executeQuery("select * from karaoke");
			
			while (rs.next()) {
				this.serviceID = rs.getInt("id");
				this.serviceName = rs.getString("karaoke_name");
				this.serviceCode = rs.getString("karaoke_code");
				this.rentTime = rs.getInt("rent_time");
				this.serviceCost = rs.getDouble("karaoke_cost");
				this.serviceType = rs.getString("karaoke_type");
				
				super.displayService();
				System.out.printf("%14s\n", this.rentTime);
				System.out.println("-----------------------------------------" +
						"--------------------------------------------" +
						"--------------------");
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
				String.format("%s %15s",
						"-----------------------------------------------" +
								"--------------------------------------" +
								"--------------------", this.rentTime);
	}
	
	public int getRentTime() {
		return rentTime;
	}
	
	public void setRentTime(int rentTime) {
		this.rentTime = rentTime;
	}
}