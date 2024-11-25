package com.yupi.tloj.judge;


import com.yupi.tloj.model.entity.QuestionSubmit;
import com.yupi.tloj.model.vo.QuestionSubmitVO;

/*
* 判题服务接口
* */
public interface JudgeService {

    /*
    * 判题
    * */
    QuestionSubmit doJudge(long questionSubmitId);

}
