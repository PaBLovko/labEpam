package com.epam.unit2.model.converter;

import com.epam.unit2.model.bean.GiftCertificate;
import com.epam.unit2.model.dto.GiftCertificateDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
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
        bean.setPrice(new BigDecimal(giftCertificateDTO.getPrice()));
        bean.setCreateDate(LocalDateTime.parse(giftCertificateDTO.getCreateDate()));
        bean.setDuration(giftCertificateDTO.getDuration());
        bean.setLastUpdateDate(LocalDateTime.parse(giftCertificateDTO.getLastUpdateDate()));
        bean.setTags(giftCertificateDTO.getTags().stream()
                .map(tag -> new TagDTOConverter().apply(tag)).collect(Collectors.toList()));
        return bean;
    }
}
