package com.example.store.controller;

import com.example.store.domain.authentication.User;
import com.example.store.domain.authentication.UserProfile;
import com.example.store.service.UserProfileService;
import com.example.store.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @MockBean
    private UserProfileService userProfileService;

    @MockBean
    private UserService userService;

    @Test
    void testGetRegistration() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/auth/registration");
        MockMvcBuilders.standaloneSetup(this.authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("authentication/registration"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("authentication/registration"));
    }

    @Test
    void testGetRegistration2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/auth/registration");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.authController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("authentication/registration"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("authentication/registration"));
    }

    @Test
    void testRegister() throws Exception {
        User user = new User();
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        user.setEnabled(true);
        when(this.userService.save((User) any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/registration");
        MockMvcBuilders.standaloneSetup(this.authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));
    }

    @Test
    void testSaveUserProfile() throws Exception {
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail("jane.doe@example.org");
        userProfile.setPicturePath("Picture Path");
        userProfile.setUsername("janedoe");
        userProfile.setCity("Oxford");
        userProfile.setName("Name");
        userProfile.setPhoneNumber("4105551212");
        when(this.userProfileService.save((UserProfile) any(), (org.springframework.web.multipart.MultipartFile) any()))
                .thenReturn(userProfile);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/edit-profile");
        MockMvcBuilders.standaloneSetup(this.authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("userProfile"))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/shop"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/shop"));
    }

    @Test
    void testSetEnableUser() throws Exception {
        doNothing().when(this.userService).setEnable((String) any(), (Boolean) any());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/auth/enable");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("enable", String.valueOf(true))
                .param("userId", "foo");
        MockMvcBuilders.standaloneSetup(this.authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/auth/list"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/auth/list"));
    }
}

