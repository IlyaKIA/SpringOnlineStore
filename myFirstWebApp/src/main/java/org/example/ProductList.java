package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ProductList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(i,new Product(i, "Fruit" + (i+1), (i+1)*20 ));
        }
        resp.getWriter().printf("<html><body><h1>My product list:</h1>");
        for (int i = 0; i < 10; i++) {
            resp.getWriter().printf("<p>" + products.get(i) +"</p>");
        }
        resp.getWriter().printf("</body></html>");
    }
}
