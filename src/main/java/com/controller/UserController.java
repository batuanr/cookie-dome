package com.controller;

import com.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes("user")
public class UserController {
    @ModelAttribute("user")
    public User setUserForm(){
        return new User();
    }
    @RequestMapping("/login")
    public String Index(@CookieValue(value = "setUser", defaultValue = "") String setUser,@CookieValue(value = "pass", defaultValue = "") String pass, Model model) {
        Cookie cookie = new Cookie("setUser", setUser);
        Cookie cookie1 = new Cookie("pass", pass);
        model.addAttribute("cookieValue", cookie);
        model.addAttribute("pass", cookie1);
        return "/login";
    }
    @PostMapping("/dologin")
    public String doLogin(@ModelAttribute("user") User user, Model model, @CookieValue(value = "setUser", defaultValue = "") String setUser,
                          @CookieValue(value = "pass", defaultValue = "") String pass,HttpServletResponse response, HttpServletRequest request) {
        //implement business logic
        if (user.getEmail().equals("admin@gmail.com") && user.getPassword().equals("12345")) {
            setUser = user.getEmail();
            pass = user.getPassword();
            Cookie cookie1 = new Cookie("pass" , pass);
            // create cookie and set it in response
            Cookie cookie = new Cookie("setUser", setUser);
            cookie.setMaxAge(24 * 60 * 60);
            cookie1.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
            response.addCookie(cookie1);
            model.addAttribute("cookieValue", cookie);
            model.addAttribute("pass", cookie1);
            //get all cookies
//            Cookie[] cookies = request.getCookies();
//            //iterate each cookie
//            for (Cookie ck : cookies) {
//                //display only the cookie with the name 'setUser'
//                if (!ck.getName().equals("setUser")) {
//                    ck.setValue("");
//                }
//
//                break;
//            }

            model.addAttribute("message", "Login success. Welcome ");
        } else {
            user.setPassword("");
            user.setEmail("");
            pass = "";
            setUser = "";
            Cookie cookie1 = new Cookie("pass", pass);
            Cookie cookie = new Cookie("setUser", setUser);
            model.addAttribute("cookieValue", cookie);
            model.addAttribute("pass", cookie1);
            model.addAttribute("message", "Login failed. Try again.");
        }
        return "/login";
    }
}
