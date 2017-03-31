package com.catsic.core.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import android.util.Log;

/**  
  * @Description: 反射工具类 
  * @author wuxianling  
  * @date 2014年7月7日 上午11:42:55    
  */ 
public class ReflectionUtil {

    public static final String CGLIB_CLASS_SEPARATOR = "$$";

    private ReflectionUtil() {
    }

    /**
     * 调用Getter方法.
     */
    public static Object invokeGetter(Object obj, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, new Class[] {},
                new Object[] {});
    }

    /**
     * 调用Setter方法.使用value的Class来查找Setter方法.
     */
    public static void invokeSetter(Object obj, String propertyName,
            Object value) {
        invokeSetter(obj, propertyName, value, null);
    }

    /**
     * 调用Setter方法.
     * 
     * @param propertyType
     *            用于查找Setter方法,为空时使用value的Class替代.
     */
    public static void invokeSetter(Object obj, String propertyName,
            Object value, Class<?> propertyType) {
        Class<?> type = propertyType != null ? propertyType : value.getClass();
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class[] { type },
                new Object[] { value });
    }

    /**
     * 直接读取对象属�?�? 无视private/protected修饰�? 不经过getter函数.
     */
    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + obj + "]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            Log.e("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 直接设置对象属�?�? 无视private/protected修饰�? 不经过setter函数.
     */
    public static void setFieldValue(final Object obj, final String fieldName,
            final Object value) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            Log.e("不可能抛出的异常:{}", e.getMessage());
        }
    }

    /**
     * 对于被cglib AOP过的对象, 取得真实的Class类型.
     */
    public static Class<?> getUserClass(Class<?> clazz) {
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰�? 用于�?��性调用的情况.
     */
    public static Object invokeMethod(final Object obj,
            final String methodName, final Class<?>[] parameterTypes,
            final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method ["
                    + methodName + "] on target [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访�?
     * 
     * 如向上转型到Object仍无法找�? 返回null.
     */
    public static Field getAccessibleField(final Object obj,
            final String fieldName) {
        if (obj == null) {
            throw new IllegalArgumentException("object can't be null");
        }
        if (!StringUtils.isNotBlank(fieldName)) {
            throw new IllegalArgumentException("fieldName can't be blank");
        }
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {// NOSONAR
                // Field不在当前类定�?继续向上转型
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访�? 如向上转型到Object仍无法找�? 返回null.
     * 
     * 用于方法�?��被多次调用的情况. 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object...
     * args)
     */
    public static Method getAccessibleMethod(final Object obj,
            final String methodName, final Class<?>... parameterTypes) {
        if (obj == null) {
            throw new IllegalArgumentException("object can't be null");
        }
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                Method method = superClass.getDeclaredMethod(methodName,
                        parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {// NOSONAR
                // Method不在当前类定�?继续向上转型
            }
        }
        return null;
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找�? 返回Object.class. eg. public UserDao
     * extends HibernateDao<User>
     * 
     * @param clazz
     *            The class to introspect
     * @return the first generic declaration, or Object.class if cannot be
     *         determined
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找�? 返回Object.class.
     * 
     * 如public UserDao extends HibernateDao<User,Long>
     * 
     * @param clazz
     *            clazz The class to introspect
     * @param index
     *            the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     *         determined
     */
    @SuppressWarnings("rawtypes")
    public static Class getSuperClassGenricType(final Class clazz,
            final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            Log.w("warn:",clazz.getSimpleName()+ "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            Log.w("warn:","Index: " + index + ", Size of "
                    + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            Log.w("warn:",clazz.getSimpleName()
                    + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(
            Exception e) {
        if (e instanceof IllegalAccessException
                || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(
                    ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

    /**
     * 通过类路径获得类的实�?
     * 
     * @param packageName
     * @param className
     * @return
     */
    public static Object getInstanceByClassName(String classFullPath) {
        try {
            return Class.forName(classFullPath).newInstance();
        } catch (InstantiationException e) {
            Log.e("Instantiation error:", e.toString());
        } catch (IllegalAccessException e) {
            Log.e("IllegalAccess error:", e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("ClassNotFound error:", e.toString());
        }
        return null;
    }

    public static Object getInstanceByClassName(String packageName,
            String className) {
        return getInstanceByClassName(packageName + "." + className);
    }
    
    public static Class<?> getFieldType(final Object obj, final String fieldName) {
        if(obj == null || StringUtils.isBlank(fieldName)){
            return null;
        }
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + obj + "]");
        }
        return field.getType();
    }
    
    //把字符串类型转换成字段类型对应的�?
    public static Object getFieldValue(Class<?> filedType, String strValue){
        if(filedType == null || StringUtils.isBlank(strValue)){
            return null;
        }
        Object value = strValue;
        if(filedType == String.class){
            //
        }else if(filedType == Integer.class || filedType == int.class){
            if(StringUtils.isNotBlank(strValue)){
                value = Integer.parseInt(float2Long(strValue));
            }
        }else if(filedType == Long.class || filedType == long.class){
            if(StringUtils.isNotBlank(strValue)){
                value = Long.parseLong(float2Long(strValue));
            }
        }else if(filedType == Double.class || filedType == double.class){
            value = Double.parseDouble(strValue);
        }else if(filedType == Boolean.class || filedType == boolean.class){
            value = Boolean.parseBoolean(strValue);
        }else if(filedType == Date.class){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                value = sdf.parse(strValue);
            } catch (ParseException e) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    value =  sdf.parse(strValue);
                } catch (ParseException e1) {
                   //
                }
            }
        }else if(filedType == BigInteger.class){
            value = new BigInteger(strValue.getBytes());
        }else if(filedType == Float.class || filedType == float.class){
            value = Float.parseFloat(strValue);
        }else if(filedType == Byte.class || filedType == byte.class){
            if(StringUtils.isNotBlank(strValue)){
                value = Byte.parseByte(float2Long(strValue));
            }
        }else if(filedType == Short.class || filedType == short.class){
            if(StringUtils.isNotBlank(strValue)){
                value = Short.parseShort(float2Long(strValue));
            }
        }
        return value;
    }
    
   
    
    private static String float2Long(String strValue){
        int pos = strValue.lastIndexOf(".");
        if(pos > -1){
            strValue = strValue.substring(0,pos);
        }
        return strValue;
    }
}
