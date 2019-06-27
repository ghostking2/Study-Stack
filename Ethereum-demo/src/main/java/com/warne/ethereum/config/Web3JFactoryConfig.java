/*
 *
 *  *   @project        Ethereum-demo
 *  *   @file           Web3JFactoryConfig
 *  *   @author         warne
 *  *   @date           19-4-23 下午4:25
 *
 */

package com.warne.ethereum.config;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * function：description
 * datetime：2019-04-23 16:25
 * author：warne
 */
public class Web3JFactoryConfig {

    public final static String eth_token = "";
    public final static String eth_http = "";
    public final static String eth_http_service = "";

    public static Web3j web3j() {
        return Singleton.INSTANCE.getWeb3j();
    }

    enum Singleton {
        INSTANCE;

        Web3j web3j;

        Singleton() {
            this.web3j = Web3j.build(new HttpService(eth_http_service));
        }

        public Web3j getWeb3j() {
            return this.web3j;
        }
    }

}
