package com.yupi.tloj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.tloj.model.dto.question.QuestionQueryRequest;
import com.yupi.tloj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.yupi.tloj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.yupi.tloj.model.entity.Question;
import com.yupi.tloj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.tloj.model.entity.User;
import com.yupi.tloj.model.vo.QuestionSubmitVO;
import com.yupi.tloj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author IPython
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2024-10-31 20:10:58
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmit, User loginUser);

}
