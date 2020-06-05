import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/BoardView")
public class BoardView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		
		if(session!=null)
		{  
			String parameter = request.getParameter("action");
			if ( parameter == null || parameter.equals("") || parameter.equals("board") )
			{
				String filename = "/board.html";
				ServletContext context = getServletContext();
		        InputStream is = context.getResourceAsStream(filename);
		        if (is != null) 
		        {
		            InputStreamReader isr = new InputStreamReader(is);
		            BufferedReader reader = new BufferedReader(isr);
		            String text;
		            while ((text = reader.readLine()) != null) 
		            {
		            	if ( text.contains("[[") && text.contains("]]") ) 
		            	{
		            		text = text.substring(5, text.length()-2);
		            		if ( text.equals("LOGIN") ) 
		            		{
		            			if(session!=null)
		            			{  
		            				String login=(String)session.getAttribute("login"); 
		            				out.println(login);
		            			}
		            		}
		            	} 
		            	else out.println(text);
		            }
		        } 
			}
			else if(parameter.equals("profile"))
	        {
	        	response.sendRedirect("ProfileView");
	        }
			else if(parameter.equals("log out"))
	        {
	        	session.invalidate();
	        	response.sendRedirect("login.html");
	        }
		}
		else response.sendRedirect("login.html");
	}
}
