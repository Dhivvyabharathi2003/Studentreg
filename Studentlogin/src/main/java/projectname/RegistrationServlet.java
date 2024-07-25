package projectname;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vsb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Dhivvya@20";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Prepare SQL query
            String sql = "INSERT INTO studentlogin (fullname, email, username, pass) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fullname);
            pstmt.setString(2, email);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            
            // Execute query
            int rowsAffected = pstmt.executeUpdate();
            
            // Check if registration was successful
            if (rowsAffected > 0) {
                // Redirect to login page after successful registration
                response.sendRedirect("login.html");
            } else {
                // Registration failed, redirect to an error page or handle appropriately
                response.sendRedirect("error.html");
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // Handle database errors or class not found exception
            response.sendRedirect("error.html"); // Redirect to an error page
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
