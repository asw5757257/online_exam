package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Paper;
import com.zyw.online_exam.graduation_design.pojo.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/9 20:03
 */
public interface PaperDao extends JpaRepository<Paper,Integer> {
    Paper findAllById(Integer id);
    Page<Paper> findAllByNameLike(Pageable pageable, String name);
    int countAllByNameLike(String name);
    Paper findAllByName(String name);


}
