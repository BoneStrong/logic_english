package com.dzz.service;

import com.dzz.support.exception.ExceptionGenerator;
import org.springframework.stereotype.Service;

/**
 * @Author shawn
 * @Date 2017/10/17 17:59
 */
@Service
public class TestService {

    public String doSomeThing(){
        System.out.println("do something then throw exception");
        throw ExceptionGenerator.create("do something exception");
    }

    public String doSomeThing(String a){
        System.out.println("do something then throw exception");
        throw ExceptionGenerator.create("do something exception");
    }

}
