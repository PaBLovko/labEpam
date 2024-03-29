package com.epam.esm.controller;

import com.epam.esm.GiftCertificate;
import com.epam.esm.api.GiftCertificateService;
import com.epam.esm.api.OrderService;
import com.epam.esm.attribute.ResponseAttribute;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.response.OperationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService certificateService;
    private final OrderService orderService;
    private final Hateoas<GiftCertificate> certificateHateoas;
    private final Hateoas<OperationResponse> responseHateoas;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService, OrderService
            orderService, Hateoas<GiftCertificate> certificateHateoas, @Qualifier("certificateOperationResponseHateoas")
                                             Hateoas<OperationResponse> responseHateoas) {
        this.certificateService = certificateService;
        this.orderService = orderService;
        this.certificateHateoas = certificateHateoas;
        this.responseHateoas = responseHateoas;
    }

    @GetMapping("/all")
    public List<GiftCertificate> findAllGiftCertificates(@RequestParam int page, @RequestParam int elements) {
        List<GiftCertificate> giftCertificates = certificateService.findAll(page, elements);
        giftCertificates.forEach(certificateHateoas::createHateoas);
        return giftCertificates;
    }

    @GetMapping
    public List<GiftCertificate> findCertificatesWithTags(@RequestParam int page, @RequestParam int elements,
                                                          @RequestParam(required = false) List<String> tagsNames,
                                                          @RequestParam(required = false) String certificateName,
                                                          @RequestParam(required = false) String certificateDescription,
                                                          @RequestParam(required = false) String sortByName,
                                                          @RequestParam(required = false) String sortByDate) {
        List<GiftCertificate> giftCertificates = certificateService.findCertificatesWithTagsByCriteria(page, elements, tagsNames,
                certificateName, certificateDescription, sortByName, sortByDate);
        giftCertificates.forEach(certificateHateoas::createHateoas);
        return giftCertificates;
    }

    @PostMapping("/new")
    public OperationResponse createGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        OperationResponse response = new OperationResponse(OperationResponse.Operation.CREATION,
                ResponseAttribute.CERTIFICATE_CREATE_OPERATION, String.valueOf(certificateService.insert(giftCertificate)));
        responseHateoas.createHateoas(response);
        return response;
    }

    @GetMapping("/{id}")
    public GiftCertificate findCertificateById(@PathVariable String id) {
        GiftCertificate giftCertificate = certificateService.findById(id);
        certificateHateoas.createHateoas(giftCertificate);
        return giftCertificate;
    }

    @DeleteMapping("/{id}")
    public OperationResponse deleteGiftCertificate(@PathVariable String id) {
        orderService.deleteByCertificateId(id);
        certificateService.delete(id);
        OperationResponse response = new OperationResponse(OperationResponse.Operation.DELETION,
                ResponseAttribute.CERTIFICATE_DELETE_OPERATION, id);
        responseHateoas.createHateoas(response);
        return response;
    }

    @PatchMapping("/{id}")
    public OperationResponse updateGiftCertificate(@PathVariable String id,
                                                   @RequestBody GiftCertificate giftCertificate) {
        certificateService.update(id, giftCertificate);
        OperationResponse response = new OperationResponse(OperationResponse.Operation.UPDATE,
                ResponseAttribute.CERTIFICATE_UPDATE_OPERATION, id);
        responseHateoas.createHateoas(response);
        return response;
    }
}
