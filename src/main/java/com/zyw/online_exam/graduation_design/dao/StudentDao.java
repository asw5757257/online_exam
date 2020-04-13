package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 12:54
 */
public interface StudentDao extends JpaRepository<Student,Integer> {
    Student save(Student student);
    Student findAllById(Integer id);
    Student findAllByUsername(String username);

    Student findAllByUsernameAndPassword(String username,String password);

    Student findAllByPasswordAndId(String password,Integer id);
    int deleteAllById(Integer id);
    Page<Student> findAllByNameOrUsernameOrStuNumOrMajorId(Pageable pageable, String name, String username,String stuId,Integer mid);
}
