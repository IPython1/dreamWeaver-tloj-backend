package com.yupi.tloj.judge.codesandbox;

import com.yupi.tloj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.yupi.tloj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.yupi.tloj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * @Author:杰杰睡不醒
 * @Date:2024/11/2 23:37
 * @Description:
 * 工厂模式 这里没有使用单例工厂模式
 * 代码沙箱工厂  (根据字符串参数创建指定的代码沙箱实例)
 **/
public class CodeSandboxFactory {
    /*
    * 创建代码沙箱示例
    * @param type 沙箱类型
    * */
    public static CodeSandbox newInstance(String type){
        switch (type){
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
