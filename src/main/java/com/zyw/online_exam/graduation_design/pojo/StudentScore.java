package com.zyw.online_exam.graduation_design.pojo;

import lombok.*;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 11:47
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentScore {
    private String name;

    private Integer Student_id;

    private int score;

    private String paper_name;
}
