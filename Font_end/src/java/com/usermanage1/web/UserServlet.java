
package com.usermanag.web;

import com.usermanage.bean.user;
import com.usermanage.dao.UserDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.User;


@WebServlet(name = "userServlet", urlPatterns = {"/"})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao us;
	
        @Override
	public void init() {
		UserDao us = new UserDao();
	}

        @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

        @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertUser(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			default:
				listUser(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

        
        private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
        
          //inser user
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
        {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String ph = request.getParameter("phone");
		User newUser = new User(name, email, ph);
		UserDao.insertUser(newUser);
		response.sendRedirect("list");
	}

           //delete user
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException 
        {
		int id = Integer.parseInt(request.getParameter("id"));
               us.deleteUser(id);
		response.sendRedirect("list");

	}
        
        //edit user
        private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = us.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);

	}

      
        //uodate user
	private void updateUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String ph = request.getParameter("phone");

		User book = new User(id, name, email, ph);
		us.updateUser(book);
		response.sendRedirect("list");
	}
        
        
        //default of users
	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<User> listUser =  us.selectAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	}

	   
}
