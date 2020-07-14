package com.spring.redditclone.repository;

import com.spring.redditclone.model.Comment;
import com.spring.redditclone.model.Post;
import com.spring.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
