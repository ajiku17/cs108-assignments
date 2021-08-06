package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Product;

public class ShoppingCart {

	private Map<Product, Integer> cart;
	
	/*
	 * Public consturctor
	 */
	public ShoppingCart() {
		cart = new HashMap<Product, Integer>();
	}
	
	/*
	 * Returns the full list of products in this cart
	 */
	public List<Product> getProducts(){
		List<Product> res = new ArrayList<Product>();
		for(Product p: cart.keySet()) {
			res.add(p);
		}
		return res;
	}
	
	/*
	 * The amount of a single product in this cart
	 */
	public int getFrequency(Product p) {
		if(cart.containsKey(p)) {
			return cart.get(p);
		}
		return 0;
	}
	
	/*
	 * Sets the amount
	 */
	public void setFrequency(Product p, int freq) {
		if(freq == 0) {
			if(cart.containsKey(p)) {
				removeFromCart(p);
			}
		}else {
			cart.put(p, freq);
		}
	}
	
	/*
	 * Removes a type of product form the cart
	 */
	private void removeFromCart(Product p) {
		cart.remove(p);
	}
	
	/*
	 * Adds an instance of a product
	 */
	public void addToCart(Product p) {
		if(cart.containsKey(p)) {
			cart.put(p, cart.get(p) + 1);
		}else {
			cart.put(p, 1);
		}
	}
	
	
}
