/*
 *
 *  *   @project        ECC-demo
 *  *   @file           ECCCoder
 *  *   @author         warne
 *  *   @date           19-4-19 下午1:51
 *
 */

package com.warne.ecc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;
import sun.security.ec.ECKeyPairGenerator;

import java.security.KeyPair;

/**
 * function：description
 * datetime：2019-04-19 13:51
 * author：warne
 */
public class ECCCoder {
    final static Logger log = LoggerFactory.getLogger(ECCCoder.class);

    public static void main(String[] args){
        for (int i = 0; i < 10; i++) {
            ECKeyPairGenerator generator = new ECKeyPairGenerator();
            KeyPair keyPair = generator.generateKeyPair();
            log.info("publicKey={}", encodeBase64(keyPair.getPublic().getEncoded()));
            log.info("privateKey={}", encodeBase64(keyPair.getPrivate().getEncoded()));
        }
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
