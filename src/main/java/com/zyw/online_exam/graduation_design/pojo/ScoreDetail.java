package com.zyw.online_exam.graduation_design.pojo;

import lombok.*;

import javax.persistence.Entity;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/15 15:46
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDetail {
    private String name;

    private String stu_num;

    private String score;

    private String paperName;
}
