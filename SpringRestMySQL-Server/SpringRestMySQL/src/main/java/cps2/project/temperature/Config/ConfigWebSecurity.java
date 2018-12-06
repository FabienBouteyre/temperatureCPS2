package cps2.project.temperature.Config;


import cps2.project.temperature.Service.ServiceUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfigWebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private ServiceUserDetails serviceUserDetails;

//    @Autowired
//    private H2ConsoleProperties console;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        String path = this.console.getPath();
//        String antPattern = (path.endsWith("/") ? path + "**" : path + "/**");
//        HttpSecurity h2Console = http.antMatcher(antPattern);
//        h2Console.csrf().disable();
//        h2Console.httpBasic();
//        h2Console.headers().frameOptions().sameOrigin();

        http
//                .csrf().ignoringAntMatchers("/console/**")
//                .and()
                .authorizeRequests()
                    .antMatchers( "/static/**", "/regist", "/sensors/**", "/api/**", "/", "/home").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login").permitAll().successForwardUrl("/home")
                .and()
                    .logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(serviceUserDetails).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
