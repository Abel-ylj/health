package cn.ylj;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : yanglujian
 * create at:  2021/1/19  1:03 下午
 */
public class App {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-jobs.xml");
    }
}