package ar.edu.itba.persistence.rowmapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ar.edu.itba.models.PersistableUser;
import ar.edu.itba.models.User;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return PersistableUser.builder()
            		.username(rs.getString("username"))
            		.password(rs.getString("password"))
            		.mail(rs.getString("mail"))
            		.build();
    }
}
