package com.reiuy.dao;


import com.reiuy.entity.Student;

import java.util.List;

public interface StudentDao {

    //使用动态sql时，参数需要是java对象
    //使用if动态sql
    List<Student> selectStudentIf(Student student);


    //使用where动态sql
    List<Student> selectStudentWhere(Student student);

    //使用foreach 用法1，参数为基本数据类型
    List<Student> selectStudentForeach1(List<Integer> idlist);

    //使用foreach 用法2,参数为对象
    List<Student> selectStudentForeach2(List<Student> studentListlist);

    //测试插件pagehelper分页数据
    List<Student> selectAllwithph();



}





