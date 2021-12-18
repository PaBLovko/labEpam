package com.epam.unit2.web.controller;

import com.epam.unit2.model.bean.GiftCertificate;
import com.epam.unit2.service.api.GiftCertificateService;
import com.epam.unit2.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService<GiftCertificate> service;

    @Autowired
    public GiftCertificateController(GiftCertificateService<GiftCertificate> service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<GiftCertificate> findAllGiftCertificates() {
        return service.findAll();
    }

    @GetMapping
    public List<GiftCertificate> findCertificatesWithTags(@RequestParam(required = false) String tagName,
                                                          @RequestParam(required = false) String certificateName,
                                                          @RequestParam(required = false) String certificateDescription,
                                                          @RequestParam(required = false) String sortByName,
                                                          @RequestParam(required = false) String sortByDate) {
        return service.findCertificatesWithTagsByCriteria(tagName, certificateName, certificateDescription, sortByName,
                sortByDate);
    }

    @PostMapping("/new")
    public ResponseEntity<String> createGiftCertificate(@RequestBody GiftCertificate giftCertificate) throws ServiceException {
        service.insert(giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Certificate created successfully");
    }

    @GetMapping("/{id}")
    public GiftCertificate findCertificateById(@PathVariable String id) throws ServiceException {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGiftCertificate(@PathVariable String id) throws ServiceException {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Gift certificate deleted successfully" +
                " (id = " + id + ")");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateGiftCertificate(@PathVariable String id,
                                                        @RequestBody GiftCertificate giftCertificate) throws ServiceException {
        service.update(id, giftCertificate);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Gift certificate updated successfully" +
                " (id = " + id + ")");
    }
}
