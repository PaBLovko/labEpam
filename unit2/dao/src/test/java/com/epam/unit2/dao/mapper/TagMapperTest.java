package com.epam.unit2.dao.mapper;

import com.epam.unit2.dao.config.DataSourceConfig;
import com.epam.unit2.dao.sql.SqlTagQuery;
import com.epam.unit2.model.bean.Tag;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TagMapperTest {
    @Test
    void mapRowTest() throws SQLException {
        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag(1,"#funny"));
        expected.add(new Tag(2,"#cool"));
        expected.add(new Tag(3,"#warm"));
        expected.add(new Tag(4,"#cold"));
        expected.add(new Tag(5,"#relax"));

        Statement statement = DataSourceConfig.dataSource.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(SqlTagQuery.SQL_SELECT_ALL_TAGS);

        List<Tag> actual = new ArrayList<>();
        while (rs.next()) {
            actual.add(new TagMapper().mapRow(rs, rs.getRow()));
        }
        assertEquals(expected, actual);
    }
}
