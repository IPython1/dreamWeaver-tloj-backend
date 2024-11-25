package com.yupi.tloj.judge.codesandbox.impl;

import com.yupi.tloj.judge.codesandbox.CodeSandbox;
import com.yupi.tloj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.tloj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @Author:杰杰睡不醒
 * @Date:2024/11/2 22:27
 * @Description: 第三方代码沙箱 调用网上现成的代码沙箱
 **/
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱执行代码");
        return null;
    }
}
