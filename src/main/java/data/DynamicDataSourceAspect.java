package data;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Aspect
public class DynamicDataSourceAspect {

    /** 查询方法名前缀 **/
    private final List<String> READ_PREFIX = Arrays.asList("query", "select", "get", "find");

    /** 数据修改方法前缀  **/
    private final List<String> WRITE_PREFIX = Arrays.asList("add", "insert","update","modify", "delete", "remove");

    @Pointcut("execution(* ..*.*(..))")
    public void daoAspect() {}

    @Before("daoAspect()")
    public void switchDataSource(JoinPoint point) {
        String methodName = point.getSignature().getName();
        if (isReadMethod(methodName)) {
            DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.slaverDataSource.name());
        } else if(isWriteMethod(methodName)){
            DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.masterDataSource.name());
        }else {
            DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.masterDataSource.name());
        }
    }

    @After("daoAspect()")
    public void clearDataSource(JoinPoint point) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
    }

    public boolean isReadMethod(String methodName){
        /** 非空判断 **/
        if(StringUtils.isBlank(methodName)){
            return false;
        }
        /** 按照前缀判断方法是否为数据库度操作 **/
        for(String prefix : READ_PREFIX){
            if(StringUtils.isNotBlank(prefix) && methodName.startsWith(prefix)){
                return true;
            }
        }
        return false;
    }

    public boolean isWriteMethod(String methodName){
        if(StringUtils.isBlank(methodName)){
            return false;
        }
        for(String prefix : WRITE_PREFIX){
            if(StringUtils.isNotBlank(prefix) && methodName.startsWith(prefix)){
                return true;
            }
        }
        return false;
    }
}