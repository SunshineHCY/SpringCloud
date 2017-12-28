package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 测试接口
 */
@WebServlet("/myController")
public class testMain extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.err.println("---");
        //解决乱码
        req.setCharacterEncoding("UTF-8");
        String name=req.getParameter("name");
        req.setAttribute("name",name);
        System.out.println(name);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

}
