package com.zyw.online_exam.graduation_design.vo;

import lombok.*;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/12 10:31
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentPaperVo {
    private Integer paperId;

    private String paperName;

    private String publicTime;
}
