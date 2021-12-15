package com.epam.dao.mapper;


import com.epam.dao.sql.SqlGiftCertificateName;
import com.epam.dao.sql.SqlTagName;
import com.epam.model.bean.GiftCertificate;
import com.epam.model.bean.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateMapper implements ResultSetExtractor<List<GiftCertificate>> {
    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        if (rs.next()) {
            while (!rs.isAfterLast()) {
                long giftCertificateId = rs.getLong(SqlGiftCertificateName.GIFT_CERTIFICATE_ID);
                String giftCertificateName = rs.getString(SqlGiftCertificateName.NAME);
                String description = rs.getString(SqlGiftCertificateName.DESCRIPTION);
                BigDecimal price = rs.getBigDecimal(SqlGiftCertificateName.PRICE);
                int duration = rs.getInt(SqlGiftCertificateName.DURATION);
                LocalDateTime createDate = rs.getObject(SqlGiftCertificateName.CREATE_DATE, LocalDateTime.class);
                LocalDateTime lastUpdateDate = rs.getObject(SqlGiftCertificateName.LAST_UPDATE_DATE, LocalDateTime.class);

                List<Tag> tags = new ArrayList<>();
                do {
                    long tagId = rs.getLong(SqlTagName.TAG_ID);
                    String tagName = rs.getString(SqlTagName.TAG_NAME);
                    if (tagId != 0 && tagName != null) {
                        tags.add(new Tag(tagId, tagName));
                    }
                } while (rs.next() && rs.getLong(SqlGiftCertificateName.GIFT_CERTIFICATE_ID) == giftCertificateId);
                giftCertificates.add(new GiftCertificate(giftCertificateId, giftCertificateName, description, price,
                        duration, createDate, lastUpdateDate, tags));
            }
        }
        return giftCertificates;
    }
}
