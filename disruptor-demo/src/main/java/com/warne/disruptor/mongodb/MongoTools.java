/*
 *
 *  *   @project        disruptor-demo
 *  *   @file           MongoTools
 *  *   @author         warne
 *  *   @date           19-4-18 下午2:59
 *
 */

package com.warne.disruptor.mongodb;


import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

/**
 * function：description
 * datetime：2019-04-17 12:48
 * author：warne
 */
public final class MongoTools {

    final static MongoDatabase database = MongoFactory.database();
    final static String collectionName = "disruptor";


    public static void save(Document info) {
        database.getCollection(collectionName).insertOne(info);
    }

    public static void save(List<Document> infos) {
        database.getCollection(collectionName).insertMany(infos);
    }

    /**
     * 直接初始化数据库连接
     */
    public MongoTools() {
        database.getCollection(collectionName).insertOne(new Document("init", "0"));
    }
}
