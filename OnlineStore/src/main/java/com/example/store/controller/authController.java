package com.example.store.controller;

import com.example.store.domain.authentication.User;
import com.example.store.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/authentication")
public class authController {

    private UserService userService;

    @GetMapping("/list")
    public String getUsers(@RequestParam(required = false) Integer pageNum, Model model) {
        final int pageSize = 5;

        Pageable pageRequest = PageRequest.of(pageNum == null ? 0 : pageNum, pageSize);
        Page<User> page = userService.findAllByPage(pageRequest);

        model.addAttribute("page", page);

        return "authentication/list";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model) {
        model.addAttribute("user", new User());

        return "authentication/registration";
    }

    @PostMapping("/registration")
    public String register(@Valid User user) {
        userService.save(user);

        return "redirect:/login";
    }

    @GetMapping("/enable")
    public String setEnableUser(@RequestParam Long userId, @RequestParam Boolean enable) {
        userService.setEnable(userId, enable);

        return "redirect:/authentication/list";
    }
}
