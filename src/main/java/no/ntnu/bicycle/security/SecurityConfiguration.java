package no.ntnu.bicycle.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{


    @Autowired
    private UserDetailsService userDetailsService;

    public SecurityConfiguration(AccessUserService accessUserService) {
        this.userDetailsService = accessUserService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String admin = "ADMIN";
        final String user = "USER";
        // Set up the authorization requests, starting from most restrictive at the top, to least restrictive on bottom
        http.csrf().disable().authorizeRequests()
                .antMatchers("/admin").hasRole(admin)
                .antMatchers("/user").hasAnyRole(user,admin)
                .antMatchers("/account").hasAnyRole(user,admin)
                .antMatchers("/").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/loginOk")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout().logoutUrl("/logout")
                //.deleteCookies("JSESSIONID")
                //.invalidateHttpSession(true)
        ;
    }

    /**
     * This method is called to decide what encryption to use for password checking
     *
     * @return The password encryptor
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}