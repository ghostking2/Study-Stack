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

}
