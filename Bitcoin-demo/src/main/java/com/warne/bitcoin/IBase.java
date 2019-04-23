/*
 *
 *  *   @project        Bitcoin-demo
 *  *   @file           IBase
 *  *   @author         warne
 *  *   @date           19-4-23 下午3:07
 *
 */

package com.warne.bitcoin;

import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;

/**
 * function：description
 * datetime：2019-04-23 15:07
 * author：warne
 */
@Slf4j
public class IBase {
    public final static NetworkParameters networkParam = TestNet3Params.get();
    public final static String walletName = "mywallet";
    public final static String walletNameWithSeedCode = "mywallet_seedcode"; //# 助记词类型的钱包


    /**
     * ### Bitcoin 比特币 wallet 钱包 助记词
     * #### 创建钱包 create walllet
     * #### 查询余额  query balance
     * #### 恢复钱包  recovery wallet
     * #### 发送交易  转账  send transaction
     *
     * ---
     *
     *
     * ### 比特币环境
     * #### TestNet3 公网测试环境  实例化：TestNet3Params.get()
     * #### TestNet2 公网测试环境  实例化：TestNet2Params.get()
     * #### RegTest  私有测试环境，需要自己搭建环境  实例化：RegTestParams.get()
     * #### UnitTest test测试环境  实例化：UnitTestParams.get()
     * #### MainNet  正式环境  实例化：MainNetParams.get()  【这个环境上的币是有价值的，这个环境上的币是有价值的，这个环境上的币是有价值的，重要的事说三遍遍！】
     *
     *
     * ---
     * #### 比特币地址类型
     * ##### 开头是m或n字母的是测试环境的地址（币没有真正价值）
     * ##### 开头是数字的是正式环境的地址（币有真正价值！！！）
     *
     * | ######### |    版本前缀 (hex)   | Base58格式 | 描述  |
     * |----------|-------------|-------------|-------------|
     * |Bitcoin Address | 0x00  | 1开头  |  比特币正式地址 |
     * |Pay-to-Script-Hash Address| 0x05 |  3开头  |  比特币正式地址 |
     * |Bitcoin Testnet Address| 0x6F |  m或n开头  |  比特币测试环境地址 |
     * |Private Key WIF| 0x80 |  5, K or L  |   比特币地址 | Wallet Import Format 私钥格式 |
     * |BIP38 Encrypted Private Key| 0x0142 |  6P  |  |
     * |BIP32 Extended Public Key| 0x0488B21E |  xpub  |  |
     *
     */

}
