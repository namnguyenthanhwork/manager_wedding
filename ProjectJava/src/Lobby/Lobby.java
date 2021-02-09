package Lobby;

import JDBC.ConnectSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Lobby {
	private int lobbyID;
	private String lobbyCode;
	private String lobbyName;
	private String lobbyLocation;
	private int lobbyCapacity;
	private String lobbyStatus;
	
	public Lobby() {
	}
	
	
	// auto create new ID after create object
	{
		ConnectSQL conn = new ConnectSQL();
		try {
			int amount = 0;
			Statement st = conn.getConnection().createStatement();
			st.executeQuery("select * from lobby");
			ResultSet rs = st.getResultSet();
			while (rs.next()) {
				amount = rs.getInt("id");
			}
			this.lobbyCode = String.format("%s%03d", "S", ++amount);
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		} finally {
			conn.close();
		}
	}
	
	/**
	 * input lobby from user
	 * @param scanner input value from user
	 * @throws SQLException
	 * Constructs a <code>SQLException</code> object with a given
	 * <code>reason</code>, <code>SQLState</code>
	 */
	public void inputLobby(Scanner scanner) throws SQLException {
		boolean isExist = false;
		
		ConnectSQL conn = new ConnectSQL();
		Statement st = conn.getConnection().createStatement();
		
		do {
			System.out.print("- Nhập tên sảnh: ");
			this.lobbyName = scanner.nextLine();
			
			// check lobby is exist in db
			ResultSet rs = st.executeQuery("select * from lobby");
			String lobbyName;
			
			while (rs.next()) {
				lobbyName = rs.getString("lobby_name");
				if (this.lobbyName.equalsIgnoreCase(lobbyName)) {
					System.out.println("<!> Sảnh đã tồn tại, vui lòng nhập sảnh khác");
					isExist = true;
					break;
				} else isExist = false;
			}
		} while (isExist);
		
		System.out.print("- Nhập vị trí sảnh: ");
		this.lobbyLocation = scanner.nextLine();
		
		do {
			System.out.print("- Nhập sức chứa (bàn): ");
			this.lobbyCapacity = scanner.nextInt();
			scanner.nextLine();
			
			if (this.lobbyCapacity <= 0)
				System.out.print("<!> Số bàn không thể <= 0");
		} while (this.lobbyCapacity <= 0);
		
		PreparedStatement pst =
				conn.getConnection().prepareStatement(
						"insert into lobby(lobby_name, lobby_code, lobby_location, " +
								"lobby_capacity, status) values(?,?,?,?,?)");
		
		this.lobbyStatus = "Rảnh";
		
		pst.setString(1, this.lobbyName);
		pst.setString(2, this.lobbyCode);
		pst.setString(3, this.lobbyLocation);
		pst.setDouble(4, this.lobbyCapacity);
		pst.setString(5, this.lobbyStatus);
		
		int i = pst.executeUpdate();
		if (i != 0) {
			System.out.println("Thêm thành công!");
		} else {
			System.out.println("<!> Thêm thất bại!");
		}
		st.close();
		conn.close();
	}
	
	/**
	 * display information lobby to terminal
	 */
	public void displayLobby() {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from lobby");
			
			while (rs.next()) {
				this.lobbyID = rs.getInt("id");
				this.lobbyName = rs.getString("lobby_name");
				this.lobbyCode = rs.getString("lobby_code");
				this.lobbyLocation = rs.getString("lobby_location");
				this.lobbyCapacity = rs.getInt("lobby_capacity");
				this.lobbyStatus = rs.getString("status");
				
				System.out.printf("  %-10d %-15s %-15s %-10s %10d %15s \n%s\n",
						this.lobbyID, this.lobbyName.toUpperCase(),
						this.lobbyCode, this.lobbyLocation.toUpperCase(),
						this.lobbyCapacity, this.lobbyStatus, "----------------" +
								"----------------------------------------------" +
								"--------------------------");
			}
			
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	public int getLobbyID() {
		return lobbyID;
	}
	
	public void setLobbyID(int lobbyID) {
		this.lobbyID = lobbyID;
	}
	
	public String getLobbyCode() {
		return lobbyCode;
	}
	
	public void setLobbyCode(String lobbyCode) {
		this.lobbyCode = lobbyCode;
	}
	
	public String getLobbyName() {
		return lobbyName;
	}
	
	public void setLobbyName(String lobbyName) {
		this.lobbyName = lobbyName;
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
