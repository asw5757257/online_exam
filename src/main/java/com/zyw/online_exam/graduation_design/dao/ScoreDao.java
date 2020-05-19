package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Score;
import com.zyw.online_exam.graduation_design.pojo.ScoreDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 12:55
 */
public interface ScoreDao extends JpaRepository<Score,Integer> {
    List<Score> findByStudentIdAndPaperId(Integer sid,Integer pid);

    List<Score> findAllByStudentId(Integer sid);
    @Query(value = "SELECT new com.zyw.online_exam.graduation_design.pojo.ScoreDetail(a.name,a.stuNum,b.score,c.name)" +
            " from Student AS a,Score as b,Paper as c" +
            " WHERE b.studentId=a.id AND b.paperId=c.id")
    List<ScoreDetail> findAllScore();

    int countAllByStudentId(Integer sid);
}
