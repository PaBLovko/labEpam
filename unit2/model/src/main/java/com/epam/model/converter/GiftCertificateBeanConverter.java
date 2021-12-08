package com.epam.model.converter;

import com.epam.model.bean.GiftCertificate;
import com.epam.model.dto.GiftCertificateDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GiftCertificateBeanConverter implements Function<GiftCertificate, GiftCertificateDTO> {
    @Override
    public GiftCertificateDTO apply(GiftCertificate giftCertificate) {
        GiftCertificateDTO dto = new GiftCertificateDTO();
        dto.setId(giftCertificate.getId());
        dto.setName(giftCertificate.getName());
        dto.setDescription(giftCertificate.getDescription());
        dto.setPrice(giftCertificate.getPrice());
        dto.setCreateDate(giftCertificate.getCreateDate().toString());
        dto.setDuration(giftCertificate.getDuration().toString());
        dto.setLastUpdateDate(giftCertificate.getLastUpdateDate().toString());
        dto.setTags(giftCertificate.getTags().stream()
                .map(tag -> new TagBeanConverter().apply(tag)).collect(Collectors.toList()));
        return dto;
    }
}
