package com.zyw.online_exam.graduation_design.service;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.dao.*;
import com.zyw.online_exam.graduation_design.pojo.*;
import com.zyw.online_exam.graduation_design.utils.BigDecimalUtil;
import com.zyw.online_exam.graduation_design.utils.Md5Util;
import com.zyw.online_exam.graduation_design.utils.TimeUtil;
import com.zyw.online_exam.graduation_design.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/12 9:52
 */
@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private PaperDao paperDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private PaperQuestionDao paperQuestionDao;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private TeacherAndMajorDao teacherAndMajorDao;
    @Autowired
    private PaperAndMajorDao paperAndMajorDao;
    @Autowired
    private MajorDao majorDao;
    @Autowired
    private ScoreDao scoreDao;
    //登录
    public Dto login(String username,String password){
        Student student;
        if (StringUtils.isAnyBlank(username, password)) {
            return Dto.getFailed("登录失败：请检查");
        }
        student = studentDao.findAllByUsername(username);
        if(student == null){
            return Dto.getFailed("登陆失败,用户名不存在");
        }
        String md5Password = Md5Util.md5EncodeUtf8(password);
        student = studentDao.findAllByUsernameAndPassword(username,md5Password);
        if(student == null){
            return Dto.getFailed("登录失败：密码不正确");
        }
        student.setPassword(StringUtils.EMPTY);
        return Dto.getSuccess("登陆成功",student);
    }
    //未登录时手机验证码登录和重置密码
    //todo
    //登陆过后修改密码
    public Dto resetStudentPassword(String newPwd, String oldPwd, Student student){
        Student stu;
        stu = studentDao.findAllByPasswordAndId(Md5Util.md5EncodeUtf8(oldPwd),student.getId());
        if(stu == null){
            return Dto.getFailed("旧密码错误");
        }
        stu.setPassword(Md5Util.md5EncodeUtf8(newPwd));
        stu.setLastUpdatedBy(student.getId());
        studentDao.save(stu);
        return Dto.getSuccess("密码更新成功");
    }
    //设置StudentVO
    public StudentVo setStudentVo(Student student){
        StudentVo studentVo = new StudentVo();
        studentVo.setId(student.getId());
        studentVo.setUsername(student.getUsername());
        studentVo.setName(student.getName());
        studentVo.setStudentId(student.getStuNum());
        Major major = majorDao.findAllById(student.getMajorId());
        if(major != null){
            studentVo.setMajor(major.getName());
            studentVo.setGrade(major.getGrade());
        }
        studentVo.setCreatedTime(student.getCreateTime());
        studentVo.setLastUpdatedTime(student.getUpdateTime());
        return studentVo;
    }
    //查询待完成的试卷
    public Dto getUnfinishedPaper(Student student){
        List<PaperAndMajor> paperAndMajorList = paperAndMajorDao.findAllByMajorId(student.getMajorId());
        /*for(PaperAndMajor paperAndMajor:paperAndMajorList){
            System.out.println(paperAndMajor);
        }*/
        List<StudentPaperVo> list = new ArrayList<>();
        for(PaperAndMajor paperAndMajor:paperAndMajorList){
            Score score = scoreDao.findAllByStudentIdAndPaperId(student.getId(),paperAndMajor.getPaperId());
            if(score != null){
                //System.out.println(score);
                continue;
            }
            StudentPaperVo studentPaperVo = new StudentPaperVo();
            studentPaperVo.setPaperId(paperAndMajor.getPaperId());
            studentPaperVo.setPublicTime(TimeUtil.dateToStr(paperAndMajor.getPublishTime()));
            Paper paper = paperDao.findAllById(paperAndMajor.getPaperId());
            studentPaperVo.setPaperName(paper.getName());
            list.add(studentPaperVo);
        }
        return Dto.getSuccess(list);
    }
    //获取试卷内容
    public Dto getPaper(Integer pid,Student student){
        PaperAndMajor paperAndMajor = paperAndMajorDao.findAllByPaperIdAndMajorId(pid,student.getMajorId());
        if(paperAndMajor == null){
            return Dto.getFailed("无法浏览该试卷");
        }
        PaperDetailVo paperDetailVo = new PaperDetailVo();
        List<ChoiceQuestionVO> choiceQuestionVOList = new ArrayList<>();
        Paper paper = paperDao.findAllById(pid);
        String score = "0";
        if(paper != null){
            paperDetailVo.setId(paper.getId());
            paperDetailVo.setName(paper.getName());
            String createdBy = teacherDao.findAllById(paper.getCreatedBy()).getName();
            paperDetailVo.setCreatedBy(createdBy);
        }
        List<PaperQuestion> paperQuestionList = paperQuestionDao.findAllByPaperId(pid);
        if(paperQuestionList.size()>0){
            for(PaperQuestion paperQuestion:paperQuestionList){
                score = BigDecimalUtil.add(paperQuestion.getScore(), score);
                String type = paperQuestion.getType();
                if("1".equals(type)){
                    setOption(paperQuestion,choiceQuestionVOList);
                }
            }
        }
        paperDetailVo.setScore(score);
        paperDetailVo.setChoiceQuestion(choiceQuestionVOList);
        return Dto.getSuccess(paperDetailVo);

    }
    /**
     * 选择题拼装
     */
    private void setOption(PaperQuestion p, List<ChoiceQuestionVO> choiceQuestionVOList) {
        ChoiceQuestionVO choiceQuestionVO = new ChoiceQuestionVO();
        Question question = questionDao.findAllById(p.getQuestionId());
        //拼装choiceQuestionVO对象
        choiceQuestionVO.setId(question.getId());
        choiceQuestionVO.setTitle(question.getTitle());
        String[] contents = question.getContent().split(";");
        choiceQuestionVO.setOptionA(contents[0]);
        choiceQuestionVO.setOptionB(contents[1]);
        choiceQuestionVO.setOptionC(contents[2]);
        choiceQuestionVO.setOptionD(contents[3]);
        choiceQuestionVO.setScore(p.getScore());
        choiceQuestionVOList.add(choiceQuestionVO);
    }
    //学生交卷，计算分数
    public Dto submitPaper(Integer pid,Student student,String questionAndanswer){
        Integer scores = 0;
        String[] taas = questionAndanswer.split(";");
        for(String t : taas){
            String[] taa = t.split("_");
            if(StringUtils.isNoneBlank(taa[0], taa[1])){
                Question question = questionDao.findAllByIdAndAnswer(Integer.parseInt(taa[0]),taa[1]);
                if(question!=null){
                    scores += Integer.valueOf(taa[2]);
                }
            }else{
                return Dto.getFailed("非法请求！！！");
            }
        }
        Score score = new Score();
        score.setPaperId(pid);
        score.setStudentId(student.getId());
        score.setScore(String.valueOf(scores));
        score.setFlag("Y");
        score = scoreDao.save(score);
        if(score!=null){
            return Dto.getSuccess("交卷成功");
        }else{
            return Dto.getFailed("交卷失败");
        }
    }
    //查询成绩
    public Dto queryScore(Student student){
        List<Score> scoreList = scoreDao.findAllByStudentId(student.getId());
        List<ScoreVo> scoreVos = new ArrayList<>();
        for(Score score : scoreList){
            ScoreVo scoreVo = new ScoreVo();
            Student stu = studentDao.findAllById(student.getId());
            scoreVo.setStudentName(stu.getName());
            scoreVo.setStudentId(stu.getStuNum());
            Major major = majorDao.findAllById(stu.getMajorId());
            scoreVo.setMajor(major.getGrade()+" "+major.getName());
            Paper paper = paperDao.findAllById(score.getPaperId());
            scoreVo.setPaperName(paper.getName());
            scoreVo.setScore(score.getScore());
            scoreVos.add(scoreVo);
        }
        return Dto.getSuccess(scoreVos);
    }
}
