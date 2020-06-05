

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/App")
public class App extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("action");
		HttpSession session = request.getSession();
		
		if ( parameter == null || parameter.equals("") || session==null || parameter.equals("loginform"))
		{
			response.sendRedirect("login.html");
		}
		else if(parameter.equals("registerform"))
		{
			response.sendRedirect("registration.html");
		}
		else if(parameter.equals("log in"))
		{
			String loginIn=request.getParameter("login");  
			String passIn=request.getParameter("pass");
			if(!loginIn.isEmpty() && !passIn.isEmpty())
			{
				try
		        {  	
					Class.forName("com.mysql.jdbc.Driver").newInstance();
		        	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306" ,"root", "root");
		        	String str = "SELECT * FROM cloud.users1 WHERE login='" + loginIn + "' and pass='" + passIn + "'";
		        	PreparedStatement stm=con.prepareStatement(str);  
		        	ResultSet rs = stm.executeQuery();
		        	if(rs.next())
		            {
		        		String login = rs.getString("login");
			            String name = rs.getString("name");
			            String surname = rs.getString("surname");
			            String email = rs.getString("email"); 
			            String pass = rs.getString("pass");

						String id = rs.getString("id");
		            	session.setAttribute("login", login);
		    	        session.setAttribute("name", name);
		    	        session.setAttribute("surname", surname);
		    	        session.setAttribute("email", email);
		    	        session.setAttribute("pass", pass);

						session.setAttribute("id", id);
		    	        response.sendRedirect("BoardView");
		            }
		        	else response.sendRedirect("login.html");
		        }
		        catch (Exception e2) 
				{ 
		        	System.out.println(e2);
		        	response.sendRedirect("login.html");
				}	
			}
			
		}

	}
}