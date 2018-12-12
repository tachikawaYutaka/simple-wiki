package com.wakabatimes.simplewiki.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security設定クラス.
 */
@Configuration
@EnableWebSecurity   // Spring Securityの基本設定
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public void configure(WebSecurity web) throws Exception {
        // セキュリティ設定を無視するリクエスト設定
        // 静的リソース(images、css、javascript)に対するアクセスはセキュリティ設定を無視する
        web.ignoring().antMatchers(
                "/img/**",
                "/css/**",
                "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 認可の設定
        http.authorizeRequests()
                .antMatchers("/", "/index","/login","/start","/home","/public_content/**","/puml_image/**").permitAll() // indexは全ユーザーアクセス許可
                // ロールが必要なURL
                .antMatchers("/private_content/**").hasRole("USER")
                .anyRequest().authenticated();  // それ以外は全て認証無しの場合アクセス不許可

        // ログイン設定
        http.formLogin()
                .loginProcessingUrl("/login")   // 認証処理のパス
                .loginPage("/login")            // ログインフォームのパス
                .defaultSuccessUrl("/user_home")     // 認証成功時の遷移先
                .usernameParameter("userName").passwordParameter("userPassword")  // ユーザー名、パスワードのパラメータ名
                .and();

        // ログアウト設定
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))       // ログアウト処理のパス
                .logoutSuccessUrl("/home");                                        // ログアウト完了時のパス

    }

    @Autowired
    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}