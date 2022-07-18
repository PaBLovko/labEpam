package com.epam.esm.controller;

import com.epam.esm.GiftCertificate;
import com.epam.esm.Tag;
import com.epam.esm.api.GiftCertificateService;
import com.epam.esm.api.TagService;
import com.epam.esm.constant.HeaderName;
import com.epam.esm.constant.ResponseMessageName;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.response.EntityOperationResponse;
import com.epam.esm.util.MessageLocale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final GiftCertificateService certificateService;
    private final Hateoas<Tag> tagHateoas;
    private final Hateoas<EntityOperationResponse> responseHateoas;

    @Autowired
    public TagController(TagService tagService, Hateoas<Tag> tagHateoas, GiftCertificateService
            certificateService, @Qualifier("tagOperationResponseHateoas") Hateoas<EntityOperationResponse> responseHateoas) {
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
    public EntityOperationResponse deleteTag(HttpServletRequest request, @PathVariable String id) {
        Tag tag = tagService.findById(id);
        List<GiftCertificate> certificatesWithCurrentTag = certificateService.findCertificatesWithTagsByCriteria(
                0, 0, Collections.singletonList(tag.getName()),
                null, null, null, null);
        tagService.delete(id, !certificatesWithCurrentTag.isEmpty());
        EntityOperationResponse response = new EntityOperationResponse(EntityOperationResponse.Operation.DELETION,
                ResponseMessageName.TAG_DELETE_OPERATION, Long.parseLong(id), MessageLocale.defineLocale(
                request.getHeader(HeaderName.LOCALE)));
        responseHateoas.createHateoas(response);
        return response;
    }

    @PostMapping("/new")
    public EntityOperationResponse createTag(HttpServletRequest request, @RequestBody Tag tag) {
        EntityOperationResponse response = new EntityOperationResponse(EntityOperationResponse.Operation.CREATION,
                ResponseMessageName.TAG_CREATE_OPERATION, tagService.insert(tag), MessageLocale.defineLocale(
                request.getHeader(HeaderName.LOCALE)));
        responseHateoas.createHateoas(response);
        return response;
    }
}
