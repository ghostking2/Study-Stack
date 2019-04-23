/*
 *
 *  *   @project        Bitcoin-demo
 *  *   @file           CreateWalletWithSeedCode
 *  *   @author         warne
 *  *   @date           19-4-23 下午3:15
 *
 */

package com.warne.bitcoin;

import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import static org.bitcoinj.crypto.HDUtils.parsePath;

/**
 * function：创建钱包，输出助记词
 * datetime：2019-04-23 15:15
 * author：warne
 */
@Slf4j
public class CreateWalletWithSeedCode extends IBase {

    public static void main(String[] args) {

        WalletAppKit walletAppKit = new WalletAppKit(networkParam, new File("."), walletNameWithSeedCode) {
            @Override
            protected void onSetupCompleted() {
                wallet().allowSpendingUnconfirmedTransactions();
            }
        };

        /**
         * Chain download 8% done with 看到这些日志信息就是表示正在下载区块信息，100%表示更新完成
         *
         * 目录下会生成俩个文件： walletNameWithSeedCode.wallet 和 walletNameWithSeedCode.spvchain
         *
         */

        walletAppKit.startAsync();
        walletAppKit.awaitRunning();

        Wallet wallet = walletAppKit.wallet();
        List<ECKey> importedKeys = wallet.getImportedKeys();
        if (importedKeys != null && importedKeys.size() > 0) {
            for (ECKey importedKey : importedKeys) {
                wallet.removeKey(importedKey);
            }
        }

        DeterministicSeed keyChainSeed = wallet.getKeyChainSeed();

        DeterministicKeyChain deterministicKeyChain = DeterministicKeyChain.builder().seed(keyChainSeed).build();
        BigInteger privateKey = deterministicKeyChain.getKeyByPath(parsePath("M"), true).getPrivKey();
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        wallet.importKey(ecKey);

        DeterministicKey key = wallet.currentReceiveKey();

        List<String> mnemonicCode = wallet.getKeyChainSeed().getMnemonicCode();
        String seedCode = String.join(" ", mnemonicCode);

        System.out.println("\n");
        log.warn("walletAddress={}", wallet.currentReceiveAddress().toBase58());//# 钱包地址，和账号一样

        //# 私钥的不同形式
        log.warn("privateKey={}", key.getPrivKey());
        log.warn("encode privateKey={}", key.getPrivateKeyEncoded(networkParam));
        log.warn("hex privateKey={}", key.getPrivateKeyAsHex());
        log.warn("wif privateKey={}", key.getPrivateKeyAsWiF(networkParam));

        log.warn("seedCode={}", seedCode); //# 助记词

        //# 钱包信息
        log.warn("wallet[{}] info: \r\n {}", walletName, wallet.toString());

        System.out.println("\n");
    }


}
