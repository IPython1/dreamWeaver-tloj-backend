package com.yupi.tloj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.yupi.tloj.model.dto.question.JudgeCase;
import com.yupi.tloj.model.dto.question.JudgeConfig;
import com.yupi.tloj.judge.codesandbox.model.JudgeInfo;
import com.yupi.tloj.model.entity.Question;
import com.yupi.tloj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * @Author:杰杰睡不醒
 * @Date:2024/11/3 14:40
 * @Description:默认判题策略
 **/
public class DefaultJudgeStrategy implements JudgeStrategy {
    //默认策略
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();


        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.WAITING;
        //1.先判断沙箱执行的结果输出数量是否和预期输出数量相等
        //3.判题题目的限制是否符合要求
        //4.可能还有其他的异常情况
        if (outputList.size()!=inputList.size()){
            judgeInfoMessageEnum=JudgeInfoMessageEnum.WRONG_ANSWER;
            return null;
        }
        //2.依次判断每一项输出和预期输出是否相等
        for (int i = 0; i < judgeCaseList.size(); i++){
            JudgeCase judgeCase = judgeCaseList.get(i);
            String input = judgeCase.getInput();
            String output = judgeCase.getOutput();
            if (!output.equals(outputList.get(i))){
                judgeInfoMessageEnum=JudgeInfoMessageEnum.WRONG_ANSWER;
                break;
            }
        }
        //3.判题题目的限制是否符合要求
//        JudgeInfo judgeInfo=executeCodeResponse.getJudgeInfo();

        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long needMemoryLimit = judgeConfig.getMemoryLimit();
        Long needTimeLimit = judgeConfig.getTimeLimit();
        if (memory>needMemoryLimit){
            judgeInfoMessageEnum=JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            return null;
        }
        if (time>needTimeLimit){
            judgeInfoMessageEnum=JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            return null;
        }
        JudgeInfo judgeInfoResponse =new JudgeInfo();
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);

        return judgeInfoResponse;
    }
}
