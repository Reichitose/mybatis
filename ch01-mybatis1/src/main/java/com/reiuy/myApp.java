package com.reiuy;

import com.reiuy.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.List;

/**
 * 这是一个测试类
 */
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
        String sqlId = "com.reiuy.dao.StudentDao.selectStudents";
        //7.通过sqlID找到sql语句，执行sql语句
        List<Student> studentList = sqlSession.selectList(sqlId);
        //8.输出结果
        for (Student stu:studentList){
            System.out.println("查询的学生："+stu);
        }
        //studentList.forEach(stu -> System.out.println(stu) );
        //此处是一个lambda表达式
        //后半段实际的内容类似于
        //设定了一个匿名函数，参数为stu，函数下执行sout(stu)
        //虽然elegant但是对修改要求很高，尽量不使用(?),所以我们用for实现
        //9.关闭sqlSession对象
        sqlSession.close();
    }
}