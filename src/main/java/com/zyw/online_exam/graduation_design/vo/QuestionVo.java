package com.zyw.online_exam.graduation_design.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/9 19:24
 */
@Getter
@Setter
@ToString
public class QuestionVo {
    private Integer id;

    private String subject;

    private String title;

    private String content;

    private String answer;

    private String type;

    private String flag;

    private String createdBy;

    private String lastUpdatedTime;

}
