/*
 *
 *  *   @project        disruptor-demo
 *  *   @file           MongoHelper
 *  *   @author         warne
 *  *   @date           19-4-18 下午2:59
 *
 */

package com.warne.disruptor.mongodb;


import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * function：description
 * datetime：2019-04-17 12:48
 * author：warne
 */
@Component
public class MongoHelper {

    final static String collectionName = "order_info";
    final static MongoCollection disruptorCollection = MongoFactory.collection(collectionName);

    public static void save(Document info) {
        disruptorCollection.insertOne(info);
    }

    public static void save(List<Document> infos) {
        disruptorCollection.insertMany(infos);
    }

}
