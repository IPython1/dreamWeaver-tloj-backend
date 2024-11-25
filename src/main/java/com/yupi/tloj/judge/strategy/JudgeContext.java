package com.yupi.tloj.judge.strategy;

import com.yupi.tloj.model.dto.question.JudgeCase;
import com.yupi.tloj.judge.codesandbox.model.JudgeInfo;
import com.yupi.tloj.model.entity.Question;
import com.yupi.tloj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @Author:杰杰睡不醒
 * @Date:2024/11/3 14:38
 * @Description:    上下文 用于定义再策略中传递的参数
 **/
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private Question question;

    private List<JudgeCase> judgeCaseList;

    private QuestionSubmit questionSubmit;

}
