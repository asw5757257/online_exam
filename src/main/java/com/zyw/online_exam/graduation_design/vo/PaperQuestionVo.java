package com.zyw.online_exam.graduation_design.vo;

import lombok.*;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/11 13:12
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaperQuestionVo {
    private Integer id;

    private String testTitle;

    private String score;

    private String priority;

}
