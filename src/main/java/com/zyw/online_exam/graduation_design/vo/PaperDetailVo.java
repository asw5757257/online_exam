package com.zyw.online_exam.graduation_design.vo;

import lombok.*;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 17:06
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaperDetailVo {
    private Integer id;

    private String name;

    private String createdBy;

    private String score;
    /**
     * 选择题VO
     */
    private List<ChoiceQuestionVO> choiceQuestion;
}
