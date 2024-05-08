package io.seoLeir.servlet;

import io.seoLeir.util.ShuntingYard;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static io.seoLeir.util.ShuntingYard.*;

@WebServlet("/calc")
public class CalcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        String expression = req.getParameter("expression");

        Integer result = calculate(expression).intValue();
        writer.write(result);
    }
}
