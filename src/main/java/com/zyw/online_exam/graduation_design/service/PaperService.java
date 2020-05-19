package com.zyw.online_exam.graduation_design.service;

import com.zyw.online_exam.graduation_design.Dto;
import com.zyw.online_exam.graduation_design.dao.*;
import com.zyw.online_exam.graduation_design.exception.MyException;
import com.zyw.online_exam.graduation_design.pojo.*;
import com.zyw.online_exam.graduation_design.utils.BigDecimalUtil;
import com.zyw.online_exam.graduation_design.vo.ChoiceQuestionVO;
import com.zyw.online_exam.graduation_design.vo.PaperDetailVo;
import com.zyw.online_exam.graduation_design.vo.PaperQuestionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cengyunwen
 * @version 1.0
 * @date 2020/4/10 16:19
 */
@Service
public class PaperService {
    private static final String FLAG_Y = "Y";

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

    //查询试卷
    public Dto queryPaper(int start, int size,String query){
        Pageable pageable = PageRequest.of(start-1, size);
        Page<Paper> page = paperDao.findAllByNameLike(pageable,"%"+query+"%");
        List<Paper> lists = page.getContent();
        int total = paperDao.countAllByNameLike("%"+query+"%");
        if (lists.size() == 0) {
            return Dto.getSuccess("当前没有信息");

        }
        return Dto.getSuccess("查询成功",lists,total);
    }

    //增加试卷
    public Dto addPaper(String paperName, String publicFlag, Teacher teacher) {
        if (StringUtils.isNotBlank(paperName) && StringUtils.isNotBlank(publicFlag)) {
            Paper paper = new Paper();
            paper.setName(paperName);
            paper.setIsPublic(publicFlag);
            paper.setIsEdit("Y");
            paper.setFlag("Y");
            paper.setCreatedBy(teacher.getId());
            paper.setLastUpdatedBy(teacher.getId());
            paperDao.save(paper);
            return Dto.getSuccess("新增试卷成功");
        }
        return Dto.getSuccess("新增试卷失败");
    }

    //修改公开状态
    public Dto updatePublic(Integer pid, String flag, Teacher teacher) {
        if (pid != null && StringUtils.isNotBlank(flag)) {
            Paper paper = paperDao.findAllById(pid);
            if (paper == null) {
                return Dto.getFailed("没有这张试卷");
            }
            if (paper.getCreatedBy().equals(teacher.getId())) {
                paper.setIsPublic(flag);
                paper.setLastUpdatedBy(teacher.getId());
                paperDao.save(paper);
                return Dto.getSuccess("修改成功");
            } else {
                return Dto.getFailed("没有权限修改这张试卷");
            }
        }
        return Dto.getFailed("参数不正确");


    }

    //修改试卷是否有效
    public Dto updateFlag(Integer pid, String flag, Teacher teacher) {
        if (pid != null && StringUtils.isNotBlank(flag)) {
            Paper paper = paperDao.findAllById(pid);
            if (paper == null) {
                return Dto.getFailed("没有这张试卷");
            }
            if (paper.getCreatedBy().equals(teacher.getId())) {
                paper.setFlag(flag);
                paper.setLastUpdatedBy(teacher.getId());
                paperDao.save(paper);
                return Dto.getSuccess("修改成功");
            } else {
                return Dto.getFailed("没有权限修改这张试卷");
            }
        }
        return Dto.getFailed("参数不正确");

    }

    public Dto paperDetail(Integer pid, Teacher teacher) {
        Paper paper = paperDao.findAllById(pid);
        if(paper == null){
            return Dto.getFailed("没有该张试卷");
        }
        String isPublic = paper.getIsPublic();
        if (!FLAG_Y.equals(isPublic)) {
            Integer id = paper.getCreatedBy();
            if (!teacher.getId().equals(id)) {
                return Dto.getFailed("无权限查看不属于你的未公开试卷");
            }
        }
        //试卷详情对象
        PaperDetailVo paperDetailVo = new PaperDetailVo();
        //选择题list对象
        List<ChoiceQuestionVO> choiceQuestionVOList = new ArrayList<>();
        if (paper != null) {
            paperDetailVo.setId(pid);
            paperDetailVo.setName(paper.getName());
            Teacher tea = teacherDao.findAllById(paper.getCreatedBy());
            paperDetailVo.setCreatedBy(tea.getName());
        }
        String score = "0";
        List<PaperQuestion> paperQuestions = paperQuestionDao.findAllByPaperId(pid);
        if (paperQuestions.size() > 0) {
            for (PaperQuestion p : paperQuestions) {
                //累积分数
                score = BigDecimalUtil.add(p.getScore(), score);
                //根据试题类型分别加入对应list
                String type = p.getType();
                if ("1".equals(type)) {
                    setOption(p, choiceQuestionVOList);
                }
            }
        }
        paperDetailVo.setScore(String.valueOf(score));
        paperDetailVo.setChoiceQuestion(choiceQuestionVOList);
        List<PaperDetailVo> paperDetailVos = new ArrayList<>();
        paperDetailVos.add(paperDetailVo);
        return Dto.getSuccess(paperDetailVos);
    }

    /**
     * 选择题拼装
     */
    private void setOption(PaperQuestion p, List<ChoiceQuestionVO> choiceQuestionVOList) {
        ChoiceQuestionVO choiceQuestionVO = new ChoiceQuestionVO();
        Question question = questionDao.findAllById(p.getQuestionId());
        //拼装choiceQuestionVO对象
        choiceQuestionVO.setType("选择题");
        choiceQuestionVO.setSubject(question.getSubject());
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

    //组装试卷 添加试题
    public Dto compositionPaper(PaperQuestion paperQuestion, Teacher teacher) {
        Paper paper = paperDao.findAllById(paperQuestion.getPaperId());
        if (paper == null) {
            return Dto.getFailed("没有这张试卷");
        }
        Integer tid = paper.getCreatedBy();
        if (tid.equals(teacher.getId())) {
            String edit = paper.getIsEdit();
            if (FLAG_Y.equals(edit)) {
                PaperQuestion is_repeat = paperQuestionDao.findAllByPaperIdAndQuestionId(paperQuestion.getPaperId(), paperQuestion.getQuestionId());
                if ((is_repeat != null)) {
                    return Dto.getFailed("该试卷已有这道题，请勿重复添加");
                }
                Question question = questionDao.findAllById(paperQuestion.getQuestionId());
                if (question == null) {
                    return Dto.getFailed("题库里没有这道题");
                }
                paperQuestion.setType(question.getType());
                paperQuestionDao.save(paperQuestion);
                return Dto.getSuccess("组装这道题成功");
            }
            return Dto.getFailed("该试卷不可编辑");
        }
        return Dto.getFailed("无权限");

    }

    //删除试卷试题
    @Transactional
    public Dto deleteQuestionFromPaper(Integer pid, Integer qid, Teacher teacher) {
        Paper paper = paperDao.findAllById(pid);
        if (paper.getCreatedBy().equals(teacher.getId())) {
            String id_edit = paper.getIsEdit();
            if (FLAG_Y.equals(id_edit)) {
                int result = paperQuestionDao.deleteByPaperIdAndQuestionId(pid, qid);
                if (result > 0) {
                    return Dto.getSuccess("删除成功");
                }
                return Dto.getFailed("删除失败");
            }
            return Dto.getFailed("该试卷不可编辑");
        }
        return Dto.getFailed("无权限");
    }

    //清空试卷试题
    @Transactional
    public Dto emptyQuestionFromPaper(Integer pid, Teacher teacher) {
        Paper paper = paperDao.findAllById(pid);
        if (paper.getCreatedBy().equals(teacher.getId())) {
            String id_edit = paper.getIsEdit();
            if (FLAG_Y.equals(id_edit)) {
                int result = paperQuestionDao.deleteAllByPaperId(pid);
                if (result > 0) {
                    return Dto.getSuccess("清空成功");
                }
                return Dto.getFailed("清空失败");
            }
            return Dto.getFailed("该试卷不可编辑");
        }
        return Dto.getFailed("无权限");
    }

    //发布试卷
    public Dto publishPaper(Integer pid, Integer mid, Teacher teacher) {
        Paper paper = paperDao.findAllById(pid);
        if (paper.getCreatedBy().equals(teacher.getId())) {
            TeacherAndMajor teacherAndMajor = teacherAndMajorDao.findAllByTeacherIdAndMajorId(teacher.getId(), pid);
            if (teacherAndMajor == null) {
                return Dto.getFailed("你不是这个专业的老师");
            }
            PaperAndMajor paperAndMajor = paperAndMajorDao.findAllByPaperIdAndMajorId(pid, mid);
            if (paperAndMajor != null) {
                return Dto.getSuccess("该专业已经被布置过该试卷");
            }
            String flag = paper.getFlag();
            if ("N".equals(flag)) {
                return Dto.getFailed("试卷无效，请联系相关人员");
            }
            PaperAndMajor paperAndMajor1 = new PaperAndMajor();
            paperAndMajor1.setMajorId(mid);
            paperAndMajor1.setPaperId(pid);
            paperAndMajor1 = paperAndMajorDao.save(paperAndMajor1);
            if (paperAndMajor1 != null) {
                return Dto.getSuccess("发布成功");
            }
            return Dto.getFailed("发布失败");
        }
        return Dto.getFailed("无权限");
    }

    //查询试卷试题
    public Dto selectPaperQuestion(Integer pid) {
        List<PaperQuestion> paperQuestionList = paperQuestionDao.findAllByPaperId(pid);
        List<PaperQuestionVo> paperQuestionVoList = new ArrayList<>();
        for (PaperQuestion paperQuestion : paperQuestionList) {
            PaperQuestionVo paperQuestionVo = new PaperQuestionVo();
            Question question = questionDao.findAllById(paperQuestion.getQuestionId());
            paperQuestionVo.setId(question.getId());
            paperQuestionVo.setTestTitle(question.getTitle());
            paperQuestionVo.setScore(paperQuestion.getScore());
            paperQuestionVo.setPriority(paperQuestion.getPriority());
            paperQuestionVoList.add(paperQuestionVo);
        }
        return Dto.getSuccess(paperQuestionVoList);
    }

    //自动组装试卷
    public Dto autoBuildPaper(String paperName, String is_public,
                              Integer optionNum, String optionScore,
                              String subject, Teacher teacher) {
        if (StringUtils.isBlank(paperName) || StringUtils.isBlank(subject)) {
            return Dto.getFailed("试卷名称或学科不能为空");
        }
        Paper paper = paperDao.findAllByName(paperName);
        if (paper != null) {
            return Dto.getFailed("试卷名重复，请修改名字");
        }
        int count = questionDao.countAllBySubjectAndType(subject, "1");
        if (count < optionNum) {
            return Dto.getFailed("题库中该题的数量不够");
        }
        Paper newPaper = new Paper();
        newPaper.setName(paperName);
        newPaper.setIsPublic(is_public);
        newPaper.setIsEdit("Y");
        newPaper.setFlag("Y");
        newPaper.setCreatedBy(teacher.getId());
        newPaper.setLastUpdatedBy(teacher.getId());
        newPaper = paperDao.save(newPaper);
        if (newPaper != null) {
            Integer pid = paperDao.findAllByName(paperName).getId();
            List<Question> questionList = questionDao.randomFind("1",subject,optionNum);
            if(questionList!=null){
                for(Question question:questionList){
                    PaperQuestion paperQuestion = new PaperQuestion();
                    paperQuestion.setPaperId(pid);
                    paperQuestion.setQuestionId(question.getId());
                    paperQuestion.setType(question.getType());
                    paperQuestion.setScore(optionScore);
                    paperQuestion.setPriority("1");
                    paperQuestionDao.save(paperQuestion);
                }
                return Dto.getSuccess("试卷组装成功");
            }
            return Dto.getFailed("获取试题失败");
        }
        return Dto.getFailed("试卷生成失败");
    }
}
