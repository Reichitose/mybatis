package com.reiuy.dao.impl;

import com.reiuy.dao.StudentDao;
import com.reiuy.entity.Student;
import com.reiuy.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;


public class StudentDaoImpl implements StudentDao {

    @Override
    public List<Student> selectStudents() {
        //获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        String sqlId = "com.reiuy.dao.StudentDao.selectStudents";
        //执行sql语句
        List<Student> students = sqlSession.selectList(sqlId);
        sqlSession.close();
        return students;
    }

    @Override
    public int insertStudent(Student student) {
        //获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        String sqlId = "com.reiuy.dao.StudentDao.insertStudent";
        //执行sql语句
        int nums = sqlSession.insert(sqlId,student);
        sqlSession.commit();
        sqlSession.close();
        return nums;
    }
}
