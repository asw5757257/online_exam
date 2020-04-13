package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.PaperAndMajor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 16:21
 */
public interface PaperAndMajorDao extends JpaRepository<PaperAndMajor,Integer> {
    PaperAndMajor findAllByPaperIdAndMajorId(Integer pid,Integer mid);
    List<PaperAndMajor> findAllByMajorId(Integer id);
}
