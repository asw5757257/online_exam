package com.zyw.online_exam.graduation_design.vo;

import com.zyw.online_exam.graduation_design.pojo.Major;
import lombok.*;

import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/13 13:09
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeacherAndMajorVo {
    private String name;

    private List<Major> majorList;
}
