package com.dzhenetl.controller;

import com.dzhenetl.model.Post;
import com.dzhenetl.service.PostService;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class PostController {

    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final List<Post> data = service.all();
        final Gson gson = new Gson();
        response.getWriter().println(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final Gson gson = new Gson();
        Post post = service.getById(id);
        response.getWriter().println(gson.toJson(post));
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final Gson gson = new Gson();
        final Post post = gson.fromJson(body, Post.class);
        final Post data = service.save(post);
        response.getWriter().println(gson.toJson(data));
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        service.removeById(id);
        response.getWriter().println("OK");
    }
}
