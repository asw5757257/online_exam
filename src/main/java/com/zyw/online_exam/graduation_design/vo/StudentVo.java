package com.zyw.online_exam.graduation_design.vo;

import lombok.*;

import java.util.Date;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/12 10:19
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentVo {
    private Integer id;

    private String username;

    private String name;

    private String studentId;

    private String majorId;

    private String major;

    private String grade;

    private String email;

    private String tel;

    private String sex;

    private Date createdTime;

    private Date lastUpdatedTime;
}
