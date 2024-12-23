package com.capricorn.summer;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * 数据库加密
 */
public class DruidEncryptUtil {

    /**
     * 在启动时传递参数
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 要加密的信息
        String password = "capricorn";
        System.out.println("加密密码[ " + password + " ]的加密信息如下：\n");
        // 阿里加密工具类
        String[] keyPair = ConfigTools.genKeyPair(512);
        // 获取私钥
        String privateKey = keyPair[0];
        // 获取公钥
        String publicKey = keyPair[1];
        // 用私钥加密后的密文
        password = ConfigTools.encrypt(privateKey, password);

        // 私钥
        System.out.println("privateKey:" + privateKey);
        // 公钥
        System.out.println("publicKey:" + publicKey);
        // 加密后的密码
        System.out.println("password:" + password);
        // 解密
        String decryptPassword = ConfigTools.decrypt(publicKey, password);
        // 解密后的密码
        System.out.println("decryptPassword：" + decryptPassword);
    }

}
