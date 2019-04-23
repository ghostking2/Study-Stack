/*
 *
 *  *   @project        Bitcoin-demo
 *  *   @file           QueryBalance
 *  *   @author         warne
 *  *   @date           19-4-23 下午3:05
 *
 */

package com.warne.bitcoin;

import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.Wallet;

import java.io.File;

/**
 * function：查询余额
 * datetime：2019-04-23 15:05
 * author：warne
 */
@Slf4j
public class QueryBalance extends IBase {

    public static void main(String[] args) {
        WalletAppKit walletAppKit = new WalletAppKit(networkParam, new File("."), walletName);
        walletAppKit.startAsync();
        walletAppKit.awaitRunning();

        /**
         * Chain download 8% done with 看到这些日志信息就是表示正在下载区块信息，100%表示更新完成
         */

        Wallet wallet = walletAppKit.wallet();

        //# 查询余额，并且优化格式化
        String value = wallet.getBalance().toFriendlyString();

        System.out.println("\n");
        log.warn("wallet balance is {} ", value);
        System.out.println("\n");
    }

}
