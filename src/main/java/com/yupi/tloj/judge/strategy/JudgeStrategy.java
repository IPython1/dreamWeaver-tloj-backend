package com.yupi.tloj.judge.strategy;


import com.yupi.tloj.judge.codesandbox.model.JudgeInfo;

/*
* 判题策略
* */
public interface JudgeStrategy {

    /*
    * 执行判题
    *
    * */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
