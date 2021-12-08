package com.epam.model.converter;

import com.epam.model.bean.GiftCertificate;
import com.epam.model.dto.GiftCertificateDTO;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.Period;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class GiftCertificateDTOConverter implements Function<GiftCertificateDTO, GiftCertificate> {
    //TODO:add
    @Override
    public GiftCertificate apply(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate bean = new GiftCertificate();
        bean.setId(giftCertificateDTO.getId());
        bean.setName(giftCertificateDTO.getName());
        bean.setDescription(giftCertificateDTO.getDescription());
        bean.setPrice(giftCertificateDTO.getPrice());
        bean.setCreateDate(Instant.parse(giftCertificateDTO.getCreateDate()));
        bean.setDuration(Period.parse(giftCertificateDTO.getDuration()));
        bean.setLastUpdateDate(Instant.parse(giftCertificateDTO.getLastUpdateDate()));
        bean.setTags(giftCertificateDTO.getTags().stream()
                .map(tag -> new TagDTOConverter().apply(tag)).collect(Collectors.toList()));
        return bean;
    }
}
