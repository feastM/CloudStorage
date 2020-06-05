import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ProfileView")
@MultipartConfig(maxFileSize = 16177215) 
public class ProfileView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		OutputStream os = null;

		if (session != null) {
			String parameter = request.getParameter("action");

			 if (parameter == null || parameter.equals("") || parameter.equals("profile")) {
				String filename = "/MyProfile.html";
				ServletContext context = getServletContext();
				InputStream is = context.getResourceAsStream(filename);
				if (is != null) {
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader reader = new BufferedReader(isr);
					String text;
					while ((text = reader.readLine()) != null) {
						if (text.contains("[[") && text.contains("]]")) {
							text = text.substring(5, text.length() - 2);
							if (text.equals("NAME")) {
								if (session != null) {
									String name = (String) session.getAttribute("name");
									out.print(name);
								}
							}
							if (text.equals("SURNAME")) {
								if (session != null) {
									String surname = (String) session.getAttribute("surname");
									out.print(surname);
								}
							}
							if (text.equals("EMAIL")) {
								if (session != null) {
									String email = (String) session.getAttribute("email");
									out.print(email);
								}
							}
							if (text.equals("LOGIN")) {
								if (session != null) {
									String login = (String) session.getAttribute("login");
									out.print(login);
								}
							}
							if (text.equals("PASS")) {
								if (session != null) {
									String pass = (String) session.getAttribute("pass");
									out.print(pass);
								}
							}
						} else out.println(text);
					}
				} else if (parameter.equals("board")) {
					response.sendRedirect("BoardView");
				} else if (parameter.equals("log out")) {
					session.invalidate();
					response.sendRedirect("login.html");
				}

			} else if(parameter.equals("download"))
			{

				try
				{
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306" ,"root", "root");
					Statement stm = null;
					String id=(String)session.getAttribute("id");
					String sql = "SELECT action FROM cloud.users1 WHERE id='" + id + "'";

					stm = con.createStatement();
					ResultSet rs = stm.executeQuery(sql);
					rs = stm.executeQuery(sql);
					while (rs.next()) {
						String filename = "Me";
						Blob ufile = rs.getBlob("action");
						File file1 = new File("D:\\DownloadFile\\" + filename + ".png");
						FileOutputStream foStream = new FileOutputStream(file1);
						if( ufile != null){
							int length = (int) ufile.length();
							InputStream is2 = ufile.getBinaryStream();
							int b = 0;
							while(b!=-1){
								b=is2.read();
								foStream.write(b);
							}}}
				}
				catch (Exception e2)
				{
					System.out.println(e2);
					response.sendRedirect("board.html");
				}


			}else if (parameter.equals("board")) {
				 response.sendRedirect("BoardView");
			 } else if (parameter.equals("log out")) {
				 session.invalidate();
				 response.sendRedirect("login.html");
			 }
			 else{
				response.sendRedirect("login.html");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		
		if(session!=null)
		{  
			String parameter = request.getParameter("action");
		
			if(parameter.equals("upload"))
	        {
				InputStream inputStream = null; // input stream of the upload file
		        Part filePart = request.getPart("upload");
		        if (filePart != null)  inputStream = filePart.getInputStream();
		        try 
		        {
		        	Class.forName("com.mysql.jdbc.Driver").newInstance();
		        	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306" ,"root", "root");
		        	Statement stm = null; 
		        	String login=(String)session.getAttribute("login"); 
		        	String sql = "SELECT id FROM cloud.users1 WHERE login='" + login + "'";
		        	 
		        	stm = con.createStatement();
		        	ResultSet rs = stm.executeQuery(sql);
                    rs.next();
                    int id = rs.getInt("id");
                   
		            PreparedStatement preStm = con.prepareStatement("update cloud.users1 set action=(?) where id=" + id);
		            if (inputStream != null) preStm.setBlob(1, inputStream);
		            int i = preStm.executeUpdate();
		            if (i > 0) 
		            {
		            	 out.println("File uploaded and saved into database");
		            }
		        } 
		        catch (Exception ex) 
		        {
		        	System.out.println(ex);
		        	out.println("File not uploaded");
		        }
	        
	        }
			if(parameter.equals("delete"))
			{
				InputStream inputStream = null;
				Part filePart = request.getPart("delete");
				if (filePart != null)  inputStream = filePart.getInputStream();
				try
				{
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306" ,"root", "root");
					Statement stm = null;
					String login=(String)session.getAttribute("login");
					String sql = "SELECT id FROM cloud.users1 WHERE login='" + login + "'";

					stm = con.createStatement();
					ResultSet rs = stm.executeQuery(sql);
					rs.next();
					int id = rs.getInt("id");

					PreparedStatement preStm = con.prepareStatement("update cloud.users1 set action=NULL where id=" + id);
					if (inputStream != null) preStm.setBlob(1, inputStream);
					 preStm.executeUpdate();

						out.println("File was sucesfully deleted ");


				}
				catch (Exception ex)
				{
					System.out.println(ex);
					out.println("File not uploaded");
				}

			}
		}
	}
}
