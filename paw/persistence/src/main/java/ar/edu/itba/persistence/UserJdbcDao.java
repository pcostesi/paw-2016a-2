package ar.edu.itba.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.models.User;
import ar.edu.itba.persistence.rowmapping.UserRowMapper;

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
                                + "username varchar(100) NOT NULL,"
                                + "password varchar(100) NOT NULL,"
                                + "mail varchar(100) NOT NULL,"
                                + "PRIMARY KEY ( username ),"
                                + "UNIQUE ( mail )"
                        + ")");

        }

        @Override
        public User createUser(final String username, final String password, final String mail){
        	
            final Map<String, Object> args = new HashMap<String, Object>();
            args.put("username", username);
            args.put("password", password);
            args.put("mail", mail);
            jdbcInsert.execute(args);
            
            return new User(username, password, mail);
            
        }
        
        @Override
        public User getByUsername(final String username) {
            List<User> users = jdbcTemplate.query("SELECT * FROM user WHERE username = ? LIMIT 1", userRowMapper, username);
            
            if (users.isEmpty()) {
            	return null;
            } else {
            	return users.get(0);
            }
        }
        
		@Override
		public boolean userNameExists(String name) {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user WHERE username = ?", Integer.class, name) > 0;
		}

		@Override
		public boolean userMailExists(String mail) {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user WHERE mail = ?", Integer.class, mail) > 0;
		}
		
}


