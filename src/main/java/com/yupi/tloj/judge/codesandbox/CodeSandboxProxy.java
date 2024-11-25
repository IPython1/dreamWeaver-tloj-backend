package com.yupi.tloj.judge.codesandbox;

import com.yupi.tloj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.tloj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author:杰杰睡不醒
 * @Date:2024/11/3 11:23
 * @Description:代理模式
 **/
@Slf4j
public class CodeSandboxProxy implements CodeSandbox{

    private  final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息"+executeCodeRequest.toString());
        // 调用工厂模式创建的沙箱对象
        ExecuteCodeResponse executeCodeResponse=codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息"+executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
