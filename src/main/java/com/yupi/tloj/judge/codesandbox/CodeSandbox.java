package com.yupi.tloj.judge.codesandbox;

import com.yupi.tloj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.tloj.judge.codesandbox.model.ExecuteCodeResponse;


/*
* 代码沙箱接口定义 alt+enter对着类名 会出现很多可以选择的操作比如 创建实现类，创建测试类等等
* */
public interface CodeSandbox {

    /*
    * 执行代码
    * */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
