package com.example.springcloudprovider;

import com.example.springcloudprovider.service.UserImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class SpringcloudProviderApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void s1(){
        UserImpl user = new UserImpl();
        String user1 = user.getUser(1);
    }

}
