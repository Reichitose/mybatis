# 框架（framework）引言

> 框架是整个或部分系统的可重用设计，表现为一组抽象构件及构件实例间交互的方法，框架是可被开发者定制的骨架，模板，也是一个半成品的软件,是可复用的，可升级的

## 1.三层架构

### 界面层(controller包-----servlet)

和用户直接打交道，接收参数，显示结果（jsp，HTML，servlet）

### 业务逻辑层(service包-----XXXService类)

接收了用户的请求参数，计算逻辑，调用数据库，获取数据

### 数据访问层(dao包-------XXXDao类)

访问数据库，执行查询修改删除等工作的

### 三层中类的交互

用户-->界面层--->业务逻辑层--->数据访问层（持久层）--->数据库

### 三层对应的处理框架

界面层---servlet----springmvc

业务逻辑层----service类-----spring

数据访问层-----Dao类-------mybatis

## 2.框架

### 概念

是一个舞台，也是一个模板（规定了一些规范）

### 框架是一个模块

定义好了一些可用的功能，可以加入项目中自己的功能，这些功能也可以利用框架中已经写好的内容

### 框架特点

1.框架不是全能的，它不能做所有事情

2.框架是针对某一个领域有效的，例如mybatis是针对数据库的，但是并不能做其他的

3.框架是一个写好了功能的软件



# mybatis框架

## 1.传统JDBC的缺陷

1.代码多，效率低

2.需要关注对象的创建销毁

3.result set需要自行封装list

4.大量的重复代码

5.业务代码会和数据库操作混在一起

我们通过JDBCUtil试图解决，但仍然不能完全解决

## 2.mybatis框架概述

是一个基于java的持久层框架

全名Mybatis SQL Mapper Framework for Java

是一个sql映射框架，提供对数据库的操作能力

可以看做是JDBC的威力加强版

## 3.作用

### sql mapper sql映射

可以把数据库表中的一行数据，映射为一个java对象

一行数据可以看做是一个对象，操作这个对象就等同于操作表中的数据

#### Data Access Object（DAOs）数据访问

对数据库进行CRUD

## 4.提供的功能

1.提供了创建Connection，Statement ResultSet的能力，不许需要开发人员创建

2.提供了执行sql语句的能力

3.提供了循环sql，将sql的结果转换为java对象和list集合的能力 例如JDBC的while(rs.next()){}

4.提供了关闭资源的能力，不需要关闭Connection，Statement ResultSet

使得开发人员能够集中到sql语句的编写和开发上，不用关心过多的细节

只需要提供sql语句，mybatis就会返回代表表中数据的list或java对象，开发人员直接进行处理



## 5.使用的步骤

### 1.加入maven依赖

```xml
<!--mybatis依赖-->
<dependency>
  <groupId>org.mybatis</groupId>
  <artifactId>mybatis</artifactId>
  <version>3.5.7</version>
</dependency>

<!--mysql驱动-->
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.26</version>
</dependency>
```

### 2.创建Dao接口，定义操作数据的方法

### 3.创建mapper文件，也叫做sql映射文件

跟dao接口同名的xml文件，其中有需要执行的sql语句

### 4.创建mybatis的一个主配置文件

在resource文件夹中，名称为mybatis.xml

​	1.连接数据库

​	2.指定mapper文件的位置

### 5.用mybatis的SqlSession对象，通过它的方法执行sql语句，访问数据库、

我们会使用封装好的工具类实现大量重复的步骤

```java
public class myApp {
    public static void main(String[] args) throws IOException {
        //访问mybatis读取student数据
        //1.定义mybatis主配置文件的名称，从类路径的根开始
        String config = "mybatis.xml";
        //2.读取这个config表示的文件
        InputStream in = Resources.getResourceAsStream(config);
        //3.创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //4.创建sqlSessionFactory对象
        SqlSessionFactory factory = builder.build(in);
        //【重点】5.获取SqLSession对象，从SqlSessionFactory中获取SqlSession
        SqlSession sqlSession = factory.openSession();
        //6.指定要执行的sql语句的表示，sql映射文件中的namespace+"."+标签id
        String sqlId = "com.reiuy.dao.StudentDao"+"."+"selectStudent";
        //7.通过sqlID找到sql语句，执行sql语句
        List<Student> studentList = sqlSession.selectList(sqlId);
        //8.输出结果
        for (Student stu:studentList){
            System.out.println("查询的学生："+stu);
        }
        sqlSession.close();
 ｝
```

工具类代码如下

```java
package com.reiuy.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtils {

    private static SqlSessionFactory factory = null;

    static {
        String config = "mybatis.xml";//需要和你的项目中的文件名一致
        try {
            InputStream in  = Resources.getResourceAsStream(config);
            //创建SqlSession对象，使用SqlSessionBuilder
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            factory = builder.build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取SqlSession方法
    public static SqlSession getSqlSession(){
        SqlSession sqlSession = null;
        if (factory != null){
            sqlSession = factory.openSession();//非自动提交事务
        }
        return sqlSession;

    }
}
```





## 6.几个主要类

### 1.Resource 

是mybatis中的一个类，负责读取主配置文件

```java
//读取这个config表示的文件
InputStream in = Resources.getResourceAsStream(config);
```

### 2.SqlSessionFactoryBuilder

作用是用来创建SqlSessionFactory对象

```java
 //创建SqlSessionFactoryBuilder对象
 SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
 //创建sqlSessionFactory对象
SqlSessionFactory factory = builder.build(in);
```

### 3.SqlSessionFactory（重量级对象）

程序创建这个对象耗时比较长，使用资源比较多，所以称之为重量级，整个项目中有一个就够用了

SqlSessionFactory是一个接口，接口的实现类是 DefaultSqlSessionFactory

SqlSessionFactory作用是获取SqlSession对象

```java
//【重点】获取SqLSession对象，从SqlSessionFactory中获取SqlSession
SqlSession sqlSession = factory.openSession();
```

openSession方法的说明：

1.openSession()  无参数  获取非自动提交事务的SqlSession对象

2.openSession(boolean)  参数为true时，会获取一个自动提交事务的SqlSession对象，此时就不																	需要手动提交了

​										如果参数为false时，会获取一个非自动提交事务的SqlSession对象

### 4.SqlSession(操作数据库的核心)

是接口，接口中定义了操作数据库的方法例如selectOne(),selectList,insert(),delete(),commit(),rollback;

SqlSession接口的实现类DefaultSqlSession

使用要求：SqlSession对象并不是线程安全的，需要在方法内部使用，要先使用openSession()获取，执行完sql语句需要关闭它，执行SqlSession.close()，这样才能保证他的线程安全



## 7.mybatis动态代理

### 分析利用dao的mybatis实现

我们可以发现

1.dao对象，类型是XXXDao，全限定名称是：com.reiuy.dao.xxxDao

全限定名称和namespace是一样的

2.方法名称。selectStudents这个方法就是mapper文件中的id值

3.通过dao方法中的返回值也可以确定mybatis要调用的sqlSession方法

如果返回值是list，则调用 的是sqlSession.selectList()方法

如果是int则是非select的，可以通过标签确定是insert update和delete



### Mybatis的动态代理

mybatis根据dao的方法调用，获取执行sql语句的信息

根据你的dao接口，创建出一个dao接口的实现类，并创建这个类的对象来完成sqlSession调用方法来访问数据库

### 写法

```
XXXDao dao = sqlSession.getMapper(XXXDao.class)
//这样，我们就通过getMapper方法创建了一个dao的对象，并可以在之后直接调用这个类进行操作
//例如.直接调用我们在StudentDao中规定的方法，把实现交给mybatis
List<Student> students = dao.selectsStudents();
```

### 流程

#### 1.动态代理

使用SqlSesion.getMapper(dao接口.class)获取这个dao接口的对象

#### 2.传入参数 

从java代码中把数据传入到mapper文件的sql语句中。

##### parameterType 

写在mapper文件中的一个属性，表示dao接口中方法的参数的数据类型

##### 一个简单类型的参数

简单类型：mybatis把java的基本数据类型和字符串都叫简单类型
在mapper文件中要获取简单类型 的一个参数值，使用#{任意字符}

接口

```
public Student selectStudentById(Integer id);
```

mapper：

```
SELECT * FROM student WHERE id=#{id}
```

##### 多个参数@param命名参数

接口

```
public List<Student> selectMulitParam(@param("myName")Stringname,@param("myAge")Integer age)
```

使用@param("参数名") String name

mapper文件

```
<select>
	select * from student where name=#{myName} or age=#{myAge}
</select>
```

##### 对象作为参数

#{属性名}

##### 按位置）（易出错）

mybatis 3.4之后 使用#{arg0},#{arg1}用来表示第一个和第二个参数。按顺序





##### 使用map（可读性差）

方法的参数是一个map

占位符采用#{key}

利用map的key获得value值

#### 3.输出结果

mybatis执行了sql语句，得到Java对象

##### resultType

结果类型值，值有两种，一种是值的全限定名称，一种是别名

例如java.lang.Integer 也可以用别名int

建议使用全限定名称

返回类型也可以是自定义的某个对象，或者某个基本类型

定义类型的别名

在mybatis的主配置文件中进行定义，使用<typeAlias>定义别名，就可以在resultType中使用了

sql语句执行完毕后，数据转为的数据对象，可以看作是在dao中定义方法时所用的返回值，这个java类型是任意的



返回时map的时候，最多只能返回一行数据，多于一行必报错

使用推荐使用返回对象



处理方式

1.mybatis执行sql语句，然后mybatis调用类的无参数构造方法 创建对象

2.mybatis把ResultSet指定列值赋给同名的属性



##### ResultMap

结果映射，用来指定列名和java对象的属性对应关系

用法

1.自定义列的值赋给哪个属性，默认情况下列名是赋给同名属性的

2.当你的列名和属性名不一样时，使用ResultMap，也可以通过sql语句中的as别名来实现

#### 4.Like模糊查询

##### 第一种方式

用like #{like_name} 将在java代码中定义好的模糊查询传入sql语句

```
String like_name = "%李%";
List<Student> students = dao.selecLikeOne(like_name);
```



##### 第二种方式

在mapper文件中拼接

更改模糊条件时需要修改两个变量，这种办法很蠢，极度不建议使用





### 占位#和$

#

#在执行时代替？

使用prepareStatement

$

$在执行时会被作为字符串进行拼接

使用Statement，比使用prepareStatement效率低，且不能防止sql注入

通常不建议使用

但$仍有其用处，可以替换列名和表名

例如

select * from student order by ${colName}

或在能完全确定传入的数据是安全的时，使用$





## 8.动态sql

sql的内容是变化的，可以根据条件获取到不同的sql语句，主要是where部分

动态sql的实现，使用的是mybatis提供的标签

主要是<if>,<where>,<foreach>

### 1. < if >是判断条件 的

语法

```
<if test = "判断java对象的属性值">
  SQL语句
</if>
```

### 2.< where >是用来包含if 的

包含了多个if，当if中有一个成立的，where会自动添加一个where关键字。并去除if中影响语法的字符

### 3.< foreach >循环java中 的数组，list集合的，主要用在in语句中

例如select * from student where id in (1001,1002,1003)

```
<foreach collection="" item="" open="" close="" separator="">
    
</foreach>
```

collection 表示接口中方法参数的类型，数组使用array list集合使用list

 item 自定义的，表示数组和集合成员的变量

open 循环开始时的字符

 close 循环结束时的字符

separator 成员之间的分割字符

## 9.sql代码片段

复用一些sql语句或不完全的sql语句

1.先定义<sql id = "自定义唯一名称"> sql语句，表名列名字段</sql>

2.再使用<include refid="自定义的名称"/>

相当于使用了定义的语句

## 10.数据库属性配置文件

将数据库连接信息放到一个单独的文件中，和mybatis主配置文件分开

目的是便于修改保存处理多个数据库的信息

1.在resources目录中定义一个属性配置文件，xxx.properties

在该文件中定义数据，格式是key=value

key一般是使用 . 做多级分隔，做多级目录的

value就是配置

2.在mybatis的主配置文件中，使用property标签来指定文件的位置，需要使用值的地方，用${key}

推荐这样进行管理



## 11.通过package导入多个mapper

在mappers标签下

```
<!--
利用包名指定多个mapper文件
name：mapper文件xml文件所在的包名
这个包中所有的xml文件一次全部加载给mybatis
使用package的要求
1.mapper文件名称需要和接口名称一样，大小写严格
2.mapper文件和dao的接口需要在统一目录（dao和dao的xml在一个目录下）
-->
<package name="com.reiuy.dao"/>
```



## 12.pagehelper

帮助分页的，添加依赖然后使用

pom依赖如下

```
<!--添加pagehelper的依赖-->
 <dependency>
   <groupId>com.github.pagehelper</groupId>
   <artifactId>pagehelper</artifactId>
   <version>5.2.1</version>
 </dependency>
```

在总配置文件里添加插件

```
<!--添加pagehelper插件-->
<plugins>
    <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
</plugins>
加在环境标签之前
```



## 简易命名规则

vo：value object 放一些用于储存数据的类，比如说提交请求参数

vo：view object 从servlet把数据返回给浏览器所使用的类，表示显示结果

pojo 普通的有set get方法的java类，普通的java对象

entity：实体类，和数据库中的表相对应
