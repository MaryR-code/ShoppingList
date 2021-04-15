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

@WebServlet(name = "ArticleServlet", value = "/article")
public class ArticleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (
                Connection con = DriverManager.getConnection("jdbc:h2:file:~/ShoppingList", "sa", "");
                Statement articlesSTMT = con.createStatement();
                ResultSet articlesRS = articlesSTMT.executeQuery("SELECT a.id, a.name, u.name as units " +
                                                                "FROM articles a, units u " +
                                                                "WHERE a.unit_id = u.id");
                Statement unitsSTMT = con.createStatement();
                ResultSet unitsRS = unitsSTMT.executeQuery("SELECT u.id, u.name FROM units u")
        ) {
            List<Article> articleList = new ArrayList<>();
            while (articlesRS.next()) {
                Article article = new Article();
                article.setId(articlesRS.getInt("ID"));
                article.setName(articlesRS.getString("NAME"));
                article.setUnits(articlesRS.getString("UNITS"));
                articleList.add(article);
            }

            List<Unit> unitList = new ArrayList<>();
            while (unitsRS.next()) {
                Unit unit = new Unit();
                unit.setId(unitsRS.getInt("ID"));
                unit.setName(unitsRS.getString("NAME"));
                unitList.add(unit);
            }

            request.setAttribute("articles", articleList);
            request.setAttribute("units", unitList);
            request.getRequestDispatcher("/WEB-INF/article.jsp").forward(request, response);

        } catch (SQLException | ServletException e) {
                throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int unitId = Integer.parseInt(request.getParameter("unitId"));

        try (
                Connection con = DriverManager.getConnection("jdbc:h2:file:~/ShoppingList", "sa", "");
                PreparedStatement stmt = con.prepareStatement("INSERT INTO ARTICLES (ID, NAME, UNIT_ID) VALUES (?, ?, ?)")
        ) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setInt(3, unitId);

            stmt.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/article");

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}
