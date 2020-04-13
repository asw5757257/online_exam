package com.zyw.online_exam.graduation_design.vo;

import lombok.*;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 17:07
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceQuestionVO {
    private Integer id;

    private String type;

    private String subject;

    private String title;
    /**
     * 选项A
     */
    private String optionA;
    /**
     * 选项B
     */
    private String optionB;
    /**
     * 选项C
     */
    private String optionC;
    /**
     * 选项D
     */
    private String optionD;

    private String testAnswer;

    private String flag;

    private String score;

    private String createdBy;

    private String lastUpdatedTime;
}
