# 重定向和请求转发操作

当处理器对请求处理完毕后，向其它资源进行跳转时，有两种跳转方式：请求转发与重定向。而根据所要跳转的资源类型，又可分为两类：跳转到页面与跳转到其它处理器。

注意，对于请求转发的页面，可以是WEB-INF中页面；而重定向的页面，是不能为WEB-INF中页的。因为重定向相当于用户再次发出一次请求，而用户是不能直接访问 WEB-INF 中资源的

![image-20210527223102909](http://ooszy.cco.vin/img/blog-note/image-20210527223102909.png?x-oss-process=style/pictureProcess1)

SpringMVC 框架把原来 Servlet 中的请求转发和重定向操作进行了封装。现在可以使用简单的方式实现转发和重定向。

forward:表示转发，实现 request.getRequestDispatcher("xx.jsp").forward()redirect:表示重定向，实现 response.sendRedirect("xxx.jsp")



## 请求转发

请求转发其实就是`ModelAndView`对象的`setViewName()`方法的作用，这个方法的作用就是请求转发，只是如果设置了视图解析器之后，那么就不可以请求转发到视图解析器路径之外的地址，但是使用`setName("forward:...")`设置之后，就可以转发到视图解析器之外的路径

> 使用`setViewName("forward:cc.jsp")`对资源进行请求转发，不能和视图解析器一同工作，也就是，此时的视图解析器配置为，那么这里，就不能直接填写`/WEB-INF/jsp/`路径下的资源，必须这样才可以`setViewName("forward:/WEB-INF/jsp/cc.jsp")`
>
> ```xml
> <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" >
>     <property name="prefix" value="/WEB-INF/jsp/" />
>     <property name="suffix" value=".jsp" />
> </bean>
> ```
>
> 

`setViewName("forward:")不能和视图解析器一同工作`

```java
@RequestMapping("forward")
public ModelAndView forward(String name,String age) {
    ModelAndView mv = new ModelAndView();
    mv.addObject("name",name);
    mv.addObject("age",age);
    mv.setViewName("forward:/cc.jsp");
    System.out.println("请求转发了");
    return mv;
}
```





## 重定向

在处理器方法返回的视图字符串的前面添加 redirect:，则可实现重定向跳转。处理器方法定义：

```java
@RequestMapping("redirect")
public ModelAndView redirect(String name,String age) {
    ModelAndView mv = new ModelAndView();
    mv.addObject("name",name);
    mv.addObject("age",age);

    System.out.println("name --->"+name+" age--->"+age);

    mv.setViewName("redirect:/cc.jsp");
    System.out.println("重定向了");
    return mv;
}
```



### 地址变化

我们知道，重定向的时候，其实是访问了两个地址，但是在servlet中，如果第一个地址中含有参数和值，那么重定向到第二个时，其地址上面不会出现参数和参数的值，但是使用springmvc进行重定向时，如果第一个地址存在参数和值(尽管参数值为空)，那么在重定向到第二个地址的时候，框架就会将第一个地址中的参数和值，放在第二个地址上面，这个过程是框架做的，主要就是让我们操作方便

- 第一个地址

    > http://localhost/ssm/student/redirect?name=chuchen&age=22

- 重定向到的第二个地址

    > http://localhost/ssm/cc.jsp?name=chuchen&age=22

```java
@RequestMapping("redirect")
public ModelAndView redirect(String name,String age) {
    ModelAndView mv = new ModelAndView();
    mv.addObject("name",name);
    mv.addObject("age",age);
    System.out.println("name --->"+name+" age--->"+age);
    mv.setViewName("redirect:/cc.jsp");
    System.out.println("重定向了");
    return mv;
}
```



对于框架的重定向操作，不能重定向到`WEB-INF`目录下，因为重定向就是再次访问，而`WEB-INF`目录中的资源，是不可以直接访问的，使用请求转发可以访问到

