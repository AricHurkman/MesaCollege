package MakeupTest;

import java.util.Random;

public class LoyaltyShopper extends Shopper {
	Random random = new Random();
	private String customerNumber;
	LoyaltyShopper(){
		super();

		for(int i = 0; i < 14; ++i) {
			int n = random.nextInt(10);
			this.customerNumber = this.customerNumber + Integer.toString(n);
		}
	}
	LoyaltyShopper(String shopperName, boolean newsLetter, int numItemsPurchased){
		super(shopperName,newsLetter,numItemsPurchased);
		for(int i = 0; i < 14; ++i) {
			int n = random.nextInt(10);
			this.customerNumber = this.customerNumber + Integer.toString(n);
		}
	}

	@Override
	public String getShopperName() {
		return super.getShopperName();
	}

	@Override
	public int getNumItemsPurchased() {
		return super.getNumItemsPurchased();
	}

	@Override
	public boolean isNewsLetter() {
		return super.isNewsLetter();
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void makeCustomerNumber(String customerNumber) {
		for(int i = 0; i < 14; ++i) {
			int n = random.nextInt(10);
			this.customerNumber = this.customerNumber + Integer.toString(n);
		}
	}
	public String shopperInfo(){
		return "Customer Number "+ getCustomerNumber() + " MakeupTest.Shopper Name " + getShopperName() + " Number Of Items Purchased " + getNumItemsPurchased() + " Mailing list " + isNewsLetter();
	}
}
 class Main{
	public static void main(String[] args0){
		LoyaltyShopper[] customers = new LoyaltyShopper[3000];
		customers[0] = new LoyaltyShopper ("Jane Doe", true, 5);
		customers[1] = new LoyaltyShopper("Maria Gonzales", true, 100);
// code omitted here fills in information for remaining customers
		customers[2998] = new LoyaltyShopper("Bob Bob", true, 10);
		customers[2999] = new LoyaltyShopper("Juan Rodriguez", false, 50);


		for (int i = 0; i < customers.length; i++){
			System.out.println(customers[i].shopperInfo());
		}

	}
}
