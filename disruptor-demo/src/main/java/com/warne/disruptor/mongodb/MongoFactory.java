/*
 *
 *  *   @project        disruptor-demo
 *  *   @file           MongoFactory
 *  *   @author         warne
 *  *   @date           19-4-18 下午2:59
 *
 */

package com.warne.disruptor.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * function：description
 * datetime：2019-04-17 11:45
 * author：warne
 */
@Configuration
@PropertySource("classpath:application.properties")
public class MongoFactory {

    static String host;
    static int port;
    static String username;
    static String password;
    static String dbName;

    public static MongoClient mongoClient() {
        return Singleton.INSTANCE.mongoClient();
    }

    public static MongoCollection collection(String collectionName) {
        if (StringUtils.isBlank(collectionName)) {
            throw new RuntimeException("collectionName is empty!");
        }
        return database().getCollection(collectionName);
    }

    public static MongoDatabase database() {
        return mongoClient().getDatabase(dbName);
    }

    enum Singleton {
        INSTANCE;

        MongoClient singletonMongoClient;

        Singleton() {
            build();
        }

        public MongoClient mongoClient() {
            return singletonMongoClient;
        }

        private void build() {
            MongoClientOptions options = MongoClientOptions.builder()
                    .writeConcern(WriteConcern.ACKNOWLEDGED)
                    .socketTimeout(0)
                    .connectionsPerHost(600)
                    .threadsAllowedToBlockForConnectionMultiplier(500)
                    .connectTimeout(6000).build();
            MongoCredential credential = MongoCredential.createCredential(username, "admin", password.toCharArray());

            singletonMongoClient = new MongoClient(new ServerAddress(host), credential, options);
        }
    }

    @Value("${mongodb.host:127.0.0.1}")
    public void setHost(String host) {
        MongoFactory.host = host;
    }

    @Value("${mongodb.port:27017}")
    public void setPort(int port) {
        MongoFactory.port = port;
    }

    @Value("${mongodb.username:root}")
    public void setUsername(String username) {
        MongoFactory.username = username;
    }

    @Value("${mongodb.password:123456}")
    public void setPassword(String password) {
        MongoFactory.password = password;
    }

    @Value("${mongodb.name:disruptor-demo}")
    public void setDbName(String dbName) {
        MongoFactory.dbName = dbName;
    }
}
