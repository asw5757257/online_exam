package com.zyw.online_exam.graduation_design.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 11:56
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="paper_question")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@EntityListeners(AuditingEntityListener.class)
public class PaperQuestion {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer paperId;

    private Integer questionId;

    private String score;

    private String type;

    private String priority;
}
