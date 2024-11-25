package com.yupi.tloj.judge;

import com.yupi.tloj.judge.strategy.DefaultJudgeStrategy;
import com.yupi.tloj.judge.strategy.JavaLanguageJudgeStrategy;
import com.yupi.tloj.judge.strategy.JudgeContext;
import com.yupi.tloj.judge.strategy.JudgeStrategy;
import com.yupi.tloj.judge.codesandbox.model.JudgeInfo;
import com.yupi.tloj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * @Author:杰杰睡不醒
 * @Date:2024/11/3 15:06
 * @Description:判题管理 简化调用
 **/
@Service
public class JudgeManager {

    /*
     * 执行判题
     *
     * */
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if (language.equals("java")){
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
