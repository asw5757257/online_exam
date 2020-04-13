package com.zyw.online_exam.graduation_design.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/13 11:01
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="manager")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Manager {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String role;
}
