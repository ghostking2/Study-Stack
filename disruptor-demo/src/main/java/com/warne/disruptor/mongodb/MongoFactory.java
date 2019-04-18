package com.warne.disruptor.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
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

    public static MongoDatabase database() {
        return Singleton.INSTANCE.database();
    }

    enum Singleton {
        INSTANCE;

        MongoClient singletonMongoClient;
        MongoDatabase singletonMongoDatabase;

        Singleton() {
            build();
        }

        public MongoClient mongoClient() {
            return singletonMongoClient;
        }

        public MongoDatabase database() {
            return singletonMongoDatabase;
        }

        private void build() {
            MongoClientOptions options = MongoClientOptions.builder()
                    .writeConcern(WriteConcern.ACKNOWLEDGED)
                    .socketTimeout(0)
                    .connectionsPerHost(600)
                    .socketKeepAlive(true)
                    .threadsAllowedToBlockForConnectionMultiplier(500)
                    .connectTimeout(6000).build();
            MongoCredential credential = MongoCredential.createCredential(username, "admin", password.toCharArray());

            singletonMongoClient = new MongoClient(new ServerAddress(host), credential, options);
            singletonMongoDatabase = singletonMongoClient.getDatabase(dbName);
        }
    }


    @Value("${mongodb.host}")
    public void setHost(String host) {
        MongoFactory.host = host;
    }

    @Value("${mongodb.port}")
    public void setPort(int port) {
        MongoFactory.port = port;
    }

    @Value("${mongodb.username}")
    public void setUsername(String username) {
        MongoFactory.username = username;
    }

    @Value("${mongodb.password}")
    public void setPassword(String password) {
        MongoFactory.password = password;
    }

    @Value("${mongodb.name}")
    public void setDbName(String dbName) {
        MongoFactory.dbName = dbName;
    }
}
