package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Depository;
import com.zyw.online_exam.graduation_design.pojo.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/9 16:18
 */
public interface QuestionDao extends JpaRepository<Question,Integer> {
    Question findAllById(Integer id);

    Page<Question> findAllBySubjectId(Pageable pageable,Integer sid);

    Page<Question> findAllByDepositoryId(Pageable pageable,Integer did);

    void deleteById(Integer id);


}
