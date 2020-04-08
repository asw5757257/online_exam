package com.zyw.online_exam.graduation_design.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/8 16:51
 */
@Getter
@Setter
@ToString
@Entity
@Table(name="subject")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Subject {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
