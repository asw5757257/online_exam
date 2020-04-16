package com.zyw.online_exam.graduation_design.dao;

import com.zyw.online_exam.graduation_design.pojo.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/8 16:57
 */

public interface MajorDao extends JpaRepository<Major,Integer> {
    Major findAllByName(String name);

    Page<Major> findAll(Pageable pageable);
    void deleteByName(String name);
    Major findAllById(Integer id);
    Major save(Major major);
    Major findAllByNameAndGrade(String name,String grade);
    int deleteAllById(Integer id);
    List<Major> findAllByGrade(String grade);
    Page<Major> findAllById(Pageable p,Integer id);
    Page<Major> findAllByNameLikeAndGradeLike(Pageable pageable,String name,String grade);

}
