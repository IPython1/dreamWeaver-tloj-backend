package com.yupi.tloj.model.dto.question;

import lombok.Data;

/**
 * @Author:杰杰睡不醒
 * @Date:2024/11/1 10:14
 * @Description:题目配置
 **/
@Data
public class JudgeConfig {
    /*
    * 时间限制(ms)
    * */
    private Long timeLimit;
    /*
    * 内存限制(kb)
    * */
    private Long memoryLimit;
    /*
    * 堆栈限制(kb)
    * */
    private Long stackLimit;
}
