package com.fpt.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserService userservice;



    @GetMapping("")
    public String showHomePage(Model model, HttpSession session, RedirectAttributes ra) {
        if (session.getAttribute("username") == null) {
            model.addAttribute("users", new User());
            return "index";
        } else {
            int tou =0;
            if(session.getAttribute("tou")!=null){

                tou=(int) session.getAttribute("tou");
            }
            if (tou == 1) {
                String idu = "admin";
                model.addAttribute("idu", idu);
                return "redirect:/products/add";
            } else {
                ra.addFlashAttribute("message", "You are already logged in!");
                return "redirect:/products/show";
            }

        }

    }

    @PostMapping("/login")
    public String login(User user, RedirectAttributes ra, HttpSession session, Model model) {
        if (userservice.loginauth(user.getUsername(), user.getPassword())) {
           // int cartid = cartservice.GetCartId(user.getUsername());
            int tou = userservice.getTypeofuser(user.getUsername());
            String usern = user.getUsername().toLowerCase().trim();
            session.setAttribute("username", usern);
        //    session.setAttribute("cartid", cartid);
            session.setAttribute("tou", tou);
            if (tou == 1) {
                String idu = "admin";
                model.addAttribute("idu", idu);
                return "redirect:/products/add";
            } else {
                ra.addFlashAttribute("message", "Welcome to MobileStore: " + user.getUsername());
                return "redirect:/products/show";
            }

        } else {
            ra.addFlashAttribute("messagelogout", "Wrong Username or Password, please try again!!!");
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes ra) {
        session.removeAttribute("username");
        session.removeAttribute("cartid");
        session.removeAttribute("tou");
        ra.addFlashAttribute("messagelogout", "Logout Successfully!!!");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLogin(){
        return "redirect:/";
    }
}


