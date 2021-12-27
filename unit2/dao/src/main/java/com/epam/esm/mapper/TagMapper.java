package com.epam.esm.mapper;

import com.epam.esm.sql.SqlTagName;
import com.epam.esm.Tag;
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
