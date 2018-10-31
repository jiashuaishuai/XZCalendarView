package com.example.shuaijia.xzcalendarview.utils;

import java.util.List;

/**
 * Created by JiaShuai on 2018/6/7.
 */

public class ObjectNullUtils {
    public static <T> boolean listIsNull(List<T> list) {
        return list == null || list.size() == 0 || list.get(0) == null;
    }

    public static <T extends String> boolean strintIsNull(T s) {
        return s == null || s.isEmpty();
    }
}
