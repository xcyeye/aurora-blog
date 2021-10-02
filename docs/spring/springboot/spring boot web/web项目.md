## 重复表单提交

解决表单重复提交最好的办法是使用重定向



## 拦截器

步骤

1. 创建一个类，实现`HandlerInterceptor`接口，并且重写其中的三个方法
2. 创建配置类，实现`WebMvcConfigurer`接口，并重写`addInterceptors (InterceptorRegistry registry)`方法
3. 在拦截器中，配置拦截条件
4. 在配置类中，配置拦截路径



```java
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 在返回页面之前执行
     * @author chuchen
     * @date 2021/7/6 17:56
     * @param request
     * @param response
     * @param handler
     * @return boolean
     */
    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("loginInfo");
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        if (userInfo != null) {
            log.info("path{}",requestURI);
            return true;
        }
        response.sendRedirect("/");
        return false;
    }

    /**
     *  在处理请求之后执行
     */
    @Override
    public void postHandle (HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     *  在响应页面完成之后，执行
     */
    @Override
    public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
```

```java
@Configuration
public class LoginConf implements WebMvcConfigurer {

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        //"*/css/**","*/js/**","*/images/**","*/fonts/**"
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/","/css/**","/js/**","/images/**","/fonts/**");

    }
}
```



拦截器中的三个方法，一个是发送请求，请求处理完成后，拦截

一个是，在响应页面之前，已经放行了

一个是，浏览器响应页面完成之后



因为页面中，存在很多的静态资源，但是对于静态资源，我们不用拦截，所以就要在配置类类中，配置拦截路径规则

![image-20210706184413638](http://ooszy.cco.vin/img/blog-note/image-20210706184413638.png?x-oss-process=style/pictureProcess1)

静态资源都是以css,font等开头的，所以可以直接`"/css/**","/js/**","/images/**","/fonts/**"`

，但是不能`"*/css/**","*/js/**","*/images/**","*/fonts/**"`这样配置是错误的

并且拦截所以请求，不能`addPathPatterns("/")`，这个只是拦截`localhost:8080`请求，拦截所有，需要`/**`



对静态资源的拦截，还可以配置静态资源的访问路径，在配置文件中设置，比如所有静态资源都是以static开头，那么放行静态资源就可以直接`/static/**`





## 文件上传

因为默认单个文件上传大小为1MB，多个文件上传大小为10MB，所以就需要更改文件上传大小



