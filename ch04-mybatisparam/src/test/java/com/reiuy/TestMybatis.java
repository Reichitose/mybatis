package com.reiuy;

import com.reiuy.dao.StudentDao;
import com.reiuy.entity.Student;
import com.reiuy.utils.MybatisUtils;
import com.reiuy.vo.QueryParam;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class TestMybatis {
    @Test
    public void testSelectStudentById() {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);


        Student student = dao.selectStudentById(1002);
        System.out.println("Student= " + student);
    }


    @Test
    public void testSelectMultiParam() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);
        List<Student> students = dao.selectMulitParam("马哲", 20);
        for (Student stu : students) {
            System.out.println("学生=" + stu);
        }
        sqlSession.close();
    }

    @Test
    public void testSelectMultiObject() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);
        QueryParam queryParam = new QueryParam();
        queryParam.setParamName("牛犇");
        queryParam.setParamAge(20);

        List<Student> students = dao.selectMultiObject(queryParam);
        for (Student stu : students) {
            System.out.println("学生=" + stu);
        }
        sqlSession.close();

    }


    @Test
    public void testSelectStudentuse$4order() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);

        List<Student> students = dao.selectStudentsuse$("id");
        for (Student stu : students) {
            System.out.println("学生=" + stu);
        }
        sqlSession.close();
        //查询成功且语句为select * from student order by id

    }



    @Test
    //测试修改resultMap映射关系后的
    public void testStuALLMap() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);

        List<Student> students = dao.selectAllStus();
        for (Student stu : students) {
            System.out.println("学生=" + stu);
        }
        sqlSession.close();
        //查询成功且语句为select * from student order by id

    }
    @Test
    /**
     * 测试like模糊查询
     */
    public void testselecLikeOne() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        StudentDao dao = sqlSession.getMapper(StudentDao.class);


        String like_name = "%李%";
        List<Student> students = dao.selecLikeOne(like_name);
        for (Student stu : students) {
            System.out.println("学生=" + stu);
        }
        sqlSession.close();
        //查询成功且语句为select * from student order by id

    }
}

