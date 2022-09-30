package com.dzhenetl.repository;

import com.dzhenetl.exception.NotFoundException;
import com.dzhenetl.model.Post;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class PostRepository {

    private List<Post> repository;

    public PostRepository() {
        this.repository = new CopyOnWriteArrayList<>();
    }

    public List<Post> all() {
        return repository;
    }

    public Optional<Post> getById(long id) {
        return repository.stream().filter(p -> p.getId() == id).findFirst();
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(repository.size());
            repository.add(post);
        } else {
            Post postToUpdate;
            try {
                postToUpdate = repository.get((int) post.getId());
            } catch (IndexOutOfBoundsException e) {
                throw new NotFoundException();
            }
            postToUpdate.setContent(post.getContent());
        }
        return post;
    }

    public void removeById(long id) {
        repository.removeIf(p -> p.getId() == id);
    }
}
