package com.reiuy.dao;

import com.reiuy.entity.Student;
import com.reiuy.vo.QueryParam;
import org.apache.ibatis.annotations.Param;

import javax.print.DocFlavor;
import java.util.List;

public interface StudentDao {
    /**
     *  一个简单类型的参数
     *  简单类型：mybatis把java的基本数据类型和字符串都叫简单类型
     *  在mapper文件中要获取简单类型 的一个参数值，使用#{任意字符}
     *  使用#{} 之后，mybatis执行sql是使用的jdbc中的prepareStatement对象
     *  由mybatis创建Connection，PrepareStatement对象
     *  遇到#{}用占位符?
     *  然后通过set方法把参数填入占位符
     *  执行sql并把结果封装为resultType对象
     *  并返回
     */
    public Student selectStudentById(Integer id);


    /***
     * 多个参数：命名参数，在形参定义前加入注解@param("自定义参数名")
     */

    List<Student> selectMulitParam(@Param("myname") String name, @Param("myage")Integer age);


    /**
     * 多个参数，我们采用java对象作为接口中方法的参数
     */
    List<Student> selectMultiObject(QueryParam param);
    //这里指定的对象,可以是任何存在的java对象
    //也就是说，这里使用entity下的Student也是完全可以的
    //只是利用属性名称作为参数，不一定需要额外定义专门的对象
    //灵活应用

    List<Student> selectStudentsuse$(@Param("colName") String colName);
    //依据列名排序


    /**
     *
     * 使用ResultMap来定义映射关系
     */

    List<Student> selectAllStus();

    /**
     * 实现模糊查询，第一种
     * 在java代码中指定like的内容
     */
    List<Student> selecLikeOne(String name);
    /**
     * 实现模糊查询，第二种
     * 直接将字符作为参数传入，在mapper文件中拼接%和_
     */
    List<Student> selecLikeTwo(String name);



}





