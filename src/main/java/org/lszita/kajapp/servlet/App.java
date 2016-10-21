package org.lszita.kajapp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "App", 
			urlPatterns = {"/app"})
public class App extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		
		//res.sendRedirect("/");
		
		RequestDispatcher view = req.getRequestDispatcher("/app.html");
		view.forward(req, res);
	}
}
