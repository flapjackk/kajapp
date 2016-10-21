package org.lszita.kajapp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Login", 
			urlPatterns = {"/login"})
public class Login extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		RequestDispatcher view = req.getRequestDispatcher("/index.html");
		view.forward(req, res);
	}
}
