package com.reiuy;

import com.reiuy.dao.StudentDao;
import com.reiuy.dao.impl.StudentDaoImpl;
import com.reiuy.entity.Student;
import org.junit.Test;

import java.util.List;

public class TestMybatis {
    @Test
    public void testSelectStudents(){
        StudentDao dao = new StudentDaoImpl();
        List<Student> studentList = dao.selectStudents();
        for (Student stu:studentList){
            System.out.println(stu);

        }
    }
    @Test
    public void  testinsertStudent(){
        StudentDao dao = new StudentDaoImpl();
        Student student = new Student();
        student.setId(1006);
        student.setName("王五");
        student.setEmail("wangwu@qq.com");
        student.setAge(20);
        int num = dao.insertStudent(student);
        System.out.println("添加了"+num+"个新对象");
    }
}
