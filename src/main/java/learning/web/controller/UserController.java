package learning.web.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        doHello(response, "");

    }

    private void doHello(HttpServletResponse response, String hello) throws IOException {
        response.setContentType("test/html");

        System.out.print("into servlet .");
        PrintWriter out = response.getWriter();
        out.println("This is User Servl");


        out.println();
    }


}
