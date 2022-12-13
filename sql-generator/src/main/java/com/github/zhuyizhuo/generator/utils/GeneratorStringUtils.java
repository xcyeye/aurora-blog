package com.github.zhuyizhuo.generator.utils;

/**
 * class: GeneratorStringUtils <br>
 * description: 字符串工具类 <br>
 * time: 2018/7/27 11:35
 *
 * @author yizhuo <br>
 * @version 1.0
 */
public class GeneratorStringUtils {

    /**
     * 获取传入文件全路径的路径信息
     * @param fileFullpath d:/e/a/1.txt
     * @return d:/e/a/
     */
    public static String getFrontPath(String fileFullpath){
        if (isBlank(fileFullpath)){
            return "";
        }
        String fullPath = fileFullpath.replaceAll("\\\\","/");
        return fullPath.substring(0,fullPath.lastIndexOf("/") + 1);
    }

    /**
     * 获取传入文件全路径的文件名称
     * @param fileFullpath d:/e/a/1.txt
     * @return 1.txt
     */
    public static String getFileName(String fileFullpath){
        if (isBlank(fileFullpath)){
            return "";
        }
        String fullPath = fileFullpath.replaceAll("\\\\","/");
        return fullPath.substring(fullPath.lastIndexOf("/") + 1);
    }

    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * GeneratorStringUtils.isBlank(null)      = true
     * GeneratorStringUtils.isBlank("")        = true
     * GeneratorStringUtils.isBlank(" ")       = true
     * GeneratorStringUtils.isBlank("bob")     = false
     * GeneratorStringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     *  not empty and not null and not whitespace
     */
    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 表名转java驼峰命名(首字母大写)
     * @param tableName  e.g mybatis_user_info
     * @param tableRegex 表名的分隔符 例如 _
     * @return MybatisUserInfo
     */
    public static String changeTableName2CamelFirstUpper(String tableName, String tableRegex) {
        String[] split = tableName.split(tableRegex);
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < split.length; i++) {
            s.append(firstUpper(split[i]));
        }
        return s.toString();
    }

    /**
     * 数据库字段名转java驼峰命名(驼峰处转大写,其他字母小写)  连接符colmRegex可指定  默认为_
     * changeColmName2CamelFirstLower("mybatis_user_info","_") =  "mybatisUserInfo"
     * changeColmName2CamelFirstLower("MYBATIS_USER_INFO","_") =  "mybatisUserInfo"
     * @param columnName 列名
     * @param colmRegex 字段的分隔符 例如 _
     * @return java驼峰命名
     */
    public static String changeColmName2CamelFirstLower(String columnName, String colmRegex) {
        String[] split = columnName.split(colmRegex);
        StringBuffer s = new StringBuffer();
        char[] charArray = split[0].toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            s.append(toL(charArray[i]));
        }
        for (int i = 1; i < split.length; i++) {
            s.append(firstUpper(split[i]));
        }
        return s.toString();
    }

    /**
     * 首字母大写 其他字母小写
     * @param str 传入纯字母字符串
     * @return 首字母大写 其他字母小写
     */
    public static String firstUpper(String str) {
        char[] cs = str.toCharArray();
        cs[0] = toU(cs[0]);
        for (int i = 1; i < cs.length; i++) {
            cs[i] = toL(cs[i]);
        }
        return String.valueOf(cs);
    }

    /**
     * 首字母小写
     * @param str 传入纯字母字符串
     * @return 将首字母小写后返回
     */
    public static String firstLower(String str) {
        char[] cs = str.toCharArray();
        cs[0] = toL(cs[0]);
        return String.valueOf(cs);
    }

    /**
     * 字母转大写
     * @param c a-z
     * @return A-Z
     */
    public static char toU(char c) {
        if (c >= 'a' && c <= 'z') {
            return (char)(c - 32);
        }
        return c;
    }

    /**
     * 字母转小写
     * @param c A-Z
     * @return a-z
     */
    public static char toL(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char)(c + 32);
        }
        return c;
    }

}
