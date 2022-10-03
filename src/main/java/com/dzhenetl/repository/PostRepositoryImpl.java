package com.dzhenetl.repository;

import com.dzhenetl.exception.NotFoundException;
import com.dzhenetl.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final List<Post> repository;
    private volatile int postCounter;

    public PostRepositoryImpl() {
        this.repository = new ArrayList<>();
        postCounter = 0;
    }

    public List<Post> all() {
        return repository;
    }

    public Optional<Post> getById(long id) {
        return repository.stream().filter(p -> p.getId() == id).findFirst();
    }

    public synchronized Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(++postCounter);
            repository.add(post);
        } else {
            Post postToUpdate = this.getById(post.getId()).orElseThrow(NotFoundException::new);
            postToUpdate.setContent(post.getContent());
        }
        return post;
    }

    public synchronized void removeById(long id) {
        repository.removeIf(p -> p.getId() == id);
    }
}
