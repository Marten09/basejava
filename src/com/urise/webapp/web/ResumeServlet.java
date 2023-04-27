package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + "!");
        response.getWriter().write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "  border:1px solid black;\n" +
                "}\n" +
                "</style>\n" +
                "<body>\n" +
                "\n" +
                "<h2>resume table</h2>\n" +
                "\n" +
                "<table style=\"width:100%\">\n" +
                "  <tr>\n" +
                "    <td>uuid</td>\n" +
                "    <td>full_name</td>\n" +
                "  </tr>\n");
//                "  <tr>\n" +
//                "    <td>75781ce7-8eae-46ac-a736-3fac40e9ecd7</td>\n" +
//                "    <td>Name1</td>\n" +
//                "  </tr>\n" +
//                "</table>\n")
        for (Resume resume : storage.getAllSorted()) {
            response.getWriter().write("<tr>\n" +
                    "        <td>" + resume.getUuid() + "</td>\n" +
                    "        <td>" + resume.getFullName() + "</td>\n" +
                    "    </tr>\n");
        }
        response.getWriter().write("</section>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>\n");
        response.getWriter().close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
