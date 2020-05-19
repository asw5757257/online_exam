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

import java.text.SimpleDateFormat;
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
    //未登录时邮箱验证码登录时查询邮箱是否存在
    public Dto checkMail(String email){
        Student stu;
        stu = studentDao.findAllByEmail(email);
        if(stu == null){
            return Dto.getFailed("该邮箱不存在用户");
        }
        return Dto.getSuccess();
    }
    //未登录重置密码
    public Dto resetPassword(String email,String passwordNew){
        Student stu;
        stu = studentDao.findAllByEmail(email);
        stu.setPassword(Md5Util.md5EncodeUtf8(passwordNew));
        stu.setLastUpdatedBy(stu.getId());
        stu = studentDao.save(stu);
        if(stu!=null){
            return Dto.getSuccess("密码更新成功");
        }
        return Dto.getSuccess("密码更新失败");
    }
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
        studentVo.setEmail(student.getEmail());
        studentVo.setTel(student.getTel());
        studentVo.setSex(student.getSex());
        studentVo.setCreatedTime(student.getCreateTime());
        studentVo.setLastUpdatedTime(student.getUpdateTime());
        return studentVo;
    }
    //查询全部试卷
    public Dto getAllPapers(Student student){
        List<PaperAndMajor> paperAndMajorList = paperAndMajorDao.findAllByMajorId(student.getMajorId());
        List<StudentPaperVo> list = new ArrayList<>();
        for(PaperAndMajor paperAndMajor:paperAndMajorList){
            StudentPaperVo studentPaperVo = new StudentPaperVo();
            studentPaperVo.setPaperId(paperAndMajor.getPaperId());
            studentPaperVo.setPublicTime(TimeUtil.dateToStr(paperAndMajor.getPublishTime()));
            Paper paper = paperDao.findAllById(paperAndMajor.getPaperId());
            studentPaperVo.setPaperName(paper.getName());
            list.add(studentPaperVo);
        }
        return Dto.getSuccess(list);
    }
    //查询待完成的试卷
    public Dto getUnfinishedPaper(Student student){
        List<PaperAndMajor> paperAndMajorList = paperAndMajorDao.findAllByMajorId(student.getMajorId());
        /*for(PaperAndMajor paperAndMajor:paperAndMajorList){
            System.out.println(paperAndMajor);
        }*/
        List<StudentPaperVo> list = new ArrayList<>();
        for(PaperAndMajor paperAndMajor:paperAndMajorList){
            List<Score> score = scoreDao.findByStudentIdAndPaperId(student.getId(),paperAndMajor.getPaperId());
            if(score.size()>0){
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
    public Dto submitPaper(Integer pid,Student student,String questionAndAnswer){
        Integer scores = 0;
        String[] taas = questionAndAnswer.split(";");
        for(String t : taas){
            String[] taa = t.split("_");
            if(StringUtils.isNoneBlank(taa[0], taa[1])){
                Question question = questionDao.findAllByIdAndAnswer(Integer.parseInt(taa[0]),taa[1]);
                PaperQuestion paperQuestion = paperQuestionDao.findAllByPaperIdAndQuestionId(pid,Integer.parseInt(taa[0]));
                if(question!=null){
                    scores += Integer.parseInt(paperQuestion.getScore());
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
    //获取本次考试的成绩
    public Dto getThisScore(Integer pid,Student student,String questionAndAnswer){
        String[] taas = questionAndAnswer.split(";");
        StringBuffer answer = new StringBuffer();
        for(String t : taas){
            String[] taa = t.split("_");
            if(StringUtils.isNoneBlank(taa[0], taa[1])){
                Question question = questionDao.findAllById(Integer.parseInt(taa[0]));
                if(question == null){
                    return Dto.getFailed("获取交卷详情失败");
                }
                answer.append(taa[0]).append("_").append(question.getAnswer()).append(";");
            }
        }
        answer.deleteCharAt(answer.length()-1);
        return Dto.getSuccess(answer);
    }
    //查询成绩
    public Dto queryScore(Student student){
        List<Score> scoreList = scoreDao.findAllByStudentId(student.getId());
        List<ScoreVo> scoreVos = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Score score : scoreList){
            ScoreVo scoreVo = new ScoreVo();
            Student stu = studentDao.findAllById(student.getId());
            scoreVo.setStudentName(stu.getName());
            scoreVo.setStudentId(stu.getStuNum());
            scoreVo.setFinishTime(sdf.format(score.getFinishTime()));
            Major major = majorDao.findAllById(stu.getMajorId());
            scoreVo.setMajor(major.getGrade()+" "+major.getName());
            Paper paper = paperDao.findAllById(score.getPaperId());
            scoreVo.setPaperName(paper.getName());
            scoreVo.setScore(score.getScore());
            scoreVos.add(scoreVo);
        }
        int count = scoreDao.countAllByStudentId(student.getId());
        return Dto.getSuccess("查询成功",scoreVos,count);
    }
    //查询成绩
    public Dto queryThisScore(Student student,Integer pid){
        List<Score> list = scoreDao.findByStudentIdAndPaperId(student.getId(),pid);
        String score = list.get(list.size()-1).getScore();
        return Dto.getSuccess(score);
    }
}
