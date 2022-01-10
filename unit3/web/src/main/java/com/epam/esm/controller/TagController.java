package com.epam.esm.controller;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.api.GiftCertificateService;
import com.epam.esm.api.TagService;
import com.epam.esm.attribute.ResponseAttribute;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.response.OperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final GiftCertificateService certificateService;
    private final Hateoas<Tag> tagHateoas;
    private final Hateoas<OperationResponse> responseHateoas;

    @Autowired
    public TagController(TagService tagService, Hateoas<Tag> tagHateoas, GiftCertificateService
            certificateService, @Qualifier("tagOperationResponseHateoas") Hateoas<OperationResponse> responseHateoas) {
        this.tagService = tagService;
        this.certificateService = certificateService;
        this.tagHateoas = tagHateoas;
        this.responseHateoas = responseHateoas;
    }

    @GetMapping
    public List<Tag> findAllTags(@RequestParam int page, @RequestParam int elements) {
        List<Tag> tags = tagService.findAll(page, elements);
        tags.forEach(tagHateoas::createHateoas);
        return tags;
    }

    @GetMapping("/{id}")
    public Tag findTagById(@PathVariable String id) {
        Tag tag = tagService.findById(id);
        tagHateoas.createHateoas(tag);
        return tag;
    }

    @DeleteMapping("/{id}")
    public OperationResponse deleteTag(@PathVariable String id) {
        certificateService.disconnectTagById(id);
        tagService.delete(id);
        OperationResponse response = new OperationResponse(OperationResponse.Operation.DELETION,
                ResponseAttribute.TAG_DELETE_OPERATION, id);
        responseHateoas.createHateoas(response);
        return response;
    }

    @PostMapping("/new")
    public OperationResponse createTag(@RequestBody Tag tag) {
        OperationResponse response = new OperationResponse(OperationResponse.Operation.CREATION,
                ResponseAttribute.TAG_CREATE_OPERATION, String.valueOf(tagService.insert(tag)));
        responseHateoas.createHateoas(response);
        return response;
    }
}
