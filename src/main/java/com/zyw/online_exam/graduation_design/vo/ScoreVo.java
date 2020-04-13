package com.zyw.online_exam.graduation_design.vo;

import lombok.*;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/12 17:21
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScoreVo {
    private String studentName;

    private String studentId;

    private String major;

    private String paperName;

    private String score;
}
