package com.zyw.online_exam.graduation_design.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 11:51
 */
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="paper_and_major")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@EntityListeners(AuditingEntityListener.class)
public class PaperAndMajor {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer paperId;

    private Integer majorId;

    private Date publishTime;
}
