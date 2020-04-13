package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Paper;
import com.zyw.online_exam.graduation_design.pojo.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/9 20:03
 */
public interface PaperDao extends JpaRepository<Paper,Integer> {
    Paper findAllById(Integer id);

    Paper findAllByName(String name);


}
