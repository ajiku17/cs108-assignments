package dao;

public class Product {

	private String id;
	private String name;
	private String imageFileName;
	private double price;
	
	/*
	 * Public constructor
	 */
	public Product(String id, String name, String imageFileName, double price) {
		this.id = id;
		this.name = name;
		this.imageFileName = imageFileName;
		this.price = price;
	}
	
	/*
	 * Getters
	 */
	public double getPrice() {
		return price;
	}
	
	public String getID() {
		return id;
	}
	
	public String getImageFileName() {
		return imageFileName;
	}
	
	public String getName() {
		return name;
	}
	
	/*
	 * Setters
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public void setImageFileName(String fileName) {
		this.imageFileName = fileName;
	}
	
	/*
	 * Overriden toStirng and equals
	 */
	@Override
	public String toString() {
		return id + " " + name + " " + price + " " + imageFileName;  
	}
	
	
	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		
		if(!(other instanceof Product)) {
			return false;
		}
		
		Product otherAcc = (Product) other;
		
		return this.id == otherAcc.id;
	}
}
