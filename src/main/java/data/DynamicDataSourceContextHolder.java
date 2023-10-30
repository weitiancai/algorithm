package data;

public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> DB_CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDataSourceKey(String key){
        DB_CONTEXT_HOLDER.set(key);
    }

    public static String getDataSourceKey(){
        return DB_CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceKey(){
        DB_CONTEXT_HOLDER.remove();
    }
}