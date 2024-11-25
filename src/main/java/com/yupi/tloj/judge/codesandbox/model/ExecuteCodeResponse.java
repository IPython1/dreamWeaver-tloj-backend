package com.yupi.tloj.judge.codesandbox.model;

import lombok.Data;

import java.util.List;

/**
 * @Author:杰杰睡不醒
 * @Date:2024/11/2 22:22
 * @Description:
 **/
@Data
public class ExecuteCodeResponse {

    private List<String> outputList;

    /*
    * 执行状态
    * */
    private Integer status;

    /*
     * 接口信息
     * */
    private String message;

    /*
    * 判题信息
    * */
    private JudgeInfo judgeInfo;

}
