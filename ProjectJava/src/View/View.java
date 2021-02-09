package View;

import Bill.ManagerBill;
import Lobby.LobbyPriceList;
import Lobby.ManagerLobby;
import Product.ManagerMenu;
import Product.ManagerProduct;
import Service.ManagerService;
import Statistics.Statistics;

import java.util.Scanner;

public class View {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		ManagerLobby ml = new ManagerLobby();
		ManagerMenu mm = new ManagerMenu();
		ManagerProduct mp = new ManagerProduct();
		ManagerService ms = new ManagerService();
		ManagerBill mb = new ManagerBill();
		Statistics stt = new Statistics();
		LobbyPriceList lpl = new LobbyPriceList();
		
		int choice;
		
		do {
			System.out.println("======QUẢN LÝ TIỆC CƯỚI=====");
			System.out.println("1. Quản lý sảnh");
			System.out.println("2. Quản lý thực phẩm");
			System.out.println("3. Quản lý dịch vụ");
			System.out.println("4. Quản lý bill");
			System.out.println("5. Quản lý danh sách các menu");
			System.out.println("6. Quản lý bảng giá sảnh");
			System.out.println("7. Thống kê");
			System.out.println("0. Thoát");
			System.out.println("=============================");
			System.out.print("=> Nhập lựa chọn: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
				case 1 -> ml.lobbyController();
				case 2 -> mp.productController();
				case 3 -> ms.serviceController();
				case 4 -> mb.billController();
				case 5 -> mm.menuController();
				case 6 -> lpl.priceListController();
				case 7 -> stt.statisticsController();
				default -> System.out.println("Bạn đã chọn thoát!!!");
			}
		} while (choice != 0);
	}
}
