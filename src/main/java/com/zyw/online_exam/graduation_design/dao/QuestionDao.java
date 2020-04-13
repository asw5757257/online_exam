package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/9 16:18
 */
public interface QuestionDao extends JpaRepository<Question,Integer> {
    Question findAllById(Integer id);
    void deleteById(Integer id);
    int countAllBySubjectAndType(String subject,String type);
    @Query(value = "SELECT * FROM question" +
            " where type=?1" +
            " and subject=?2"+
            " order by rand() limit ?3", nativeQuery = true)
    List<Question> randomFind(String type,String subject,Integer optionNum);

    Question findAllByIdAndAnswer(Integer id,String answer);



}
