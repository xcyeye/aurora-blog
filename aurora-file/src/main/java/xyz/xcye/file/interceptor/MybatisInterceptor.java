package xyz.xcye.file.interceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.jdbc.PreparedStatementLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;

/**
 * @author qsyyke
 */

@Slf4j
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class MybatisInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        /*try {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

            //0.sql参数获取
            Object parameter = null;
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }

            //1.获取sqlId
            String sqlId = mappedStatement.getId();
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);

            Configuration configuration = mappedStatement.getConfiguration();

            String originalSql = boundSql.getSql();

            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

            for (ParameterMapping parameterMapping : parameterMappings) {
                String property = parameterMapping.getProperty();

            }

            //获取真实的sql语句
            String sql = getSql(configuration, boundSql, sqlId, 0);

            System.out.println("---------------------------");
            System.out.println(sql);
            //2.判断是否有officeId
            if (hasOfficeId(sql,officeIdNames)) {
                log.warn("{}", sql);
            } else {
                log.debug("{}", sql);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

       return invocation.proceed();*/

        try {
            return invocation.proceed();
        } finally {
            Object parameter = null;
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String sql = boundSql.getSql();
            //args contains complete sql
            if (invocation.getArgs().length > 0) {
                try {
                    //getTarget can be obtained. If an error is reported, try invoking getArgs()[0]. Tostring() different SQL connections
                    sql = getTarget(invocation.getArgs()[0]).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //sql collation
            sql = sql.replace("\n", "").replace("\t", "").replaceAll("\\s+", " ");

            System.out.println(sql);
        }
    }


    private static String getSql(Configuration configuration, BoundSql boundSql,
                                 String sqlId, long time) {
        String sql = showSql(configuration, boundSql);
        return sql;
    }

    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(
                    DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    private static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (!parameterMappings.isEmpty() && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration
                    .getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?",
                        Matcher.quoteReplacement(getParameterValue(parameterObject)));

            } else {
                MetaObject metaObject = configuration
                        .newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql
                                .getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        sql = sql.replaceFirst("\\?", "缺失");
                    }//打印出缺失，提醒该参数缺失并防止错位
                }
            }
        }
        return sql;
    }


    /*private final Logger logger = LoggerFactory.getLogger(MybatisInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            BoundSql boundSql = statementHandler.getBoundSql();
            String sql = boundSql.getSql();
            //args contains complete sql
            if (invocation.getArgs().length > 0) {
                try {
                    //getTarget can be obtained. If an error is reported, try invoking getArgs()[0]. Tostring() different SQL connections
                    sql = getTarget(invocation.getArgs()[0]).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //sql collation
            sql = sql.replace("\n", "").replace("\t", "").replaceAll("\\s+", " ");

            System.out.println(sql);
        }
    }*/

    /**
     * Get the proxied object from the proxy object
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    public Object getTarget(Object proxy) throws Exception {
        Field[] fields = proxy.getClass().getSuperclass().getFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
        Field field = proxy.getClass().getSuperclass().getDeclaredField("h");
        field.setAccessible(true);
        //Gets the value of this field in the specified object
        //Gets the value of this field in the Proxy object
        PreparedStatementLogger personProxy = (PreparedStatementLogger) field.get(proxy);
        Field statement = personProxy.getClass().getDeclaredField("statement");
        statement.setAccessible(true);
        return statement.get(personProxy);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}

