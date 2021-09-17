package com.reiuy;

import com.reiuy.dao.StudentDao;
import com.reiuy.entity.Student;
import com.reiuy.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class TestMybatis {
    @Test
    public void testSelectStudents(){
        /**
         * 使用mybatis的动态代理机制，使用sqlSession.getMapper(dao接口)
         * getMapper 能获取dao接口对应的实现类对象
         */
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);
        System.out.println("dao="+dao.getClass().getName());
        //我们可以看到 dao=com.sun.proxy.$Proxy7  说明利用了jdk动态代理机制
        //调用dao的方法来执行数据库的操作
        List<Student> students = dao.selectStudents();
        for (Student stu:students){
            System.out.println("学生"+stu);
        }


    }


    @Test
    public  void testInsertStudent(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);
        Student student = new Student();
        student.setId(1007);
        student.setName("牛犇");
        student.setEmail("niuben@qq.com");
        student.setAge(21);


        int num = dao.insertStudent(student);
        sqlSession.commit();
        //手动提交
        System.out.println("添加了"+num+"条新纪录");
    }

}
