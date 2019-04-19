/*
 *
 *  *   @project        ECC-demo
 *  *   @file           ECCCoder2
 *  *   @author         warne
 *  *   @date           19-4-19 下午3:05
 *
 */

package com.warne.ecc;


import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;

/**
 * function：description
 * datetime：2019-04-19 13:51
 * author：warne
 */
public class ECCCoder2 {
    final static Logger log = LoggerFactory.getLogger(ECCCoder2.class);

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            getKey();
        }
    }

    public static void getKey() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");

        keyPairGenerator.initialize(ECNamedCurveTable.getParameterSpec("secp192r1"));
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        ECPublicKey pubKey = (ECPublicKey) keyPair.getPublic();

        //记录公钥的x 和y值
        byte[] x = pubKey.getW().getAffineX().toByteArray();
        byte[] y = pubKey.getW().getAffineY().toByteArray();

        //开始还原公钥，需要使用生成密钥时记录的x和y值
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        ECPoint ecPoint = new ECPoint(new BigInteger(1, x), new BigInteger(1, y));

        ECNamedCurveParameterSpec parameterSpec = ECNamedCurveTable.getParameterSpec("secp192r1");
        ECNamedCurveSpec spec = new ECNamedCurveSpec("secp192r1", parameterSpec.getCurve(), parameterSpec.getG(), parameterSpec.getN(), parameterSpec.getH(), parameterSpec.getSeed());
        ECPublicKey ecPublicKey = (ECPublicKey) keyFactory.generatePublic(new ECPublicKeySpec(ecPoint, spec));

        ECPrivateKey ecPrivateKey = (ECPrivateKey) keyFactory.generatePrivate(new ECPrivateKeySpec(spec.getOrder(), parameterSpec));

        log.info("publicKey={}", encodeBase64(ecPublicKey.getEncoded()));
        log.info("privateKey={}", encodeBase64(ecPrivateKey.getEncoded()));
    }

    /**
     * BASE64 编码
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encodeBase64(byte[] key) {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

}
