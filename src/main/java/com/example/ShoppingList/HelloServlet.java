package com.example.ShoppingList;

import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

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
                item.setDone(done);

                itemList.add(item);
            }
            request.setAttribute("items", itemList);
            request.getRequestDispatcher("/WEB-INF/shoppinglist.jsp").forward(request, response);

        } catch (SQLException | ServletException e) {
            throw new IllegalStateException(e);
        }
    }

    public void destroy() {
    }
}