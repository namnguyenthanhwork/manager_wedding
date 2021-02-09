package Service.ServiceList;

import JDBC.ConnectSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public abstract class Service {
	protected int serviceID;
	protected String serviceCode;
	protected String serviceName;
	protected double serviceCost;
	protected String serviceType;
	
	/**
	 * update amount product type
	 * @param col the column field value
	 * @param kindService the kind product value (singer, decoration...)
	 */
	protected void setCode(String col, String kindService) {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			st.executeQuery("select * from service");
			ResultSet rs = st.getResultSet();
			if (rs.next()) {
				int amount = rs.getInt(col);
				amount++;
				st.executeUpdate(String.format("update service set %s = %d", col, amount));
				this.serviceCode = String.format("%s%03d", kindService, amount);
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	 * add new service
	 * @param scanner input data from user
	 */
	public void inputService(Scanner scanner) {
		boolean isExist = false;
		
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			
			do {
				System.out.print("- Nhập tên dịch vụ: ");
				this.serviceName = scanner.nextLine();
				System.out.print("- Nhập loại dịch vụ: ");
				this.serviceType = scanner.nextLine();
				
				// check product is exist in db
				ResultSet rs = st.executeQuery("select * from " + this.serviceType.toLowerCase());
				
				String productName;
				while (rs.next()) {
					productName = rs.getString(this.serviceType.toLowerCase() + "_name");
					
					if (this.serviceName.equalsIgnoreCase(productName)) {
						System.out.println("<!> Dịch vụ đã tồn tại, vui lòng nhập dịch vụ khác");
						isExist = true;
						break;
					} else isExist = false;
				}
			} while (isExist);
			
			do {
				System.out.print("- Nhập giá dịch vụ: ");
				this.serviceCost = scanner.nextDouble();
				
				if (this.serviceCost < 0)
					System.out.println("<!> Giá dịch vụ không thể âm.");
			} while (this.serviceCost < 0);
			scanner.nextLine();
			
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * display service to terminal
	 */
	public void displayService() {
		System.out.printf("  %-7d %-30s %-12s %,12.0f vnđ %18s",
				this.serviceID, this.serviceName.toUpperCase(),
				this.serviceCode, this.serviceCost, this.serviceType.toUpperCase());
	}
	
	@Override
	public String toString() {
		return String.format("  %-7d %-30s %-12s %,8.0f vnđ %15s\n",
				this.serviceID, this.serviceName.toUpperCase(),
				this.serviceCode, this.serviceCost, this.serviceType.toUpperCase());
		
	}
	
	public int getServiceID() {
		return serviceID;
	}
	
	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}
	
	public String getServiceCode() {
		return serviceCode;
	}
	
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public double getServiceCost() {
		return serviceCost;
	}
	
	public void setServiceCost(double serviceCost) {
		this.serviceCost = serviceCost;
	}
	
	public String getServiceType() {
		return serviceType;
	}
	
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
}