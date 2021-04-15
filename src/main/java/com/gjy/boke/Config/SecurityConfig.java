package com.gjy.boke.Config;

/**
 * @Author GJY
 * @Date 2021/3/15 9:31
 * @Version 1.0
 *
 *@EnableWebSecurity
 */

public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {

    /*//授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //只有有权限才能访问/admin下
        http.authorizeRequests().antMatchers("/admin/**");
        http.authorizeRequests().antMatchers("/").permitAll();
        //没有权限的自动跳转到login下
        http.formLogin().loginPage("/admin/login");

    }

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    }*/
}
