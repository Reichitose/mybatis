package com.reiuy;

import com.reiuy.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMybatis {
    //测试方法，测试功能
    @Test
    public void testInsert() throws IOException {
        //访问mybatis读取student数据,上面六步较为固定，只会替换第六部分的标签id
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
        String sqlId = "com.reiuy.dao.StudentDao.insertStudent";
        //7.创建被插入的对象，通过sqlID找到sql语句，执行sql语句
        Student student = new Student();
        student.setId(1005);
        student.setName("马哲");
        student.setEmail("mzsb@163.com");
        student.setAge(20);
        int nums = sqlSession.insert(sqlId,student);

        //此处数据库并未发生变化，原因是mybatis不是自动提交事务的，所以在insert，update，delete后，需要手动提交事务
        sqlSession.commit();

        //8.输出结果
        System.out.println("执行insert的结果："+nums);
        //9.关闭sqlSession对象
        sqlSession.close();
    }
}
