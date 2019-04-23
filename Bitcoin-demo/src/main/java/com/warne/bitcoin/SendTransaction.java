/*
 *
 *  *   @project        Bitcoin-demo
 *  *   @file           SendTransaction
 *  *   @author         warne
 *  *   @date           19-4-23 下午3:21
 *
 */

package com.warne.bitcoin;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;

import java.io.File;

/**
 * function：发送交易（转账）
 * datetime：2019-04-23 15:21
 * author：warne
 */
@Slf4j
public class SendTransaction extends IBase {

    public static void main(String[] args) {
        WalletAppKit kit = new WalletAppKit(networkParam, new File("."), walletName);
        kit.startAsync();
        kit.awaitRunning();
        log.warn("==================================================================");
        log.warn("========================= update completed =========================");
        log.warn("==================================================================");

        Coin value = Coin.parseCoin("0.02");
        Address to = Address.fromBase58(networkParam, "monEGqJ2uTNSAbHudVPCCszx7mWMH2oL3z");
        log.info("send money[{}] to address[{}]", value.getValue(), to.toString());

        System.out.println("balance= " + kit.wallet().getBalance().getValue());
        System.out.println("balance= " + kit.wallet().getBalance().toFriendlyString());

        try {
            SendRequest sendRequest = SendRequest.to(to, value);
            Wallet.SendResult result = kit.wallet().sendCoins(sendRequest);
            log.info("coins sent. transaction hash: {}", result.tx.getHashAsString());
        } catch (InsufficientMoneyException e) {
            log.error("send money[{}] to address[{}] error", value.getValue(), to.toString());
            log.error("not enough coins in your wallet. Missing {} satoshis are missing (including fees)", e.missing.getValue());
        }

        ListenableFuture<Coin> balanceFuture = kit.wallet().getBalanceFuture(value, Wallet.BalanceType.AVAILABLE);
        FutureCallback<Coin> callback = new FutureCallback<Coin>() {
            @Override
            public void onSuccess(Coin balance) {
                log.info("----------- **************************************************** ----------------------");
                log.info("----------- coins arrived and the wallet now has enough balance ----------------------");
                log.info("----------- **************************************************** ----------------------");
            }

            @Override
            public void onFailure(Throwable t) {
                log.error(".....................................");
                log.error("@   something went wrong #");
                log.error(".....................................");
            }
        };

        Futures.addCallback(balanceFuture, callback);
    }

    /**
     * 当前发送交易失败，因为刚创建的钱包里没有余额，异常是余额不足~  【not enough coins in your wallet.】
     */

}
