package com.ruoyi.biz.api.util;


import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.Security;

/**
 * @Author: 周周
 * @Date: Created in 9:26 2022/3/29
 * @ClassName: AesCbcUtil
 * @Description: 微信用户加密信息解密工具
 */
public class AesCbcUtil {
    /**
     * 加解密算法/模式/填充方式
     */
    private final static String algorithmStr = "AES/CBC/PKCS7Padding";
    /**
     * 算法名称
     */
    private static final String KEY_ALGORITHM = "AES";
    static {
        Security.addProvider(new BouncyCastleProvider()); // BouncyCastle:开源的加解密解决方案
    }

    /**
     * AES解密
     * @param data 密文，被加密的数据
     * @param key 秘钥
     * @param iv 偏移量
     */
    public static String decrypt(String data, String key, String iv) {
        byte[] dataByte = Base64.decodeBase64(data);
        byte[] keyByte = Base64.decodeBase64(key);
        byte[] ivByte = Base64.decodeBase64(iv);
        try {
            Cipher cipher = Cipher.getInstance(algorithmStr);
            SecretKeySpec spec = new SecretKeySpec(keyByte, KEY_ALGORITHM);
            AlgorithmParameters parameters = AlgorithmParameters.getInstance(KEY_ALGORITHM);
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                return new String(resultByte, StandardCharsets.UTF_8);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
