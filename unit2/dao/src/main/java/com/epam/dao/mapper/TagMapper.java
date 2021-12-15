package com.epam.dao.mapper;

import com.epam.dao.sql.SqlTagName;
import com.epam.model.bean.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(SqlTagName.TAG_ID);
        String name = rs.getString(SqlTagName.TAG_NAME);
        return new Tag(id, name);
    }
}
