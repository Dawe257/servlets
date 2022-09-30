package com.dzhenetl.servlet;

import com.dzhenetl.controller.PostController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private PostController controller;

    private final String GET = "GET";
    private final String POST = "POST";
    private final String DELETE = "DELETE";

    @Override
    public void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.dzhenetl");
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final String path = req.getRequestURI();
            final String method = req.getMethod();

            if (path.equals("/api/posts")) {
                switch (method) {
                    case GET:
                        controller.all(resp);
                        return;
                    case POST:
                        controller.save(req.getReader(), resp);
                        return;
                }
            }

            if (path.matches("/api/posts/\\d+")) {
                final long id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                switch (method) {
                    case GET:
                        controller.getById(id, resp);
                        return;
                    case DELETE:
                        controller.removeById(id, resp);
                        return;
                }
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
