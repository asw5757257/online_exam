package com.zyw.online_exam.graduation_design.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 11:17
 */
@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="student")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@EntityListeners(AuditingEntityListener.class)
public class Student {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer majorId;

    private String stuNum;

    private String name;

    private String username;

    private String password;

    private String sex;

    private String tel;

    private String email;

    private String role;


    private Integer createdBy;

    private Integer lastUpdatedBy;
    @CreatedDate
    @Column(name = "createTime",updatable = false,nullable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "updateTime",nullable = false)
    private Date updateTime;
}
