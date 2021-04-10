package com.example.ShoppingList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "shoppingListServlet", value = "/list")
public class ShoppingListServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (
                Connection con = DriverManager.getConnection("jdbc:h2:file:~/ShoppingList", "sa", "");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM items ORDER BY SORT_ORDER")
        ) {
            List<Item> itemList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                BigDecimal quantity = rs.getBigDecimal("QUANTITY");
                String units = rs.getString("UNITS");
                boolean done = rs.getBoolean("DONE");

                Item item = new Item();
                item.setId(id);
                item.setName(name);
                item.setQuantity(quantity);
                item.setUnits(units);
                item.setDone(done);

                itemList.add(item);
            }

            request.setAttribute("items", itemList);
            request.getRequestDispatcher("/WEB-INF/shoppinglist.jsp").forward(request, response);
        } catch (SQLException | ServletException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        BigDecimal quantity = new BigDecimal(request.getParameter("quantity"));
        String units = request.getParameter("units");

        try (
                Connection con = DriverManager.getConnection("jdbc:h2:file:~/ShoppingList", "sa", "");
                PreparedStatement stmt =
                        con.prepareStatement("INSERT INTO ITEMS (ID, NAME, QUANTITY, UNITS) VALUES(?, ?, ?, ?)")
        ) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setBigDecimal(3, quantity);
            stmt.setString(4, units);

            stmt.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/list");

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}