package com.yupi.tloj.judge.codesandbox.impl;

import com.yupi.tloj.judge.codesandbox.CodeSandbox;
import com.yupi.tloj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.tloj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.tloj.judge.codesandbox.model.JudgeInfo;
import com.yupi.tloj.model.enums.JudgeInfoMessageEnum;
import com.yupi.tloj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * @Author:杰杰睡不醒
 * @Date:2024/11/2 22:27
 * @Description: 示例代码沙箱（仅为跑通业务流程）
 **/
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();



        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        executeCodeResponse.setMessage("测试执行成功");
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        
        return executeCodeResponse;
    }
}
