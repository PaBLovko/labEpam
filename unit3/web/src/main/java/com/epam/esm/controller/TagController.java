package com.epam.esm.controller;

import com.epam.esm.Tag;
import com.epam.esm.api.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tag> findAllTags() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Tag findTagById(@PathVariable String id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Tag deleted successfully" +
                " (id = " + id + ")");
    }

    @PostMapping("/new")
    public ResponseEntity<String> createTag(@RequestBody Tag tag) {
        service.insert(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tag created" +
                " successfully");
    }
}
