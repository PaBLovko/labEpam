package com.epam.unit2.web.controller;

import com.epam.unit2.model.bean.Tag;
import com.epam.unit2.service.api.TagService;
import com.epam.unit2.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService<Tag> service;

    @Autowired
    public TagController(TagService<Tag> service) {
        this.service = service;
    }

    @GetMapping
    public List<Tag> findAllTags() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Tag findTagById(@PathVariable String id) throws ServiceException {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable String id) throws ServiceException {
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