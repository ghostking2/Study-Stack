/*
 *
 *  *   @project        Ethereum-demo
 *  *   @file           CreateWallet
 *  *   @author         warne
 *  *   @date           19-4-23 下午4:21
 *
 */

package com.warne.ethereum;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.io.File;
import java.security.SecureRandom;

/**
 * function：创建钱包
 * datetime：2019-04-23 16:21
 * author：warne
 */
@Slf4j
public class CreateWallet extends IBase {

    public static void main(String[] args) throws Exception {
        WalletFile walletFile = null;

        String walletName = "ethereun-wallet";

        File file = new File(".");
        String filePath = file.getAbsolutePath() + File.separator + walletName + WALLET_FILE_SIFFIX;

        byte[] initialEntropy = new byte[16];
        new SecureRandom().nextBytes(initialEntropy);
        String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, "");
        ECKeyPair ecKeyPair = ECKeyPair.create(Hash.sha256(seed));


        walletFile = Wallet.createLight("", ecKeyPair);

        File destination = new File(filePath);
        new ObjectMapper().writeValue(destination, walletFile);
        Bip39Wallet wallet = new Bip39Wallet(walletName, mnemonic);

        log.warn("walletName={}", wallet.getFilename());
        log.warn("address=0x{}", walletFile.getAddress());
        log.warn("privateKey={}", ecKeyPair.getPrivateKey().toString(16));
        log.warn("publicKey={}", ecKeyPair.getPublicKey().toString(16));

        //# 助记词
        log.warn("mnemonic={}", wallet.getMnemonic());

    }

}
