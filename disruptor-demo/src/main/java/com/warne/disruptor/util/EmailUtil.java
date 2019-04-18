/*
 *
 *  *   @project        disruptor-demo
 *  *   @file           EmailUtil
 *  *   @author         warne
 *  *   @date           4/18/19 5:50 PM
 *
 */

package com.warne.disruptor.util;

import org.bson.Document;

import java.util.List;

/**
 * function：description
 * datetime：2019-04-18 17:50
 * author：warne
 */
public abstract class EmailUtil {

    public final static void sendEmail(Document event) {
        if (event == null)
            return;

        String to = event.getString("to");
        String content = event.getString("content");

        send(to, content);

    }


    public final static void sendEmail(List<Document> eventList) {
        if (eventList == null || eventList.size() == 0)
            return;

        for (Document event : eventList)
            sendEmail(event);
    }


    private static void send(String to, String content) {

    }

}
