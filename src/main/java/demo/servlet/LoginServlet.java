package demo.servlet;

import demo.util.Db;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 高伟冬 on 2017/6/9.
 * javaEE
 * 11:59
 * 星期五
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mobile = req.getParameter("mobile");
        String password = req.getParameter("password");

        Connection connection = Db.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM db_javaee.user WHERE mobile=? AND password=?";
            if (connection != null) {
                statement = connection.prepareStatement(sql);
            } else {
                return;
            }
            statement.setString(1, mobile);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                req.getSession().setAttribute("nick", resultSet.getString("nick"));
                req.getRequestDispatcher("home.jsp").forward(req, resp);
            } else {
                req.setAttribute("message", "用户名或密码错误");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Db.close(resultSet, statement, connection);
        }
    }
}