package com.yupi.tloj.judge;

import cn.hutool.json.JSONUtil;
import com.yupi.tloj.common.ErrorCode;
import com.yupi.tloj.exception.BusinessException;
import com.yupi.tloj.judge.codesandbox.CodeSandbox;
import com.yupi.tloj.judge.codesandbox.CodeSandboxFactory;
import com.yupi.tloj.judge.codesandbox.CodeSandboxProxy;
import com.yupi.tloj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.tloj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.tloj.judge.strategy.DefaultJudgeStrategy;
import com.yupi.tloj.judge.strategy.JudgeContext;
import com.yupi.tloj.judge.strategy.JudgeStrategy;
import com.yupi.tloj.model.dto.question.JudgeCase;
import com.yupi.tloj.judge.codesandbox.model.JudgeInfo;
import com.yupi.tloj.model.entity.Question;
import com.yupi.tloj.model.entity.QuestionSubmit;
import com.yupi.tloj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.tloj.service.QuestionService;
import com.yupi.tloj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author:杰杰睡不醒
 * @Date:2024/11/3 14:03
 * @Description:
 **/
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Value("${codesandbox.type:example}")
    private String type;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1.传入题目的提交id，获取到对应的题目、提交信息(包含代码、编程语言等)
        //根据提交的id获取到题目提交信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        Long questionId = questionSubmit.getQuestionId();
        //根据题目id去获取题目信息
        Question question = questionService.getById(questionId);
        if (question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //2.如果题目提交状态不为等待中，就不用重复执行了
        if (!Objects.equals(questionSubmit.getStatus(), QuestionSubmitStatusEnum.WAITTING.getValue())){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        //3.更改判题(题目提交)的状态为“判题中”，防止重复执行，也能让用户即时看到状态 这里相当于内层加了一个锁
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目状态更新错误");
        }
        //4)调用沙箱，获取到执行结果
        //        先通过工厂模式 创建了一个可指定类型的代码沙箱 接口对象
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        // 再通过代理模式 这里主要添加了日志的输入输出
        codeSandbox = new CodeSandboxProxy(codeSandbox);

        // 获取输入用例 将字符串转换为数组列表
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .inputList(inputList)
                .code(code)
                .language(language)
                .build();
        //调用代码沙箱得到结果
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);

        List<String> outputList = executeCodeResponse.getOutputList();


        //5.根据沙箱的执行结果 设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();

        //策略模式的选择单独定义为一个类
        JudgeInfo judgeInfo=judgeManager.doJudge(judgeContext);
//        JudgeInfo judgeInfo = judgeStrategy.doJudge(judgeContext);

        //6.修改数据库中判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));//转换成json
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目状态更新错误");
        }
        QuestionSubmit questionSubmitResult =questionSubmitService.getById(questionSubmitId);
        return questionSubmitResult;
    }
}
