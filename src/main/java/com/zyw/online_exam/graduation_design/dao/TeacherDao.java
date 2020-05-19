package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Question;
import com.zyw.online_exam.graduation_design.pojo.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 11:42
 */
public interface TeacherDao extends JpaRepository<Teacher,Integer> {
    Teacher findAllByUsername(String username);

    Teacher findAllByUsernameAndPassword(String username,String pasword);

    Teacher findAllByPasswordAndId(String password,Integer id);

    Teacher findAllById(Integer id);

    int deleteAllById(Integer id);
    Teacher findAllByEmail(String email);
    Page<Teacher> findAllByNameLike(Pageable pageable,String name);
    int countAllByNameLike(String name);
    Page<Teacher> findAll(Pageable pageable);
    int countAllBy();
}
