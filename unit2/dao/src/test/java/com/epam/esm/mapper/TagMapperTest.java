package com.epam.esm.mapper;

import com.epam.esm.config.DataSourceConfig;
import com.epam.esm.sql.SqlTagQuery;
import com.epam.esm.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
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
