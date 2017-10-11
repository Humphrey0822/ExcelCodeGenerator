package com.ouyeel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello(Map<String,Object> map){
        map.put("name", "[Angel -- 守护天使]");
        return "hello";
    }

//    @RequestMapping("/main.action")
//    public ModelAndView main() {
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("main");
//        return mv;
//    }

}
