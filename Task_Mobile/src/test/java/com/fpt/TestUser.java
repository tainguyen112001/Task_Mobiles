package com.fpt;


import com.fpt.user.User;
import com.fpt.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TestUser {
    @Autowired
    private UserRepository repo;
    @Test
    public void testAddNew(){
        User user = new User();
        user.setUsername("customerrrr");
        user.setPassword("customerrrr");
        user.setTypeofuserid(2);
        User savedUser= repo.save(user);
        Assertions.assertThat(savedUser).isNotNull();
    }
}
