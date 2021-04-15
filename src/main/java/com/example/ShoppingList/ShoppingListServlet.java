package com.example.ShoppingList;

import com.example.ShoppingList.model.Article;
import com.example.ShoppingList.model.Item;

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

@WebServlet(name = "ShoppingListServlet", value = "/list")
public class ShoppingListServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (
                Connection con = DriverManager.getConnection("jdbc:h2:file:~/ShoppingList", "sa", "");
                Statement itemsSTMT = con.createStatement();
                ResultSet itemsRS = itemsSTMT.executeQuery("SELECT i.id, a.name, i.quantity, u.name as units, i.done " +
                                                            "FROM items i, articles a, units u " +
                                                            "WHERE i.article_id = a.id AND u.id = a.unit_id " +
                                                            "ORDER BY SORT_ORDER");
                Statement articlesSTMT = con.createStatement();
                ResultSet articlesRS = articlesSTMT.executeQuery("SELECT a.id, a.name, u.name as units " +
                                                                "FROM articles a, units u " +
                                                                "WHERE a.unit_id = u.id")
        ) {
            List<Article> articleList = new ArrayList<>();
            while (articlesRS.next()) {
                Article article = new Article();
                article.setId(articlesRS.getInt("ID"));
                article.setName(articlesRS.getString("NAME"));
                article.setUnits(articlesRS.getString("UNITS"));
                articleList.add(article);
            }

            List<Item> itemList = new ArrayList<>();
            while (itemsRS.next()) {//
                Item item = new Item();//
                item.setId(itemsRS.getInt("ID"));
                item.setName(itemsRS.getString("NAME"));
                item.setQuantity(itemsRS.getBigDecimal("QUANTITY"));
                item.setUnits(itemsRS.getString("UNITS"));
                item.setDone(itemsRS.getBoolean("DONE"));
                itemList.add(item);
            }

            request.setAttribute("items", itemList);
            request.setAttribute("articles", articleList);
            request.getRequestDispatcher("/WEB-INF/shoppinglist.jsp").forward(request, response);

        } catch (SQLException | ServletException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        BigDecimal quantity = new BigDecimal(request.getParameter("quantity"));
        int articleId = Integer.parseInt(request.getParameter("articleId"));

        try (
                Connection con = DriverManager.getConnection("jdbc:h2:file:~/ShoppingList", "sa", "");
                PreparedStatement stmt =
                        con.prepareStatement("INSERT INTO ITEMS (ID, QUANTITY, ARTICLE_ID) VALUES(?, ?, ?)")
        ) {
            stmt.setInt(1, id);
            stmt.setBigDecimal(2, quantity);
            stmt.setInt(3, articleId);

            stmt.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/list");

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}