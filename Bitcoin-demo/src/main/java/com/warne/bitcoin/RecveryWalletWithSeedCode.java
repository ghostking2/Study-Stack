/*
 *
 *  *   @project        Bitcoin-demo
 *  *   @file           RecveryWalletWithSeedCode
 *  *   @author         warne
 *  *   @date           19-4-23 下午3:26
 *
 */

package com.warne.bitcoin;

import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.math.BigInteger;

import static org.bitcoinj.crypto.HDUtils.parsePath;

/**
 * function：恢复钱包，通过助记词
 * datetime：2019-04-23 15:26
 * author：warne
 */
@Slf4j
public class RecveryWalletWithSeedCode extends IBase {

    public static void main(String[] args) {
        String walletName = "recoveryWallet";
        String seedCode = "garment grace sport worry ability marriage ski ginger arrive script flee gorilla";

        WalletAppKit walletAppKit = new WalletAppKit(networkParam, new File("."), walletName);
        DeterministicSeed seed = null;
        try {
            seed = new DeterministicSeed(seedCode, null, "", Utils.currentTimeSeconds());
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
            log.error("construct seed error. desc:{}", e);
        }
        walletAppKit.restoreWalletFromSeed(seed);

        walletAppKit.setAutoSave(true);

        walletAppKit.startAsync();
        walletAppKit.awaitRunning();

        Wallet wallet = walletAppKit.wallet();
        DeterministicKey key = wallet.currentReceiveKey();

        System.out.println("\n");
        log.warn("privateKey={}", key.getPrivKey());
        log.warn("encode privateKey={}", key.getPrivateKeyEncoded(networkParam));
        log.warn("hex privateKey={}", key.getPrivateKeyAsHex());
        log.warn("wif privateKey={}", key.getPrivateKeyAsWiF(networkParam));

        DeterministicKeyChain deterministicKeyChain = DeterministicKeyChain.builder().seed(seed).build();
        BigInteger privKey = deterministicKeyChain.getKeyByPath(parsePath("M"), true).getPrivKey();
        ECKey ecKey = ECKey.fromPrivate(privKey);

        wallet.importKey(ecKey);

        log.warn("walletAddress={}", wallet.currentReceiveAddress().toBase58());

        log.warn("wallet balance={}", wallet.getBalance().toFriendlyString());
        log.warn("wallet info: \n {} ", wallet.toString());

        System.out.println("\n");
    }

}
