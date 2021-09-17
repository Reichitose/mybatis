package com.reiuy;

import com.github.pagehelper.PageHelper;
import com.reiuy.dao.StudentDao;
import com.reiuy.entity.Student;
import com.reiuy.utils.MybatisUtils;
import com.reiuy.vo.QueryParam;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestMybatis {
    //测试if标签
    @Test
    public void testselectStudentIf() {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);

        Student student = new Student();
        student.setName("马哲");
        //student.setAge(18);


        List<Student> students = dao.selectStudentIf(student);
        for (Student stu:students){
            System.out.println("if"+stu);
        }

    }
    //测试where标签
    @Test
    public void testselectStudentWhere() {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);

        Student student = new Student();
        //student.setName("马哲");
        student.setAge(18);
         List<Student> students = dao.selectStudentWhere(student);
        for (Student stu:students){
            System.out.println("where"+stu);
        }

    }

    //测试foreach的第一种遍历
    @Test
    public void testselectStudentForeach1() {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);
        List<Integer> idlist = new ArrayList<>();
        idlist.add(1001);
        idlist.add(1002);
        idlist.add(1003);
        idlist.add(1004);


        List<Student> students = dao.selectStudentForeach1(idlist);
        for (Student stu:students){
            System.out.println("foreach=="+stu);
        }

    }



    //测试foreach的第二种遍历
    @Test
    public void testselectStudentForeach2() {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);

        List<Student> studentList = new ArrayList<>();
        Student s1 = new Student();
        s1.setId(1001);
        studentList.add(s1);

        s1 = new Student();
        s1.setId(1002);
        studentList.add(s1);


        List<Student> students = dao.selectStudentForeach2(studentList);
        for (Student stu:students){
            System.out.println("foreach=="+stu);
        }

    }

    @Test
    public void testselectAllwithph() {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);

        //加入pagehelper的方法 分页
        //pageNum 第几页，从1开始
        //pagesize，一页中几行数据
        //可以看到原理是先进行count，然后确定分几页，然后进行limit操作
        //会进行内部计算
        //注意这是插件，不是mybatis自带的功能
        PageHelper.startPage(2,3);


        List<Student> students = dao.selectAllwithph();
        for (Student stu:students){
            System.out.println("foreach=="+stu);
        }

    }


}

