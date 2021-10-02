# aop介绍

## 什么是动态代理

假如我们现在有一个项目，并且这个项目已经上线运行一段时间了，但是运行之后，我们现在需要对这个项目中的某些模块，加上一些功能，最简单普遍的做法就是，在这些类中，进行代码的更改，但是这种时候，如果是加入的是，执行一些日志的打印，事务的提交的话，把这些代码全部放在一起，就会使这个类变得非常的臃肿，那么我们就可以使用动态代理的方式，进行代码的更改，使用代理去执行目标方法，在代理类中，去处理需要加入功能的逻辑代码



## 步骤

使用动态代理，可以使用jdk自带的方式，但是这个方式，需要实现一个接口`InvocationHandler`



1. 创建目标类， 例如SomeServiceImpl目标类
2. 创建InvocationHandler接口的实现类， 在这个类实现给目标方法增加功能。
3. 使用jdk中 类Proxy，创建代理对象。 实现创建对象的能力。



代码实现

目标类

```java
public interface LoginService {
    void doPost();
    void doOther();
}
```

```java
//实现类
public class LoginServiceImpl implements LoginService {
    @Override
    public void doPost () {
        System.out.println("doPost is doing....");
    }

    @Override
    public void doOther () {
        System.out.println("doOther is doing....");
    }
}
```

需要在实现类的方法执行前，进行日志打印，在方法执行后，执行事务提交

```java
//事务提交，日志打印工具类
public class LoginUtil {
    public static void doLog() {
        System.out.println("执行打印日志" + new SimpleDateFormat().format(new Date()));
    }

    public static void doTrac() {
        System.out.println("执行事务提交");
    }
}
```

```java
//代理
public class MyInvocationHandler implements InvocationHandler {

    /** 目标对象 */
    private Object target;

    public MyInvocationHandler (Object target) {
        this.target = target;
    }

    @Override
    public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {

        Object rs = null;

        //执行打印日志方法
        LoginUtil.doLog();

        //执行目标对象的方法
        rs = method.invoke(target,args);
        System.out.println("method: "+method.getName());

        LoginUtil.doTrac();
        return rs;
    }
}
```

```java
//测试类
@Test
public void test2() throws Exception {
    //使用动态jdk的动态代理进行测试执行
    InvocationHandler handler = new MyInvocationHandler(service);

    LoginService proxy = 			(LoginService)Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                                                              service.getClass().getInterfaces(), handler);

    proxy.doOther();
}
```

```java
//结果
执行打印日志2021/5/20 下午2:47
doOther is doing....
method: doOther
执行事务提交
```

可以从中，看到，执行结果，就是我们想要的效果，使用这种方式，能够使

![image-20210520144759508](http://ooszy.cco.vin/img/blog-note/image-20210520144759508.png?x-oss-process=style/pictureProcess1)

实现类中的代码，变得特别简洁，我们也不需要在这个实现类中，进行代码的更改，只需要在代理中，进行就可以了



代理执行原理

![image-20210520145138764](http://ooszy.cco.vin/img/blog-note/image-20210520145138764.png?x-oss-process=style/pictureProcess1)



调用`proxy.doOther();`之后，程序就会进入到`handler`代理类中，去执行

```java
LoginService proxy = (LoginService)Proxy.newProxyInstance(handler.getClass().getClassLoader(),
service.getClass().getInterfaces(), handler);
```

![image-20210520145527946](http://ooszy.cco.vin/img/blog-note/image-20210520145527946.png?x-oss-process=style/pictureProcess1)



`newProxyInstance()`方法存在三个参数，第一个是类记载器，第二个是，目标类的接口类，第三个是`InvocationHandler`对象，所以，如果要使用动态代理，就必须要保证，目标类，存在接口类

使用动态代理，可以使代码变得简洁，还能降低程序之间的耦合度，进行解耦操作





# 什么是aop

AOP（Aspect Orient Programming），面向切面编程。面向切面编程是从动态角度考虑程

序运行过程。

`Aspect`切面，什么是切面，就是为这个类中的某个方法，增加其他的功能，就是切面

AOP 底层，就是采用动态代理模式实现的。采用了两种代理：JDK 的动态代理，与 CGLIB

的动态代理。



AOP 为 Aspect Oriented Programming 的缩写，意为：面向切面编程，可通过运行期动态代理实现程序功能的统一维护的一种技术。AOP 是 Spring 框架中的一个重要内容。利用 AOP

可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程

序的可重用性，同时提高了开发的效率。



面向切面编程，就是将交叉业务逻辑封装成切面，利用 AOP 容器的功能将切面织入到

主业务逻辑中。所谓交叉业务逻辑是指，通用的、与主业务逻辑无关的代码，如安全检查、

事务、日志、缓存等。

若不使用 AOP，则会出现代码纠缠，即交叉业务逻辑与主业务逻辑混合在一起。这样，

会使主业务逻辑变的混杂不清。就比如说，在登录中方法中，我们又加上日志打印，等等其他的

例如，转账，在真正转账业务逻辑前后，需要权限控制、日志记录、加载事务、结束事

务等交叉业务逻辑，而这些业务逻辑与主业务逻辑间并无直接关系。但，它们的代码量所占

比重能达到总代码量的一半甚至还多。它们的存在，不仅产生了大量的“冗余”代码，还大

大干扰了主业务逻辑---转账。



## 什么是面向切面编程？

面向切面编程和面向对象编程是一样的，OOP是我们在设计程序的时候，应该考虑这些对象都有哪些功能，哪些属性，AOP就是我们所面对的是一个切面或者是很多个切面

![image-20210520152030505](http://ooszy.cco.vin/img/blog-note/image-20210520152030505.png?x-oss-process=style/pictureProcess1)



面向切面编程，就是我们需要找出这个切面，比如一个转账的方法，我们就可以将这个方法作为一个切面，那么下一步，就是我们需要找出为这个方法增加的功能在什么时候执行，增加的功能有哪些

就比如，在转账前，我们要增加日志的打印功能

在转账后，需要执行事务提交功能



> 1. 需要在分析项目功能时，找出切面。
> 2. 合理的安排切面的执行时间（在目标方法前， 还是目标方法后）
> 3. 合理的安全切面执行的位置，在哪个类，哪个方法增加增强功能

## 使用好处

1. 减少重复；

2. 专注业务；

注意：面向切面编程只是面向对象编程的一种补充。





##   术语
1. Aspect:切切面泛指交叉业务逻辑。上例中的事务处理、日志处理就可以理解为切面。常用的切面

    是通知（Advice）。实际就是对主业务逻辑的一种增强。常见的切面功能有日志， 事务， 统计信息， 参数检查， 权限验证。

2. JoinPoint:连接点 ，连接业务方法和切面的位置。连接点指可以被切面织入的具体方法。通常业务接口中的方法均为连接点，就某类中的业务方法，就比如下面代码

    `rs = method.invoke(target,args);`就是一个连接点

    ```java
    //执行打印日志方法
    LoginUtil.doLog();
    //执行目标对象的方法
    rs = method.invoke(target,args);
    LoginUtil.doTrac();
    ```

    

3. Pointcut : 切入点指声明的一个或多个连接点的集合。通过切入点指定一组方法。

    被标记为 final 的方法是不能作为连接点与切入点的。因为最终的是不能被修改的，不

    能被增强的。

4. 目 标 对 象 指 将 要 被 增 强 的 对 象 。 即 包 含 主 业 务 逻 辑 的 类 的 对 象 。 上 例 中 的StudentServiceImpl 的对象若被增强，则该类称为目标类，该类对象称为目标对象。当然，

    不被增强，也就无所谓目标不目标了。

5. 通知表示切面的执行时间，Advice 也叫增强。上例中的 MyInvocationHandler 就可以理

    解为是一种通知。换个角度来说，通知定义了增强代码切入到目标代码的时间点，是目标方

    法执行之前执行，还是之后执行等。通知类型不同，切入时间不同。

    切入点定义切入的位置，通知定义切入的时间。也就是在什么时候执行这个切面

 ```java
    //执行打印日志方法
    LoginUtil.doLog();
    //执行目标对象的方法
    rs = method.invoke(target,args);
    LoginUtil.doTrac();
 ```

那么对于这个`LoginUtil.doLog()`功能的的通知就是，在方法执行前

## 切面的三个关键的要素

- 切面的功能代码，切面干什么，也就是打印日志，还是提交事务等
- 切面的执行位置，使用Pointcut表示切面执行的位置
- 切面的执行时间，使用Advice表示时间，在目标方法之前，还是目标方法之后。



# 使用aop

aop其实就是和jdk提供的动态代理一样，只是很多前辈规定了其中的规范



# **AspectJ**

对于 AOP 这种编程思想，很多框架都进行了实现。Spring 就是其中之一，可以完成面向

切面编程。然而，AspectJ 也实现了 AOP 的功能，且其实现方式更为简捷，使用更为方便，

而且还支持注解式开发。所以，Spring 又将 AspectJ 的对于 AOP 的实现也引入到了自己的框

架中。

在 Spring 中使用 AOP 开发时，一般使用 AspectJ 的实现方式

AspectJ 是一个优秀面向切面的框架，它扩展了 Java 语言，提供了强大的切面实现。



使用AspectJ可以使用xml配置文件，也可以使用注解的方式

## AspectJ 中常用的通知有五种类型：

（1）前置通知

（2）后置通知

（3）环绕通知

（4）异常通知

（5）最终通知

> - @Before
> - @AfterReturning
> - @Around
> - @AfterThrowing
> - @After



## AspectJ切入点表达式

这个表达式的语法是一个全新的语法

AspectJ 定义了专门的表达式用于指定切入点。表达式的原型是

```java
execution(modifiers-pattern? ret-type-pattern 
declaring-type-pattern?name-pattern(param-pattern)
 throws-pattern?)
```

**解释：**

- modifiers-pattern] 访问权限类型

- ret-type-pattern 返回值类型

- declaring-type-pattern 包名类名

- name-pattern(param-pattern) 方法名(参数类型和参数个数)

- throws-pattern 抛出异常类型

- ？表示可选的部分

![image-20210520164832621](http://ooszy.cco.vin/img/blog-note/image-20210520164832621.png?x-oss-process=style/pictureProcess1)



以上表达式共 4 个部分。

execution(访问权限 方法返回值 方法声明(参数) 异常类型)

切入点表达式要匹配的对象就是目标方法的方法名。所以，execution 表达式中明显就

是方法的签名。注意，表达式中黑色文字表示可省略部分，各部分间用空格分开。在其中可

以使用以下符号：

![image-20210520164859313](http://ooszy.cco.vin/img/blog-note/image-20210520164859313.png?x-oss-process=style/pictureProcess1)



### 举例说明

举例：

`execution(public * *(..))` 

> 指定切入点为：所有使用public修饰，所有包，所有方法名

`execution(* set*(..)) `

> 指定切入点为：任何一个以“set”开始的方法。

`execution(* com.xyz.service.*.*(..)) `

> 指定切入点为：定义在 `com.xyz.service.*` 包里的任意类的任意方法。

`execution(* com.xyz.service..*.*(..))`

> 指定切入点为：定义在 service 包或者子包里的任意类的任意方法。“..”出现在类名中时，后
>
> 面必须跟“*”，表示包、子包下的所有类。

`execution(* *..service.*.*(..))`

指定所有包下的 serivce 子包下所有类（接口）中所有方法为切入点

`execution(* *.service.*.*(..))`

指定只有一级包下的 serivce 子包下所有类（接口）中所有方法为切入点

`execution(* *.ISomeService.*(..))`

指定只有一级包下的 ISomeSerivce 接口中所有方法为切入点

`execution(* *..ISomeService.*(..))`

指定所有包下的 ISomeSerivce 接口中所有方法为切入点

`execution(* com.xyz.service.IAccountService.*(..)) `

指定切入点为：IAccountService 接口中的任意方法。

`execution(* com.xyz.service.IAccountService+.*(..)) `

指定切入点为：IAccountService 若为接口，则为接口中的任意方法及其所有实现类中的任意

方法；若为类，则为该类及其子类中的任意方法。

`execution(* joke(String,int)))`

指定切入点为：所有的 joke(String,int)方法，且 joke()方法的第一个参数是 String，第二个参

数是 int。如果方法中的参数类型是 java.lang 包下的类，可以直接使用类名，否则必须使用

全限定类名，如 joke( java.util.List, int)。

`execution(* joke(String,*))) `

指定切入点为：所有的 joke()方法，该方法第一个参数为 String，第二个参数可以是任意类

型，如joke(String s1,String s2)和joke(String s1,double d2)都是，但joke(String s1,double d2,String 

s3)不是

`execution(* joke(String,..))) `

指定切入点为：所有的 joke()方法，该方法第一个参数为 String，后面可以有任意个参数且

参数类型不限，如 joke(String s1)、joke(String s1,String s2)和 joke(String s1,double d2,String s3)

都是。

`execution(* joke(Object))`

指定切入点为：所有的 joke()方法，方法拥有一个参数，且参数是 Object 类型。joke(Object ob)

是，但，joke(String s)与 joke(User u)均不是。

`execution(* joke(Object+))) `

指定切入点为：所有的 joke()方法，方法拥有一个参数，且参数是 Object 类型或该类的子类。

不仅 joke(Object ob)是，joke(String s)和 joke(User u)也是。



> 使用注意
>
> `对于匹配所有修饰符，可以不用*`
>
> 如果这样写的话`value = "execution(* * vin.cco.a2.SomeServiceImpl.doOther())"`就会报错
>
> 按照理解，应该是，匹配所有的修饰符，所有的返回值类型，但是其实不然，这是一个错误的表达式`Pointcut is not well-formed: expecting '(`，可以这样写`value = "execution(* vin.cco.a2.SomeServiceImpl.doOther())"`因为修饰符是一个可选的，如果没有写的话2，就表示匹配所有的，同理，如果返回值类型也是不写的话，就表示匹配所有的返回值类型，`测试，如果连续出现两个*会出错，但是只出现一个，就不会`





## 使用

1. 新建maven工程

2. 加入spring-context，spring-aspects依赖

    ```
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.3.6</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aspects</artifactId>
        <version>5.3.6</version>
    </dependency>
    ```

3. 新建目标接口类，和实现类

4. 新建一个切面类，使用`@Aspect`，注明这是一个切面类，并在这个类中指明切面方法

5. 创建spring配置文件

6. 新建测试类



```java
//service
public interface SomeService {
    void doSome(String name,int age);
    void doOther();
}
```

```java
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome (String name, int age) {
        System.out.println("实现 dosome方法执行.....");
    }
    public void doOther() {
        System.out.println("执行其他的方法");
    }
}
```

```java
@Aspect
public class MyAspectj {

    @Before("execution(public void vin.cco.a1.SomeServiceImpl.doSome(String,int))")
    public void aspect1() {
        System.out.println("执行日志输出: "+new Date());
    }
}
```

```java
@Test
public void test() throws Exception {
    String path = "applicationContext1.xml";
    ApplicationContext context = new ClassPathXmlApplicationContext(path);
    SomeService proxy = (SomeService) context.getBean("server1");
    proxy.doSome("chuchen",20);
    proxy.doOther();
}
```

```
<bean id="server1" class="vin.cco.a1.SomeServiceImpl" />
<bean id="myAspectj" class="vin.cco.a1.MyAspectj" />
<aop:aspectj-autoproxy />
```

当加入这个`<aop:aspectj-autoproxy />`标签之后，就会在上面加入一个约束文件，idea自动加上的

新建一个普通类，并在类上面使用`@Aspect`注明这是同一个切面类，这个类的作用就是用来给业务方法增加功能的类，在这个类中有切面的功能代码

> 在这个类中，定义的方法，需要准守一些规定
>
> 1. 修饰符需使用public（但是也可以是private等 测试通过）
> 2. 没有返回值
> 3. 方法名可以有参数，也可以没有参数，但是如果需要有参数的话，参数类型只能是那几个规定的，不能是自定义



在方法中，定义通知类型，使用那几个注解，进行设置，还有切面的执行位置

```java
@Before("execution(public void vin.cco.a1.SomeServiceImpl.doSome(String,int))")
private void aspect1() {
    System.out.println("执行日志输出: "+new Date());
}
```

`@Before`前置通知注解，在目标方法执行前执行，不会改变影响目标方法的执行

方法体中，就是这个切面要执行的功能



使用这种方式，执行的过程

因为使用配置文件管理容器，如果配置文件中，没有`<aop:aspectj-autoproxy />`标签，那么尽管使用了`@Aspect`注解，其也是一个普通类（或者有这个标签，但是还没有执行到的时候），但是如果有这个标签，并且执行到这个标签之后

![image-20210520222303503](http://ooszy.cco.vin/img/blog-note/image-20210520222303503.png?x-oss-process=style/pictureProcess1)

就会在整个容器中，也就是定义的`<bean>`标签中，进行扫描，寻找是否存在`@Aspect`注解，如果没有，那么就是普通对象，反之存在，那么就会在这个切面类中寻找功能代码，因为功能代码都写在切面类中，从`@Before("execution(public void vin.cco.a1.SomeServiceImpl.doSome(String,int))")`就可以知道，目标类的位置，根据这个指引，就可以在容器中，找到这个对象，那么现在就将这个对象进行改造，变成代理，并且加上功能代码



同时，如果需要多个前置通知代理，那么就可以创建多个方法，并且在这多个方法的上面添加上`@Before`就可以实现，也可以进行改造

```java
@Before("execution(void *..SomeServiceImpl.do*(..))")
private void aspect1() {
    System.out.println("执行日志输出: "+new Date());
}
```

在这个类中，所有以`do`开头的方法都可以，那么对于调用其他以`do`开头的方法，都可以使用这个功能代码

### 在什么时候，使用这个

1. 就比如我们要为所有的方法，都添加一个日志输出的时候，就是利用aop的思想，因为不可能在每一个方法中，都去调用这个日志输出的方法
2. 在业务中，事务提交的时候
3. 等等

## 参数中使用JoinPoint

如果在执行代理的时候，需要使用到方法中的参数等信息的话，就可以在方法的参数中加入`JoinPoint jp`，使用这个对象，就可以获得，方法中的信息，`如果要使用这个，这个参数一定是位于第一位`

指定通知方法中的参数 ： JoinPoint

- JoinPoint:业务方法，要加入切面功能的业务方法
- 作用是：可以在通知方法中获取方法执行时的信息， 例如方法名称，方法的实参。
- 中需要用到方法的信息，就加入JoinPoint.
- 这个JoinPoint参数的值是由框架赋予， 必须是第一个位置的参数

```java
@Before("execution(void *..SomeServiceImpl.do*(..))")
private void aspect1(JoinPoint jp) {
    System.out.println("执行日志输出: "+new Date());
    System.out.println("------------------");
    System.out.println(jp.getSignature());
    System.out.println(jp.getSignature().getName());

    for (Object arg : jp.getArgs()) {
        System.out.println("arg: "+arg);
    }
}
```

 输出

```java
执行日志输出: Thu May 20 23:00:49 CST 2021
------------------
void vin.cco.a1.SomeService.doSome(String,int)
doSome
arg: chuchen
arg: 20
实现 dosome方法执行.....
```



## 后置通知

后置通知：就是在目标方法执行结束之后，执行的功能代码，使用注解`@AfterReturning`

```java
public interface SomeService {
    void doSome(String name,int age);
    String doOther();
}
```

```java
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome (String name, int age) {
        System.out.println("实现 dosome方法执行.....");
    }
    public String doOther() {
        System.out.println("执行其他的方法");
        return "chuchenblog";
    }
}
```

aspectj

```java
@AfterReturning(value = "execution( * vin.cco.a2.SomeServiceImpl.doOther())",
                returning = "res")
public void log(Object res) {
    System.out.println("执行后置通知");
    System.out.println("log get: "+res);
}
```

```java
@Test
public void test() throws Exception {
    String path = "applicationContext1.xml";
    ApplicationContext context = new ClassPathXmlApplicationContext(path);
    SomeService proxy = (SomeService) context.getBean("server1");
    String s = proxy.doOther();
    System.out.println("doOther return: "+s);
}
```

`@AfterReturning`需要有两个值

1. value切入点表达式
2. returning：自定义的变量，表示目标方法的返回值的

returning就是把目标方法的返回值，传递给这个参数

![image-20210521082744828](http://ooszy.cco.vin/img/blog-note/image-20210521082744828.png?x-oss-process=style/pictureProcess1)

在执行后置通知的时候，能够通过res拿到目标方法的返回值，如果log()方法中，因为切面功能代码中的参数，都不是自定义的，所以这里，不能有多个参数，否则会报错

这个参数的类型，也不一定要Object，只需要和目标方法的返回类型对应就可，所以上面，也可以写成

```java
@AfterReturning(value = "execution( * vin.cco.a2.SomeServiceImpl.doOther())",
                returning = "res")
public void log(String res) {
    System.out.println("执行后置通知");
    System.out.println("log get: "+res);
}
```

但是，如果目标方法的返回值是String，但是我们使用int进行接收，那么不会报错，但是这个切面不会执行，其还是一个代理对象，因为debug，其还是进入到

![image-20210521083641181](http://ooszy.cco.vin/img/blog-note/image-20210521083641181.png?x-oss-process=style/pictureProcess1)

只是切面中的方法，不会执行，猜测应该是，返回值类型不匹配，就不执行了，但是不会报错

后置通知，不会影响功能代码的执行结果，加入在切面中，返回一个值，不会影响目标方法的结果

```java
@AfterReturning(value = "execution( * vin.cco.a2.SomeServiceImpl.doOther())",
                returning = "res")
public String log(String res) {
    System.out.println("执行后置通知");
    System.out.println("log get: "+res);
    return "初尘";
}
```

不会影响，但是如果目标方法的返回值，是一个对象，比如student，那么修改这个对象的值，目标方法的执行结果会改变，只是return不会改变其目标方法的结果



- 如果返回的是一个对象，修改这个对象的值，是否会改变？

```java
@Override
public Student doStudent () {
    System.out.println("执行student: ");
    Student student = new Student();
    student.setAge(21);
    student.setName("青衫烟雨客");
    return student;
}
```

```java
@AfterReturning(value = "execution( * vin.cco.a2.SomeServiceImpl.doStudent())",
                returning = "res")
public String log(Student res) {
    System.out.println("执行后置通知");
    System.out.println("log get: "+res);

    res.setName("chuchen");
    return "初尘";
}
```

结果

```java
执行student: 
执行后置通知
log get: Student{name='青衫烟雨客', age=21}
-----------------
Student{name='chuchen', age=21}
```

返回结果被改变了，但是对于，String这种，其不会改变，因为String变量存储在常量池中

但是对于引用类型，目标方法的结果会被改变



### 后置通知使用JoinPoint

`一定要位于第一位`

```java
public Student log(JoinPoint jp,Student res) {}
```





## 环绕通知

环绕通知的功能非常的强大，也是最常用的一个通知，其可以在方法的前后进行执行，也就相当于jdk动态代理中的执行过程

`环绕通知能够控制方法的执行，影响方法执行的结果`

定义功能代码

```java
@Around("execution(* vin.cco.a3.SomeServiceImpl.doSome(..))")
public String log(ProceedingJoinPoint point) {

    System.out.println("日志打印: "+new Date());
    try {
        point.proceed();
    } catch (Throwable throwable) {
        throwable.printStackTrace();
    }

    System.out.println("执行事务提交");
    return "sd";
}
```

方法定义格式

> 1. public
> 2. 必须有一个返回值，推荐使用object
> 3. 方法名称自定义
> 4. 方法有参数，固定的参数 ProceedingJoinPoint

因为环绕通知能影响执行结果，就是因为环绕通知中的返回值，进行影响的，所以如果返回值类型为void的话，那么执行一个返回字符串类型的方法，那么最终目标方法的执行结果为null

`参数必须使用固定的这个类型`，如果想要在环绕通知中，使用JoinPoint，那么不用参数中加上，可以直接使用参数对象，因为ProceedingJoinPoint就已经继承了

```java
public interface ProceedingJoinPoint extends JoinPoint {}
```



`point.proceed();`就相当于执行目标方法，所以如果在环绕通知中，没有`point.proceed()`的话，那么目标方法不会执行，这个代码等同于jdk动态代理中的`method.invoke()`

`环绕通知，等同于jdk动态代理的，InvocationHandler接口`



###  影响目标方法执行结果

就是通过环绕通知的返回值进行控制的

## 异常通知

异常通知就是在发生异常的时候，执行的功能代码，其执行的原理，就是在目标方法上，加上一个

```java
try {}catch(Expection e) {}
```

异常通知通常应用在，发生异常的时候，发送邮件等



方法定义

> 1. 使用public修饰
> 2. 没有返回值
> 3. 方法参数，`public void log(JoinPoint jp, Exception e) {}`
> 4. 注解上，除了表达式之外，还需要`throwing = "e"`值就是参数中Exception对象值



```java
@AfterThrowing(value = "execution(* vin.cco.a4.SomeServiceImpl.doOther(..))",throwing = "e")
public void log(JoinPoint jp, Exception e) {
    System.out.println("执行异常通知");
    System.out.println(e.getMessage());
}
```

当我们在目标方法中，触发异常的时候，其就会执行

```java
public String doOther(int a) {
    System.out.println("执行其他的方法"+(1/0));
    return "chuchenblog";
}
```





##  最终通知

最终通知总是会被执行，其相当于

```java
try {}catch(Exception e){}finally{}
```

中的finally，尽管发生异常，也是会执行

方法定义

> 1. public修饰
> 2. 没有返回值
> 3. 没有方法参数，如果需要的话，就只能是JoinPoint



```java
@After("execution(* vin.cco.a4.SomeServiceImpl.doOther(..))")
public void fin(JoinPoint jp) {}
```







## @Pointcut注解

可能在一个类中，存在多个相同的切入点表达式，我们可以将这些相同的表达式提取出来，这样使用就会变得非常的方便，就需要使用到`@Pointcut`注解

定义步骤

1. 定义一个没有返回值的方法，此方法中，不需要添加任何的代码块
2. 加上`@Pointcut`注解，注解中的value值就是那些相同的切入点表达式
3. 在功能代码上，其value值，使用这个定义的方法，需要加上括号就可以了

```java
@Pointcut("execution( * vin.cco.a2.SomeServiceImpl.doOther())")
public void myPt() {}
```

```java
@AfterReturning(value = "myPt()",returning = "res")
public String log(String res) {
    System.out.println("执行后置通知");
    System.out.println("log get: "+res);
    res = "sdf";
    return "初尘";
}
```





## 没有接口的代理情况

如果存在接口的话，那么其代理模式为jdk的动态代理，但是如果没有接口，那么其代理模式为`CGLIB`，这是spring框架自动选择的，也可以通过配置文件进行更改



### 如果查看代理模式

```java
SomeServiceImpl proxy = (SomeServiceImpl) context.getBean("server1");
System.out.println(proxy.getClass());
```

通过打印这个class，就可以看到

```java
class vin.cco.a5.SomeServiceImpl$$EnhancerBySpringCGLIB$$e8a84430
```

这个就是通过`CGLIB`进行代理的

```java
class com.sun.proxy.$Proxy8
```

这个就是jdk的代理模式



如果程序中，没有接口的话，那么spring就会选择通过cglib进行代理



### 存在接口，但是需要使用cglib代理

通过更改配置文件`<aop:aspectj-autoproxy proxy-target-class="true" />`在这个标签中，加上true就表示使用spring的cglib代理进行

所以如果这个标签这样写的话，尽管存在接口，那么其代理模式也是cglib，改为false就是jdk的动态代理

