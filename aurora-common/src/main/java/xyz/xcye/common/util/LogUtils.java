package xyz.xcye.common.util;

/**
 * 日志打印工具类
 * @author qsyyke
 */


public class LogUtils {
    public static String insert(long uid, int affectedRow, boolean finish, Object... insertObjs) {
        if (finish) {
            return "插入成功=== 生成的uid:" + uid + "  影响行数:" + affectedRow + "  插入的数据:" + generateObjStr(insertObjs);
        }
        return "插入=== " + "待插入的数据:" + generateObjStr(insertObjs);
    }

    public static String update(long uid, int affectedRow, boolean finish, Object... updateObjs) {
        if (finish) {
            return "修改成功=== 被修改的uid:" + uid + "  影响行数:" + affectedRow + "  更新的数据:" + generateObjStr(updateObjs);
        }

        return "修改=== 待修改的uid:" + uid + "  待更新的数据:" + generateObjStr(updateObjs);
    }

    public static String delete(long uid, boolean finish, int affectedRow) {
        if (finish) {
            return "删除成功=== 被删除的uid:" + uid + "  影响行数:" + affectedRow;
        }
        return "删除=== 待删除的uid:" + uid;
    }

    public static String query(Object... queryConditionObjs) {
        return  "查询条件=== " + generateObjStr(queryConditionObjs);
    }

    private static
    StringBuilder generateObjStr(Object[] insertObjs) {
        if (insertObjs == null) {
            return new StringBuilder("");
        }
        StringBuilder insertObjStr = new StringBuilder();
        for (Object insertObj : insertObjs) {
            insertObjStr.append(insertObj).append(",");
        }

        return insertObjStr.delete(insertObjStr.length() - 1,insertObjStr.length());
    }
}
