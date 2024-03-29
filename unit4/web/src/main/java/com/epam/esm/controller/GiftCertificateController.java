package com.epam.esm.controller;

import com.epam.esm.GiftCertificate;
import com.epam.esm.api.GiftCertificateService;
import com.epam.esm.api.OrderService;
import com.epam.esm.constant.HeaderName;
import com.epam.esm.constant.ResponseMessageName;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.response.EntityOperationResponse;
import com.epam.esm.util.MessageLocale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService certificateService;
    private final OrderService orderService;
    private final Hateoas<GiftCertificate> certificateHateoas;
    private final Hateoas<EntityOperationResponse> responseHateoas;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService, OrderService
            orderService, Hateoas<GiftCertificate> certificateHateoas, @Qualifier("certificateOperationResponseHateoas")
                                             Hateoas<EntityOperationResponse> responseHateoas) {
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
        List<GiftCertificate> giftCertificates = certificateService.findCertificatesWithTagsByCriteria(page, elements,
                tagsNames, certificateName, certificateDescription, sortByName, sortByDate);
        giftCertificates.forEach(certificateHateoas::createHateoas);
        return giftCertificates;
    }

    @PostMapping("/new")
    public EntityOperationResponse createGiftCertificate(HttpServletRequest request,
                                                         @RequestBody GiftCertificate giftCertificate) {
        EntityOperationResponse response = new EntityOperationResponse(EntityOperationResponse.Operation.CREATION,
                ResponseMessageName.CERTIFICATE_CREATE_OPERATION, certificateService.insert(giftCertificate),
                MessageLocale.defineLocale(request.getHeader(HeaderName.LOCALE)));
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
    public EntityOperationResponse deleteGiftCertificate(HttpServletRequest request, @PathVariable String id) {
        certificateService.delete(id, orderService.findWithCurrentCertificate(id));
        EntityOperationResponse response = new EntityOperationResponse(EntityOperationResponse.Operation.DELETION,
                ResponseMessageName.CERTIFICATE_DELETE_OPERATION, Long.parseLong(id), MessageLocale.defineLocale(
                request.getHeader(HeaderName.LOCALE)));
        responseHateoas.createHateoas(response);
        return response;
    }

    @PatchMapping("/{id}")
    public EntityOperationResponse updateGiftCertificate(HttpServletRequest request, @PathVariable String id,
                                                         @RequestBody GiftCertificate giftCertificate) {
        certificateService.update(id, giftCertificate);
        EntityOperationResponse response = new EntityOperationResponse(EntityOperationResponse.Operation.UPDATE,
                ResponseMessageName.CERTIFICATE_UPDATE_OPERATION, Long.parseLong(id), MessageLocale.defineLocale(
                request.getHeader(HeaderName.LOCALE)));
        responseHateoas.createHateoas(response);
        return response;
    }
}
