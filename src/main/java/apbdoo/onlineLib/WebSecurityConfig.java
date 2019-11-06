package apbdoo.onlineLib;


import apbdoo.onlineLib.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private DataSource securityDataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .and().authorizeRequests()
                .antMatchers("/registration/**",
                        "/books/**",
                        "/book/details/**",
                        "/js/**",
                        "/css/**",
                        "/images/**",
                        "/pdfs/**",
                        "/**/cover/**",
                        "/webjars/**").permitAll()
                .and().authorizeRequests()
                .antMatchers( "/mybooks").hasAuthority("ROLE_USER")
                .antMatchers("/book/add","/book/**/image","/book/**/uploadPDF","/book/**/edit").hasAuthority("ROLE_ADMIN")
                .and().authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .accessDeniedPage("/accessDenied").and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and()
                .logout()
                .permitAll();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
//        auth.inMemoryAuthentication()
//                .withUser("userCustomer").password(passwordEncoder().encode("1234")).roles("CUSTOMER", "HELLO")
//                .and().withUser("userHello").password(passwordEncoder().encode("1234")).roles("HELLO");

    }

}