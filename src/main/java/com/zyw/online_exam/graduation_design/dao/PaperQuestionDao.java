package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.PaperQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 16:21
 */
public interface PaperQuestionDao extends JpaRepository<PaperQuestion,Integer> {
    List<PaperQuestion> findAllByPaperId(Integer pid);
    PaperQuestion findAllByPaperIdAndQuestionId(Integer pid,Integer qid);
    int deleteByPaperIdAndQuestionId(Integer pid,Integer qid);
    int deleteAllByPaperId(Integer pid);
}
