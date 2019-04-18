package com.warne.disruptor.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * function：tools
 * datetime：2019-03-26 16:37
 * author：warne
 */
public abstract class Tools {

    public final static String now() {
        return LocalDateTime.now().toString();
    }

    /**
     * format json
     *
     * @param obj
     */
    public final static String printJson(Object obj) {
        StringBuffer result = new StringBuffer();
        if (obj == null) {
            System.out.println("result:\r\n" + result + "\r\n");
            return null;
        }
        String json = JSON.toJSONString(obj);
        int level = 0;
        int length = json.length();
        for (int index = 0; index < length; index++) {
            char c = json.charAt(index);
            if (level > 0 && '\n' == result.charAt(result.length() - 1)) {
                result.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    result.append(c + "\n");
                    level++;
                    break;
                case ',':
                    result.append(c + "\n");
                    break;
                case '}':
                case ']':
                    result.append("\n");
                    level--;
                    result.append(getLevelStr(level));
                    result.append(c);
                    break;
                default:
                    result.append(c);
                    break;
            }
        }

        System.out.println("result:\r\n" + result + "\r\n");

        return result.toString();
    }

    /**
     * @param level
     * @return
     */
    private final static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    public final static <T> Map beanToMap(T obj) {
        if (obj == null)
            return Maps.newHashMap();

        BeanMap map = BeanMap.create(obj);
        return map;
    }

    /**
     * 拆分list
     *
     * @param sourceList
     * @param perListSize 每个list的大小
     * @param <T>
     * @return
     */
    public final static <T> List<List<T>> splitList(List<T> sourceList, int perListSize) {
        List<List<T>> targetList = Lists.newArrayList();
        if (sourceList == null || sourceList.size() == 0 || perListSize < 1)
            return targetList;

        int size = sourceList.size();
        if (size <= perListSize) { //# 数据量不足count指定的大小
            targetList.add(sourceList);
        } else {
            int pre = size / perListSize;
            int last = size % perListSize;
            //# 前面pre个集合，每个大小都是count个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = new ArrayList<>();
                for (int j = 0; j < perListSize; j++) {
                    itemList.add(sourceList.get(i * perListSize + j));
                }
                targetList.add(itemList);
            }

            if (last > 0) {
                List<T> itemList = Lists.newArrayList();
                for (int i = 0; i < last; i++) {
                    itemList.add(sourceList.get(pre * perListSize + i));
                }
                targetList.add(itemList);
            }
        }
        return targetList;
    }


    public static class BeanUtil {
        //# 创建过的BeanCopier实例放到缓存中，下次可以直接获取，提升性能
        static final Map<String, BeanCopier> BEAN_COPIERS = Maps.newHashMap();

        private final static String genKey(Class<?> srcClazz, Class<?> destClazz) {
            return srcClazz.getName() + destClazz.getName();
        }

        public final static void copy(Object srcObj, Object destObj) {
            String key = genKey(srcObj.getClass(), destObj.getClass());
            BeanCopier copier = null;
            if (!BEAN_COPIERS.containsKey(key)) {
                copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
                BEAN_COPIERS.put(key, copier);
            } else {
                copier = BEAN_COPIERS.get(key);
            }
            copier.copy(srcObj, destObj, null);
        }
    }

}
