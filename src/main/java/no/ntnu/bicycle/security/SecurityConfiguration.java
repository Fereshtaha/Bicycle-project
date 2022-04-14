package no.ntnu.bicycle.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{


    @Autowired
    UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
        auth.inMemoryAuthentication()
                .withUser("ferreh@live.no")
                .password("$2a$12$ZFqFytg20hQt3RFA6144x./Nis7s1sFd4G3b0OK8qmKqWKSi8QmVW") // == bcrypt("Yolo1234")
                .roles("USER")
                .and()
                .withUser("sebasn@stud.ntnu.no")
                .password("$2a$12$QjPXqckLsFqDDRxrEfboC.0WYcUSP5wMhuOftGkcnpA9vI1sUOiWa") // == bcrypt("Passord1")
                .roles("USER","ADMIN");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Set up the authorization requests, starting from most restrictive at the top, to least restrictive on bottom
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER","ADMIN")
                .antMatchers("/account").hasAnyRole("USER","ADMIN")
                .antMatchers("/").permitAll()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout().logoutUrl("/logout");

        http.csrf().disable();
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