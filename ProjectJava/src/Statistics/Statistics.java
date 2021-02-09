package Statistics;

import JDBC.ConnectSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Statistics {
	private double priceJan;
	private double priceFeb;
	private double priceMar;
	private double priceApr;
	private double priceMay;
	private double priceJun;
	private double priceJul;
	private double priceAug;
	private double priceSep;
	private double priceOct;
	private double priceNov;
	private double priceDec;
	private double price1StQuarter;
	private double price2NdQuarter;
	private double price3RdQuarter;
	private double price4ThQuarter;
	
	public Statistics() {
	}
	
	/**
	 * statistics by month with year input from user
	 * @param year the year value
	 */
	public void statisticalMonthOfYear(String year) {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from statistics_bill " +
					"where year = '%s'", year));
			
			if (rs.next()) {
				this.priceJan = rs.getDouble("january");
				this.priceFeb = rs.getDouble("february");
				this.priceMar = rs.getDouble("march");
				this.priceApr = rs.getDouble("april");
				this.priceMay = rs.getDouble("may");
				this.priceJun = rs.getDouble("june");
				this.priceJul = rs.getDouble("july");
				this.priceAug = rs.getDouble("august");
				this.priceSep = rs.getDouble("september");
				this.priceOct = rs.getDouble("october");
				this.priceNov = rs.getDouble("november");
				this.priceDec = rs.getDouble("december");
				
				chartMonth(year, this.priceJan, this.priceFeb, this.priceMar, this.priceApr,
						this.priceMay, this.priceJun, this.priceJul, this.priceAug,
						this.priceSep, this.priceOct, this.priceNov, this.priceDec);
			} else
				System.out.println("Không tìm thấy năm yêu cầu !!!");
			
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * statistics by month every year with data in database
	 */
	public void statisticsMonthEveryYear() {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from statistics_bill");
			
			while (rs.next()) {
				String year = rs.getString("year");
				this.priceJan = rs.getDouble("january");
				this.priceFeb = rs.getDouble("february");
				this.priceMar = rs.getDouble("march");
				this.priceApr = rs.getDouble("april");
				this.priceMay = rs.getDouble("may");
				this.priceJun = rs.getDouble("june");
				this.priceJul = rs.getDouble("july");
				this.priceAug = rs.getDouble("august");
				this.priceSep = rs.getDouble("september");
				this.priceOct = rs.getDouble("october");
				this.priceNov = rs.getDouble("november");
				this.priceDec = rs.getDouble("december");
				
				chartMonth(year, this.priceJan, this.priceFeb, this.priceMar, this.priceApr,
						this.priceMay, this.priceJun, this.priceJul, this.priceAug,
						this.priceSep, this.priceOct, this.priceNov, this.priceDec);
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * statistics by quarter with year input data from user
	 * @param year the year value
	 */
	public void statisticalQuarterOfYear(String year) {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery(String.format("select * from statistics_bill " +
					"where year = '%s'", year));
			
			if (rs.next()) {
				this.priceJan = rs.getDouble("january");
				this.priceFeb = rs.getDouble("february");
				this.priceMar = rs.getDouble("march");
				this.priceApr = rs.getDouble("april");
				this.priceMay = rs.getDouble("may");
				this.priceJun = rs.getDouble("june");
				this.priceJul = rs.getDouble("july");
				this.priceAug = rs.getDouble("august");
				this.priceSep = rs.getDouble("september");
				this.priceOct = rs.getDouble("october");
				this.priceNov = rs.getDouble("november");
				this.priceDec = rs.getDouble("december");
				
				this.price1StQuarter = this.priceJan + this.priceFeb + this.priceMar;
				this.price2NdQuarter = this.priceApr + this.priceMay + this.priceJun;
				this.price3RdQuarter = this.priceJul + this.priceAug + this.priceSep;
				this.price4ThQuarter = this.priceOct + this.priceNov + this.priceDec;
				
				chartQuarter(year, this.price1StQuarter, this.price2NdQuarter,
						this.price3RdQuarter, this.price4ThQuarter);
			} else
				System.out.println("Không tìm thấy năm yêu cầu!!!");
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * statistics by quarter every year with data in database
	 */
	public void statisticsQuarterEveryYear() {
		ConnectSQL conn = new ConnectSQL();
		try {
			Statement st = conn.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from statistics_bill");
			
			while (rs.next()) {
				String year = rs.getString("year");
				this.priceJan = rs.getDouble("january");
				this.priceFeb = rs.getDouble("february");
				this.priceMar = rs.getDouble("march");
				this.priceApr = rs.getDouble("april");
				this.priceMay = rs.getDouble("may");
				this.priceJun = rs.getDouble("june");
				this.priceJul = rs.getDouble("july");
				this.priceAug = rs.getDouble("august");
				this.priceSep = rs.getDouble("september");
				this.priceOct = rs.getDouble("october");
				this.priceNov = rs.getDouble("november");
				this.priceDec = rs.getDouble("december");
				
				this.price1StQuarter = this.priceJan + this.priceFeb + this.priceMar;
				this.price2NdQuarter = this.priceApr + this.priceMay + this.priceJun;
				this.price3RdQuarter = this.priceJul + this.priceAug + this.priceSep;
				this.price4ThQuarter = this.priceOct + this.priceNov + this.priceDec;
				
				chartQuarter(year, this.price1StQuarter, this.price2NdQuarter,
						this.price3RdQuarter, this.price4ThQuarter);
			}
			st.close();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		conn.close();
	}
	
	/**
	 * show interface in console
	 * @param price the price value
	 * @return one string show interface with blocks
	 */
	private String blockChartInterface(double price) {
		String block = "";
		for (int i = 0; i < price / 1000000; ++i) {
			block = block.concat("=");
		}
		return block;
	}
	
	/**
	 * show chart by months in one year
	 * @param year the year value
	 * @param priceJan the sum of price in january value
	 * @param priceFeb the sum of price in february value
	 * @param priceMar the sum of price in march value
	 * @param priceApr the sum of price in april value
	 * @param priceMay the sum of price in may value
	 * @param priceJun the sum of price in june value
	 * @param priceJul the sum of price in july value
	 * @param priceAug the sum of price in august value
	 * @param priceSep the sum of price in september value
	 * @param priceOct the sum of price in october value
	 * @param priceNov the sum of price in november value
	 * @param priceDec the sum of price in december value
	 */
	private void chartMonth(String year,
	                        double priceJan, double priceFeb, double priceMar,
	                        double priceApr, double priceMay, double priceJun,
	                        double priceJul, double priceAug, double priceSep,
	                        double priceOct, double priceNov, double priceDec) {
		System.out.printf("%10s  DOANH THU CÁC THÁNG NĂM %s (triệu đồng)  \n", "", year);
		System.out.printf("Tháng 01: %-50s %,.0f vnđ\n", blockChartInterface(priceJan), priceJan);
		System.out.printf("Tháng 02: %-50s %,.0f vnđ\n", blockChartInterface(priceFeb), priceFeb);
		System.out.printf("Tháng 03: %-50s %,.0f vnđ\n", blockChartInterface(priceMar), priceMar);
		System.out.printf("Tháng 04: %-50s %,.0f vnđ\n", blockChartInterface(priceApr), priceApr);
		System.out.printf("Tháng 05: %-50s %,.0f vnđ\n", blockChartInterface(priceMay), priceMay);
		System.out.printf("Tháng 06: %-50s %,.0f vnđ\n", blockChartInterface(priceJun), priceJun);
		System.out.printf("Tháng 07: %-50s %,.0f vnđ\n", blockChartInterface(priceJul), priceJul);
		System.out.printf("Tháng 08: %-50s %,.0f vnđ\n", blockChartInterface(priceAug), priceAug);
		System.out.printf("Tháng 09: %-50s %,.0f vnđ\n", blockChartInterface(priceSep), priceSep);
		System.out.printf("Tháng 10: %-50s %,.0f vnđ\n", blockChartInterface(priceOct), priceOct);
		System.out.printf("Tháng 11: %-50s %,.0f vnđ\n", blockChartInterface(priceNov), priceNov);
		System.out.printf("Tháng 12: %-50s %,.0f vnđ\n", blockChartInterface(priceDec), priceDec);
	}
	
	/**
	 * show chart by quarters in one year
	 * @param year the year value
	 * @param the1StQuarter the sum of price in the first quarter value
	 * @param the2NdQuarter the sum of price in the second quarter value
	 * @param the3RdQuarter the sum of price in the third quarter value
	 * @param the4ThQuarter the sum of price in the fourth quarter value
	 */
	private void chartQuarter(String year, double the1StQuarter, double the2NdQuarter,
	                          double the3RdQuarter, double the4ThQuarter) {
		System.out.printf("%10s  DOANH THU CÁC QUÝ NĂM %s (triệu đồng) \n", "", year);
		System.out.printf("Quý 01: %-50s %,.0f vnđ\n",
				blockChartInterface(the1StQuarter), the1StQuarter);
		System.out.printf("Quý 02: %-50s %,.0f vnđ\n",
				blockChartInterface(the2NdQuarter), the2NdQuarter);
		System.out.printf("Quý 03: %-50s %,.0f vnđ\n",
				blockChartInterface(the3RdQuarter), the3RdQuarter);
		System.out.printf("Quý 04: %-50s %,.0f vnđ\n",
				blockChartInterface(the4ThQuarter), the4ThQuarter);
	}
	
	/**
	 * performing user actions (statistics by months, by quarters)
	 */
	public void statisticsController() {
		Scanner scanner = new Scanner(System.in);
		int choice;
		String year;
		
		do {
			System.out.println("===========================MENU==========================");
			System.out.println("1. Thống kê theo tháng (năm nhất định)");
			System.out.println("2. Thống kê theo tháng (mỗi năm)");
			System.out.println("3. Thống kê theo quý (năm nhất định)");
			System.out.println("4. Thống kê theo quý (mỗi năm)");
			System.out.println("0. Thoát");
			System.out.println("=========================================================");
			System.out.print("=> Nhập lựa chọn: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1 -> {
					System.out.print("- Nhập năm cần thống kê: ");
					year = scanner.nextLine();
					
					statisticalMonthOfYear(year);
				}
				case 2 -> statisticsMonthEveryYear();
				case 3 -> {
					System.out.print("- Nhập năm cần thống kê: ");
					year = scanner.nextLine();
					
					statisticalQuarterOfYear(year);
				}
				case 4 -> statisticsQuarterEveryYear();
				default -> System.out.println("- Bạn đã chọn thoát!");
			}
		} while (choice != 0);
	}
	
	public double getPriceJan() {
		return priceJan;
	}
	
	public void setPriceJan(double priceJan) {
		this.priceJan = priceJan;
	}
	
	public double getPriceFeb() {
		return priceFeb;
	}
	
	public void setPriceFeb(double priceFeb) {
		this.priceFeb = priceFeb;
	}
	
	public double getPriceMar() {
		return priceMar;
	}
	
	public void setPriceMar(double priceMar) {
		this.priceMar = priceMar;
	}
	
	public double getPriceApr() {
		return priceApr;
	}
	
	public void setPriceApr(double priceApr) {
		this.priceApr = priceApr;
	}
	
	public double getPriceMay() {
		return priceMay;
	}
	
	public void setPriceMay(double priceMay) {
		this.priceMay = priceMay;
	}
	
	public double getPriceJun() {
		return priceJun;
	}
	
	public void setPriceJun(double priceJun) {
		this.priceJun = priceJun;
	}
	
	public double getPriceJul() {
		return priceJul;
	}
	
	public void setPriceJul(double priceJul) {
		this.priceJul = priceJul;
	}
	
	public double getPriceAug() {
		return priceAug;
	}
	
	public void setPriceAug(double priceAug) {
		this.priceAug = priceAug;
	}
	
	public double getPriceSep() {
		return priceSep;
	}
	
	public void setPriceSep(double priceSep) {
		this.priceSep = priceSep;
	}
	
	public double getPriceOct() {
		return priceOct;
	}
	
	public void setPriceOct(double priceOct) {
		this.priceOct = priceOct;
	}
	
	public double getPriceNov() {
		return priceNov;
	}
	
	public void setPriceNov(double priceNov) {
		this.priceNov = priceNov;
	}
	
	public double getPriceDec() {
		return priceDec;
	}
	
	public void setPriceDec(double priceDec) {
		this.priceDec = priceDec;
	}
	
	public double getPrice1StQuarter() {
		return price1StQuarter;
	}
	
	public void setPrice1StQuarter(double price1StQuarter) {
		this.price1StQuarter = price1StQuarter;
	}
	
	public double getPrice2NdQuarter() {
		return price2NdQuarter;
	}
	
	public void setPrice2NdQuarter(double price2NdQuarter) {
		this.price2NdQuarter = price2NdQuarter;
	}
	
	public double getPrice3RdQuarter() {
		return price3RdQuarter;
	}
	
	public void setPrice3RdQuarter(double price3RdQuarter) {
		this.price3RdQuarter = price3RdQuarter;
	}
	
	public double getPrice4ThQuarter() {
		return price4ThQuarter;
	}
	
	public void setPrice4ThQuarter(double price4ThQuarter) {
		this.price4ThQuarter = price4ThQuarter;
	}
}