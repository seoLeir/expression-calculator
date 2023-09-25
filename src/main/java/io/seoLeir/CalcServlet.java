package io.seoLeir;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.seoLeir.ShuntingYard.*;

@WebServlet("/calc")
public class CalcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String expression = req.getParameter("expression");
        char[] chars = expression.toCharArray();
        List<String> expressionList = new ArrayList<>();
        for (char aChar : chars) {
            expressionList.add(String.valueOf(aChar));
        }
        List<String> computed = shuntingYard(expressionList);
        System.out.println("Computed: " + computed);
        int i = evalRPN(computed.toArray(new String[0]));
        resp.getWriter().write(i);
    }
}
