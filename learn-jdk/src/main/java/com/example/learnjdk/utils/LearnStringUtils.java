/**
 * @copyright remark holdings
 */
package com.example.learnjdk.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author kobe_t
 * @date 2018/7/6 16:39
 */
public class LearnStringUtils extends org.springframework.util.StringUtils {

    /**
     * 下线线
     */
    public static final String UNDER_LINE = "_";

    /**
     * 将属性转换为驼峰式命名
     * 比如:SITE_ID将转化为siteId
     *
     * @param property 字段属性
     * @return 驼峰式
     */
    public static String toCamelCase(String property) {
        // 统一转换为小写
        String[] strArray = property.toLowerCase().split(UNDER_LINE);
        StringBuffer sb = new StringBuffer();

        sb.append(strArray[0]);

        for (int i = 1; i < strArray.length; i++) {
            // 每个单词首字母大写
            sb.append(StringUtils.capitalize(strArray[i]));
        }

        return sb.toString();
    }
}
