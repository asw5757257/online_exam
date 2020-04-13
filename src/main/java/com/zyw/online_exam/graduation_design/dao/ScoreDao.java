package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 12:55
 */
public interface ScoreDao extends JpaRepository<Score,Integer> {
    Score findAllByStudentIdAndPaperId(Integer sid,Integer pid);

    List<Score> findAllByStudentId(Integer sid);
}
