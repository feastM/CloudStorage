import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String parameter = request.getParameter("action");
		HttpSession session = request.getSession();
		
		if(parameter.equals("register"))
		{
			String name=request.getParameter("name");
			String surname=request.getParameter("surname");
			String login=request.getParameter("login");  
			String email=request.getParameter("email"); 
			String pass=request.getParameter("pass");
			
			try
	        {  
				if(!name.isEmpty() && !surname.isEmpty() && !login.isEmpty() && !email.isEmpty() && !login.isEmpty() && !pass.isEmpty() )
				{
					Class.forName("com.mysql.jdbc.Driver").newInstance();
		        	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306" ,"root", "root");
		        	PreparedStatement stm=con.prepareStatement("INSERT INTO cloud.users1 (name,surname,login,email,pass) VALUES (?,?,?,?,?)");
		        	stm.setString(1,name);
		        	stm.setString(2,surname);
		        	stm.setString(3,login);  
		        	stm.setString(4,email); 
		        	stm.setString(5,pass);
		        
		        	int i = stm.executeUpdate();  
		        	if(i>0) 
		        	{  
		    	        session.setAttribute("login", login);
		    	        session.setAttribute("name", name);
		    	        session.setAttribute("surname", surname);
		    	        session.setAttribute("email", email);
		    	        response.sendRedirect("BoardView");
		        	}
				}
				else response.sendRedirect("registration.html");
	        }
	        catch (Exception e2) 
			{ 
	        	System.out.println(e2);
	        	response.sendRedirect("registration.html");
			}
		} 
	}
}