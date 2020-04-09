package com.zyw.online_exam.graduation_design.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/9 11:09
 */
@Getter
@Setter
@ToString
@Entity
@Table(name="depository")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Depository {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer subjectId;

}
