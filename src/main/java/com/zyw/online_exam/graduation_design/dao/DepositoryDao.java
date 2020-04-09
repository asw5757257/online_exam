package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Depository;
import com.zyw.online_exam.graduation_design.pojo.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/9 11:18
 */
public interface DepositoryDao extends JpaRepository<Depository,Integer> {
    Depository findAllByName(String name);
    void deleteByName(String name);
    List<Depository> findAllBySubjectId(Integer sid);
    Page<Depository> findAllBySubjectId(Pageable pageable,Integer sid);
}
