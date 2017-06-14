package demo.servlet;

import demo.model.Student;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 高伟冬 on 2017/6/12.
 * javaEE
 * 14:07
 * 星期一
 */
@WebServlet(urlPatterns = "/student")
public class StudentAction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            add(req, resp);
            return;
        }
        if ("queryALL".equals(action)) {
            queryAll(req, resp);
            return;
        }
        if ("queryId".equals(action)) {
            queryId(req, resp);
            return;
        }
        if ("modify".equals(action)) {
            modify(req, resp);
            return;
        }
        if ("remove".equals(action)) {
            remove(req, resp);
            return;
        }
        req.setAttribute("message", "出现一点问题。。");
        req.getRequestDispatcher("default.jsp").forward(req, resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String dob = req.getParameter("dob");

        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO db_javaee.student VALUE(null,?,?,?)";
        try {
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
            } else {
                req.setAttribute("message", "Error...");
                req.getRequestDispatcher("index.jap").forward(req, resp);
                return;
            }
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gender);
            preparedStatement.setString(3, dob);

            preparedStatement.executeUpdate();

            resp.sendRedirect("student?action=queryAll");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Db.close(null, preparedStatement, connection);
        }
    }
    private void queryAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "SELECT * FROM db_javaee.student ORDER BY id;";

        try {
        if (connection != null) {
            preparedStatement=connection.prepareStatement(sql);
        }else {
            req.setAttribute("message","Error/...");
            req.getRequestDispatcher("index.jsp").forward(req,resp);
            return;
        }
            resultSet = preparedStatement.executeQuery();
        List<Student> students = new ArrayList<>();
        while (resultSet.next()) {
            Student student = new Student(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("gender"),
                    resultSet.getString("dob")
            );
            students.add(student);
        }
        req.getSession().setAttribute("students",students);
        resp.sendRedirect("index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            Db.close(resultSet,preparedStatement,connection);
        }
    }

    private void queryId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "SELECT * FROM db_javaee.student ORDER BY id = ?;";
        try {
        if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
        }else {
            req.setAttribute("message", "Error...");
            req.getRequestDispatcher("index.jsp").forward(req,resp);
            return;
        }
        preparedStatement.setInt(1,id);
        resultSet =preparedStatement.executeQuery();
        resultSet.next();
        Student student = new Student(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("gender"),
                resultSet.getString("dob")
        );
        req.getSession().setAttribute("student",student);
        resp.sendRedirect("edit.jsp");
        } catch(SQLException e){
            e.printStackTrace();
        }finally {
            Db.close(resultSet,preparedStatement,connection);
        }
    }

    private void modify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String dob = req.getParameter("dob");

        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;

        String sql = "UPDATE db_javaee.student SET name = ?, gender = ?, dob = ? WHERE id = ?";

        try {
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
            } else {
                req.setAttribute("message", "Error.");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                return;
            }
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gender);
            preparedStatement.setString(3, dob);
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

            resp.sendRedirect("student?action=queryAll");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Db.close(null, preparedStatement, connection);
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        Connection connection = Db.getConnection();
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM db_javaee.student WHERE id = ?";

        try {
            if (connection != null) {
                preparedStatement = connection.prepareStatement(sql);
            } else {
                req.setAttribute("message", "Error.");
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                return;
            }
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            resp.sendRedirect("student?action=queryAll");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Db.close(null, preparedStatement, connection); // ?
        }
    }
        @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
