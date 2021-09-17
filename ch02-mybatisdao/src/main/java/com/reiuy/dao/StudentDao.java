package com.reiuy.dao;

import com.reiuy.entity.Student;

import java.util.List;

public interface StudentDao {
    List<Student> selectStudents();

    int insertStudent(Student student);
}
