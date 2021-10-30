package com.example.store.controller;

import com.example.store.domain.authentication.User;
import com.example.store.domain.authentication.UserProfile;
import com.example.store.service.UserProfileService;
import com.example.store.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    private UserProfileService userProfileService;

    @GetMapping("/edit-profile")
    public String editUserProfile(Model model) {
            UserProfile userProfile = userProfileService.findByName(
                    SecurityContextHolder.getContext().getAuthentication().getName()).orElse(new UserProfile());
            model.addAttribute("userProfile", userProfile);
            return "authentication/user-profile";
    }

    @PostMapping("/edit-profile")
    public String saveUserProfile(@ModelAttribute UserProfile userProfile,
                                  @RequestParam(required = false) MultipartFile image) {
        userProfileService.save(userProfile, image);
        return "redirect:/shop";
    }

    @GetMapping("/list")
    public String getUsers(@RequestParam(required = false) Integer pageNum, Model model) {
        final int pageSize = 5;

        Pageable pageRequest = PageRequest.of(pageNum == null ? 0 : pageNum, pageSize);
        Page<User> page = userService.findAllByPage(pageRequest);

        //Authentication check and forwarding user info
        if (authCheck().isPresent()) model.addAttribute("userProfile", authCheck().orElse(new UserProfile()));
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
    public String setEnableUser(@RequestParam String userId, @RequestParam Boolean enable) {
        userService.setEnable(userId, enable);

        return "redirect:/auth/list";
    }

    private Optional<UserProfile> authCheck (){
        return userProfileService.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
