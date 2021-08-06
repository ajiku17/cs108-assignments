package servlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manager.ProductManager;
import manager.ShoppingCart;

/**
 * Servlet implementation class ShoppingCartServlet
 */
public class ShoppingCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProductManager manager = (ProductManager)getServletContext().getAttribute("productManager");
		ShoppingCart cart = (ShoppingCart)request.getSession().getAttribute("shoppingCart");
		
		String productID = (String)request.getParameter("productID");
		if(productID != null) {
			if(manager.inStock(productID)) {
				cart.addToCart(manager.getProduct(productID));
			}
		}else {
			Enumeration e = request.getParameterNames();
			while(e.hasMoreElements()) {
				String id = (String) e.nextElement();
				int frequency = Integer.valueOf(request.getParameter(id));
				cart.setFrequency(manager.getProduct(id), frequency);
			}
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("shoppingCart.jsp");
		dispatch.forward(request, response);
	}

}
