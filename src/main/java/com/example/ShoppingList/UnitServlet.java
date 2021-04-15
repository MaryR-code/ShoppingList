package com.example.ShoppingList;

import com.example.ShoppingList.model.Article;
import com.example.ShoppingList.model.Unit;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UnitServlet", value = "/unit")
public class UnitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (
                Connection con = DriverManager.getConnection("jdbc:h2:file:~/ShoppingList", "sa", "");
                Statement unitsSTMT = con.createStatement();
                ResultSet unitsRS = unitsSTMT.executeQuery("SELECT u.id, u.name FROM units u")
        ) {
            List<Unit> unitList = new ArrayList<>();
            addUnit(unitsRS, unitList);

            request.setAttribute("units", unitList);
            request.getRequestDispatcher("/WEB-INF/unit.jsp").forward(request, response);

        } catch (SQLException | ServletException e) {
            throw new IllegalStateException(e);
        }
    }

    static void addUnit(ResultSet unitsRS, List<Unit> unitList) throws SQLException {
        while (unitsRS.next()) {
            Unit unit = new Unit();
            unit.setId(unitsRS.getInt("ID"));
            unit.setName(unitsRS.getString("NAME"));
            unitList.add(unit);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        try (
                Connection con = DriverManager.getConnection("jdbc:h2:file:~/ShoppingList", "sa", "");
                PreparedStatement stmt = con.prepareStatement("INSERT INTO UNITS (ID, NAME) VALUES (?, ?)")
        ) {
            stmt.setInt(1, id);
            stmt.setString(2, name);

            stmt.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/unit");

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}
