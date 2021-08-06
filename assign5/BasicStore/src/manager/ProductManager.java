package manager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Product;


public class ProductManager {

	private static ProductManager manager;
	private  List<Product> products;
	private Connection conn;
	
	/*
	 * Private constructor
	 */
	private ProductManager() throws SQLException{
		products = new ArrayList<Product>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/assign5_store", "root", "rootpass");
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("select * from products;");
			while(res.next()) {
				Product p = new Product(res.getString(1), res.getString(2), res.getString(3), res.getDouble(4));
				products.add(p);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Returns the singleton object
	 */
	public static ProductManager getInstance() throws SQLException {
		if(manager == null) {
			synchronized(ProductManager.class) {
				if(manager == null) {
					manager = new ProductManager();
				}
			}
		}
		return manager;
	}
	
	/*
	 * Returns whether the product is in stock or not
	 */
	public boolean inStock(String id) {
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getID().equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Finds and returns the product identified by the given parameter
	 */
	public Product getProduct(String id) {
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getID()	.equals(id)) {
				return products.get(i);
			}
		}
		return null;
	}
	
	/*
	 * Returns the full list of products
	 */
	public List<Product> getList(){
		return products;
	}
	
}
