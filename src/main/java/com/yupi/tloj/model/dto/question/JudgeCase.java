package com.yupi.tloj.model.dto.question;

import lombok.Data;

/**
 * @Author:杰杰睡不醒
 * @Date:2024/11/1 10:14
 * @Description:题目用例
 **/
@Data
public class JudgeCase {
    /*
    * 输入用例
    * */
    private String input;
    /*
    * 输出用例
    * */
    private String output;
}
