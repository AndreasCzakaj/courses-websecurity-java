package com.websecurity.app02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/injection/sql")
public class SqlInjection {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/users")
    @PreAuthorize("@userService.canAccessUser(authentication.name, #id)")
    public List<User> findUsers(@RequestParam String username) {
        //String sql = "SELECT * FROM users WHERE username = '" + username + "'";
        String sql = "SELECT * FROM users WHERE username = ?";
        // URL 0a: http://localhost:8080/injection/sql/user?username=admin
        // URL 0b: http://localhost:8080/injection/sql/user?username=john_doe
        // Attack 1: username = "admin' OR '1'='1' --"
        // Full attack URL 1:
        // Attack 2: admin'; DROP TABLE users; --
        // Full attack URL 2: localhost:8080/injection/sql/user?username=admin'; DROP TABLE users; --
        // URL 3a: http://localhost:8080/injection/sql/user?username=admin
        // URL 3b: http://localhost:8080/injection/sql/user?username=john_doe
        // URL 3c:
        return jdbcTemplate.query(sql, new UserRowMapper(), username);
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPasswordHash("<protected>");
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setRole(rs.getString("role"));
            user.setSafeUserId(rs.getString("safe_user_id"));
            return user;
        }
    }
}
