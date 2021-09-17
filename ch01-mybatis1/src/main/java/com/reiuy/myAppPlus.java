package com.reiuy;

import com.reiuy.domain.Student;
import com.reiuy.utils.MybatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 这是一个测试类
 */
public class myAppPlus {
    public static void main(String[] args) throws IOException {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //调用工具类进行sqlSession对象的获得

        String sqlId = "com.reiuy.dao.StudentDao.selectStudents";
        //通过sqlID找到sql语句，执行sql语句
        List<Student> studentList = sqlSession.selectList(sqlId);
        //输出结果
        for (Student stu:studentList){
            System.out.println("查询的学生："+stu);
        }

        //关闭sqlSession对象
        sqlSession.close();
    }
}
