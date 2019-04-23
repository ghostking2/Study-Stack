/*
 *
 *  *   @project        Bitcoin-demo
 *  *   @file           CreateWallet
 *  *   @author         warne
 *  *   @date           19-4-23 下午2:58
 *
 */

package com.warne.bitcoin;

import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.Wallet;

import java.io.File;

/**
 * function：description
 * datetime：2019-04-23 14:58
 * author：warne
 */
@Slf4j
public class CreateWallet extends IBase {

    public static void main(String[] args) {

        WalletAppKit walletAppKit = new WalletAppKit(networkParam, new File("."), walletName);
        walletAppKit.startAsync();
        walletAppKit.awaitRunning();

        /**
         * Chain download 8% done with 看到这些日志信息就是表示正在下载区块信息，100%表示更新完成
         *
         * 目录下会生成俩个文件： walletName.wallet 和 walletName.spvchain
         *
         */

        Wallet wallet = walletAppKit.wallet();
        DeterministicKey key = wallet.getWatchingKey();

        System.out.println("\n");

        //# 私钥的不同形式
        log.warn("privateKey={}", key.getPrivKey());
        log.warn("encode privateKey={}", key.getPrivateKeyEncoded(networkParam));
        log.warn("hex privateKey={}", key.getPrivateKeyAsHex());
        log.warn("wif privateKey={}", key.getPrivateKeyAsWiF(networkParam));

        //# 钱包地址
        log.warn("wallet address: {}", wallet.currentReceiveAddress().toBase58());

        //# 钱包全部信息
        log.warn("wallet info: \n {} ", wallet.toString());

        System.out.println("\n");


    }

}
