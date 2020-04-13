package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/13 11:02
 */
public interface ManagerDao extends JpaRepository<Manager,Integer> {
    Manager findAllById(Integer id);

    Manager findAllByUsername(String username);

    Manager findAllByUsernameAndPassword(String username,String password);
}
