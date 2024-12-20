//package com.yupi.tloj.controller;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.yupi.tloj.annotation.AuthCheck;
//import com.yupi.tloj.common.BaseResponse;
//import com.yupi.tloj.common.ErrorCode;
//import com.yupi.tloj.common.ResultUtils;
//import com.yupi.tloj.constant.UserConstant;
//import com.yupi.tloj.exception.BusinessException;
//
//import com.yupi.tloj.model.dto.question.QuestionQueryRequest;
//import com.yupi.tloj.model.dto.questionsubmit.QuestionSubmitAddRequest;
//import com.yupi.tloj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
//import com.yupi.tloj.model.entity.Question;
//import com.yupi.tloj.model.entity.QuestionSubmit;
//import com.yupi.tloj.model.entity.User;
//import com.yupi.tloj.model.vo.QuestionSubmitVO;
//import com.yupi.tloj.service.QuestionSubmitService;
//import com.yupi.tloj.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 题目提交接口
// *
// * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
// * @from <a href="https://yupi.icu">编程导航知识星球</a>
// */
//@RestController
//@RequestMapping("/question_submit")
//@Slf4j
//@Deprecated
//public class QuestionSubmitController {
//
//    @Resource
//    private QuestionSubmitService questionSubmitService;
//
//    @Resource
//    private UserService userService;
//
//    /**
//     * 提交题目
//     *
//     * @param questionSubmitAddRequest
//     * @param request
//     * @return 提交记录 id
//     */
//    @PostMapping("/")
//    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
//            HttpServletRequest request) {
//        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        // 登录才能提交代码
//        final User loginUser = userService.getLoginUser(request);
//        long questionId = questionSubmitAddRequest.getQuestionId();
//        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
//        return ResultUtils.success(questionSubmitId);
//    }
//
//    /**
//     * 分页获取题目提交列表（除了管理员外 普通用户只能看到非答案提交代码的公开信息）
//     *
//     * @param questionSubmitQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/list/page")
//    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
//                                                                         HttpServletRequest request) {
//        long current = questionSubmitQueryRequest.getCurrent();
//        long size = questionSubmitQueryRequest.getPageSize();
//        final User loginUser = userService.getLoginUser(request);
//        //从数据库中查询原始的题目提交分页信息
//        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
//                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
//        //返回脱敏信息
//        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
//    }
//
//}
