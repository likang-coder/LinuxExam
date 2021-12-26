
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import com.google.gson.reflect.TypeToken;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/all")
public class Text5 extends HttpServlet {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://106.12.162.113/linux_final?useUnicode=true"
    		+ "&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    static final String USER = "root";
    static final String PASS = "Lk814824..";
    static final String SQL_BOOK_FIND = "SELECT * FROM test";
    
    static Connection conn = null;

    
    public void init() {
		try {
			Class.forName(JDBC_DRIVER);
	        conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public void destory() {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 	   response.setContentType("application/json;charset=utf8");
        response.setCharacterEncoding("utf8");
        PrintWriter out = response.getWriter();
        List<book> book = findBook();
        Gson gson = new Gson();
       String json = gson.toJson(book, new TypeToken<List<book>>() {
            }.getType());
       out.println(json);
       out.flush();
       out.close();
       

}
    
    
    private List<book> findBook() {
        List<book> bookList = new ArrayList<book>();
        
        Statement stmt = null;
        try {
            
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL_BOOK_FIND);
            
            while (rs.next()) {
                book book1 =new book();
                book1.id = rs.getInt("id");
                book1.name = rs.getString("name");
                book1.author = rs.getString("author");
                bookList.add(book1);
            }
            rs.close();
            stmt.close();
            
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return bookList;
    }
}
