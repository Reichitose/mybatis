package com.reiuy.dao;

import com.reiuy.domain.Student;

import java.util.List;

//这是一个接口，操作student表
public interface StudentDao {

    //查询student表的所有数据
    public List<Student> selectStudents();

    //插入数据方法
    //参数：student，表示要插入到数据库的数据，是个对象
    //返回值 int 表示执行insert操作之后，影响的数据库行数，与jdbc一致
    public int insertStudent(Student student);

}
