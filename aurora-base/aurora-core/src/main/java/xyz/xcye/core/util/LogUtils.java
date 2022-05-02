package xyz.xcye.core.util;

/**
 * 日志打印工具类
 * @author qsyyke
 */


public class LogUtils {
    /**
     * 打印插入数据的操作
     * @param uid 生成的uid
     * @param affectedRow 影响行数
     * @param finish 是否完成
     * @param success 插入操作是否成功
     * @param insertObjs 数据
     * @return
     */
    public static String insert(long uid, int affectedRow, boolean finish, boolean success, Object... insertObjs) {
        if (finish) {
            if (success) {
                // 插入成功
                return "插入成功=== 生成的uid:" + uid + "  影响行数:" + affectedRow + "  插入的数据:" + generateObjStr(insertObjs);
            }else {
                // 插入失败
                return "插入失败=== 失败原因:" + generateObjStr(insertObjs);
            }
        }
        return "插入=== " + "待插入的数据:" + generateObjStr(insertObjs);
    }

    public static String update(long uid, int affectedRow, boolean finish, boolean success, Object... updateObjs) {
        if (finish) {
            if (success) {
                return "修改成功=== 被修改的uid:" + uid + "  影响行数:" + affectedRow + "  更新的数据:" + generateObjStr(updateObjs);
            }else {
                return "修改失败=== 被修改的uid:" + uid + "  失败原因:" + generateObjStr(updateObjs);
            }
        }

        return "修改=== 待修改的uid:" + uid + "  待更新的数据:" + generateObjStr(updateObjs);
    }

    public static String delete(long uid, boolean finish, boolean success,int affectedRow, Object... deleteObjs) {
        if (finish) {
            if (success) {
                return "删除成功=== 被删除的uid:" + uid + "  影响行数:" + affectedRow;
            }else {
                return "删除失败=== 失败原因:" + generateObjStr(deleteObjs);
            }
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
