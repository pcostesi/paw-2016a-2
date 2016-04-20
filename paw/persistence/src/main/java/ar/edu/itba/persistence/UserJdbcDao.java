package ar.edu.itba.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.user.UserDao;
import ar.edu.itba.models.user.User;

@Repository
public class UserJdbcDao implements UserDao {

		private JdbcTemplate jdbcTemplate;
        private SimpleJdbcInsert jdbcInsert;
        private UserRowMapper userRowMapper;

		@Autowired
        public UserJdbcDao(final DataSource ds) {
        		userRowMapper = new UserRowMapper();
                jdbcTemplate = new JdbcTemplate(ds);
                jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("user");

                jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS user ("
                                + "username varchar(100) NOT NULL PRIMARY KEY,"
                                + "password varchar(100) NOT NULL,"
                                + "mail varchar(100) NOT NULL"
                        + ")");

        }

        @Override
        public User create(final String username, final String password, final String mail) {
        	if (username == null || username.length() == 0 || password == null || password.length() == 0 ||
        			mail == null || mail.length() == 0 ) {
        		return null;
        	}
        	
        	boolean userExists = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user WHERE username = ?", Integer.class, username) > 0;
        	
        	if (userExists) {
        		return null;
        	}
        	
            final Map<String, Object> args = new HashMap<String, Object>();
            args.put("username", username);
            args.put("password", password);
            args.put("mail", mail);
            jdbcInsert.execute(args);

            return new User(username, password, mail);
        }
        
        @Override
        public User getByUsername(final String username) {
        	if (username == null || username.length() == 0 ) {
        		return null;
        	}
            
        	final List<User> list = jdbcTemplate.query("SELECT * FROM user WHERE username = ? LIMIT 1", userRowMapper, username);
            if (list.isEmpty()) {
                    return null;
            }

            return list.get(0);
        }
        
        private static class UserRowMapper implements RowMapper<User> {

            @Override
            public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                    return new User(rs.getString("username"), rs.getString("password"), rs.getString("mail"));
            }
        }
}


