package com.ixilink.banknote_box.message;

import com.ixilink.banknote_box.common.util.IpAddressUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MessageApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(IpAddressUtils.getIp());
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        System.out.println(map);
        map.put("list",list);
        list.add("123");
        System.out.println(map);
    }

}
