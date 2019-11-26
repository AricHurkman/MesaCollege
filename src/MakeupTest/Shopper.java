package MakeupTest;

public abstract class Shopper {

	private String shopperName;
	private boolean newsLetter;
	private int numItemsPurchased;

	public Shopper() {
		shopperName = "Doe";
		newsLetter = true;
		numItemsPurchased = 0;
	}
	public Shopper(String shopperName, boolean newsLetter, int numItemsPurchased){
		this.shopperName = shopperName;
		this.newsLetter = newsLetter;
		this.numItemsPurchased = numItemsPurchased;
	}

	public  String getShopperName() {
		return shopperName;
	}

	public int getNumItemsPurchased() {
		return numItemsPurchased;
	}

	public boolean isNewsLetter() {
		return newsLetter;
	}

	public void setShopperName(String shopperName) {
		this.shopperName = shopperName;
	}

	public void setNewsLetter(boolean newsLetter) {
		this.newsLetter = newsLetter;
	}

	public void setNumItemsPurchased(int numItemsPurchased) {
		this.numItemsPurchased = Math.abs(numItemsPurchased);
		System.out.println(this.numItemsPurchased);
	}
}