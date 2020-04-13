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
 * @date 2020/4/8 16:51
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="major")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@EntityListeners(AuditingEntityListener.class)
public class Major {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String grade;

    private Integer createdBy;

    private Integer lastUpdatedBy;

    @CreatedDate
    @Column(name = "createTime",updatable = false,nullable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(name = "updateTime",nullable = false)
    private Date updateTime;


}
