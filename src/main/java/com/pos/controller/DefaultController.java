//package com.pos.controller;
//
//import com.pos.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.Map;
//
//@Controller
//public class DefaultController {
//
//    @Autowired
//    private CustomerService customerService;
//
//    @GetMapping("/")
//    public String home() {
//        System.out.println(customerService.sayHello());
//        return "/home";
//    }
//
////    @GetMapping("/home")
////    public String home() {
////        return "/home";
////    }
//
//    @GetMapping("/admin")
//    public String admin() {
//        return "/admin";
//    }
//
//    @GetMapping("/user")
//    public String user() {
//        return "/user";
//    }
//
//    @GetMapping("/about")
//    public String about() {
//        return "/about";
//    }
//
//    @GetMapping("/login")
//    public String login() {
//        return "/login";
//    }
//
//    @GetMapping("/403")
//    public String error403() {
//        return "/error/403";
//    }
//
//    @Value("${welcome.message:test}")
//    private String message = "Hello World";
//
////    @GetMapping("/")
////    public String welcome(Map<String, Object> model) {
////        model.put("message", this.message);
////        return "welcome";
////    }
//}
