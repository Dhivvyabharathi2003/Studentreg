package projectname;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vsb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Dhivvya@20";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Prepare SQL query
            String sql = "SELECT * FROM studentlogin WHERE username = ? AND pass = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            // Execute query
            rs = pstmt.executeQuery();
            
            // Check if user exists
            if (rs.next()) {
                // User authenticated, set session attributes
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("fullname", rs.getString("fullname")); // Example: Assuming fullname is a column in studentlogin
                
                // Redirect to dashboard page
                response.sendRedirect("dashboard.jsp");
            } else {
                // Invalid credentials, redirect back to login page
                response.sendRedirect("login.html");
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Handle database errors or class not found exception
            response.sendRedirect("error.html"); // Redirect to an error page
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
