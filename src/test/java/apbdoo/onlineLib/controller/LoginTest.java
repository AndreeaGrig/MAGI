package apbdoo.onlineLib.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void loginValidTest() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder
                login = formLogin().user("andreea@gmail.com").password("1234567");
        mockMvc.perform(login).andExpect(authenticated().withUsername("andreea@gmail.com"));
    }

    @Test
    public void accessUnprotectedTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void accessProtectedRedirectsToLoginTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/mybooks"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertEquals("http://localhost/login",mvcResult.getResponse().getRedirectedUrl());
    }


    @Test
    public void loginInvalidUserTest() throws Exception {
        this.mockMvc.perform(formLogin().user("invalid").password("invalid"))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void loginUserAccessProtectedTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(formLogin().user("andreea@gmail.com").password("1234567"))
                .andExpect(authenticated()).andReturn();

        MockHttpSession httpSession = (MockHttpSession) mvcResult.getRequest().getSession(false);

        this.mockMvc.perform(get("/mybooks").session(httpSession))
                .andExpect(status().isOk());
    }

    @Test
    public void loginUserAccessProtectedInvalidTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(formLogin().user("andreea@gmail.com").password("1234567"))
                .andExpect(authenticated()).andReturn();

        MockHttpSession httpSession = (MockHttpSession) mvcResult.getRequest().getSession(false);

        this.mockMvc.perform(get("/book/add").session(httpSession))
                .andExpect(forwardedUrl("/accessDenied"));
    }

    @Test
    public void loginUserValidateLogout() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(formLogin().user("andreea@gmail.com").password("1234567"))
                .andExpect(authenticated()).andReturn();

        MockHttpSession httpSession = (MockHttpSession) mvcResult.getRequest().getSession(false);

        this.mockMvc.perform(post("/logout").with(csrf()).session(httpSession))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/login?logout"));
        this.mockMvc.perform(get("/mybooks").session(httpSession))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection());
    }

}
