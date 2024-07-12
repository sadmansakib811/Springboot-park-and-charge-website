package com.infiniteloopers.user.controller;

import com.infiniteloopers.user.model.AppUser;
import com.infiniteloopers.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute AppUser user, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute AppUser user, Model model, HttpSession session) {
        AppUser existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("user", existingUser);
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "Invalid username or password!");
        return "login";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "dashboard";
        }
        return "redirect:/login";
    }

    @GetMapping("/bookings")
    public String showBookingList() {
        return "redirect:http://localhost:8083/bookings";
    }

    @GetMapping("/bookings/create")
    public String showCreateBookingForm() {
        return "redirect:http://localhost:8083/bookings/create";
    }

    @GetMapping("/stations")
    public String showStationList() {
        return "redirect:http://localhost:8082/stations";
    }

    @GetMapping("/stations/create")
    public String showCreateStationForm() {
        return "redirect:http://localhost:8082/stations/create";
    }
}
