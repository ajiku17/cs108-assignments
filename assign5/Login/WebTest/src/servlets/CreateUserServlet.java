package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.AccountManager;

/**
 * Servlet implementation class CreateUserServlet
 */
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserServlet() {
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
//		doGet(request, response);
		ServletContext context = getServletContext();
		AccountManager manager = (AccountManager)context.getAttribute("accountManager");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(manager.registerUser(username, password)) {
			//Welcome
			RequestDispatcher dispatch = request.getRequestDispatcher("welcome.html");
			dispatch.forward(request, response);
		}else {
			//Incorrect
			RequestDispatcher dispatch = request.getRequestDispatcher("acc_inuse.jsp");
			dispatch.forward(request, response);
		}
	}

}
