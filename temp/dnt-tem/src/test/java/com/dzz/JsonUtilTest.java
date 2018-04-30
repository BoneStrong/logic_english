package com.dzz;

import com.dzz.support.util.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author shawn
 * @Date 2017/9/20 17:54
 */
@RunWith(SpringRunner.class)
@JsonTest
public class JsonUtilTest {

    JsonDto jsonDto;

    List<JsonDto> jsonDtos;

    @Before
    public void initJson() {
        jsonDto = new JsonDto();
        jsonDto.setId(2);
        jsonDto.setNamespace("test");
        List<String> ips = new ArrayList<>();
        ips.add("alph");
        ips.add("bate");
        jsonDto.setIps(ips);
        jsonDtos = new ArrayList<>();
        jsonDtos.add(jsonDto);
        jsonDtos.add(jsonDto);
    }


    @Test
    public void json2String() {
        System.out.println(JsonUtil.toString(jsonDto));
    }

    @Test
    public void string2Json() {
        String jsonString = "{\"id\":2,\"namespace\":\"test\",\"ips\":[\"alph\",\"bate\"]}";
        JsonDto json = JsonUtil.toBean(jsonString, JsonDto.class);
        Assert.assertEquals(jsonDto.getNamespace(), json.getNamespace());
        Assert.assertArrayEquals(jsonDto.getIps().toArray(), json.getIps().toArray());
        System.out.println(JsonUtil.toString(jsonDto));
    }

    @Test
    public void list2String() {
        System.out.println(JsonUtil.toString(jsonDtos));
    }

    @Test
    public void string2List() {
        String listString = "[{\"id\":2,\"namespace\":\"test\",\"ips\":[\"alph\",\"bate\"]}," +
                "{\"id\":2,\"namespace\":\"test\",\"ips\":[\"alph\",\"bate\"]}]";
        List<JsonDto> jsonDtoList = JsonUtil.toList(listString, JsonDto.class);
        Assert.assertArrayEquals(jsonDtos.toArray(), jsonDtoList.toArray());
    }
}
