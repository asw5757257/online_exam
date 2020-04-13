package com.zyw.online_exam.graduation_design.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 11:43
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="score")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@EntityListeners(AuditingEntityListener.class)
public class Score {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer studentId;

    private Integer paperId;

    private String score;

    private String flag;

    private Date finishTime;



}
