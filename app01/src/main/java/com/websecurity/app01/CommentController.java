package com.websecurity.app01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Map<String, String> request) {
        String content = request.get("content");
        String author = request.get("author");

        if (content == null || content.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (author == null || author.trim().isEmpty()) {
            author = "Anonymous";
        }

        Comment comment = new Comment(content, author);
        Comment savedComment = commentRepository.save(comment);

        return ResponseEntity.ok(savedComment);
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentRepository.findAllByOrderByCreatedAtDesc();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}