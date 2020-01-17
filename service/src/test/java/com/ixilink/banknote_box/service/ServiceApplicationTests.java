package com.ixilink.banknote_box.service;

import com.ixilink.banknote_box.common.util.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
//@RunWith(SpringRunner.class)//会启动项目内核
public class ServiceApplicationTests {

    @Test
    public void contextLoads() {
        String message = "0600002200060106495BA0F6";
        Integer layer = Integer.valueOf(message.substring(12, 14));
        Integer column = Integer.valueOf(message.substring(14, 16),16);
        String RFID = message.substring(16,message.length());
        System.out.println(layer+" "+column+" "+RFID);
        //ATM机通道编号
        Double ceil = Math.ceil(column / 5.0);
        Integer passageway = ceil.intValue();
        //钞箱位置编号
        Integer position = column%5;
        if (position == 0) position = 5;
        System.out.println(passageway+"  "+position);
        System.out.println(DateFormatUtil.strToDate(DateFormatUtil.getStringDateShort()).getTime());
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        String join = StringUtils.join(list, ",");
        System.out.println(join);

        List<String> list2 = new ArrayList<>();
        list2.add("4");
        list2.add("2");

        list.retainAll(list2);
        System.out.println(list);
    }


}
