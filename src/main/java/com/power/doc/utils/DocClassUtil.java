package com.power.doc.utils;

import com.power.common.util.StringUtil;
import com.power.doc.constants.DocGlobalConstants;
import com.power.doc.model.ApiReturn;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Doc class handle util
 *
 * @author yu 2018//14.
 */
public class DocClassUtil {

    /**
     * Check if it is the basic data type of json data
     *
     * @param type0 java class name
     * @return boolean
     */
    public static boolean isPrimitive(String type0) {
        String type = type0.contains("java.lang") ? type0.substring(type0.lastIndexOf(".") + 1, type0.length()) : type0;
        type = type.toLowerCase();
        switch (type) {
            case "integer":
            case "void":
            case "int":
            case "long":
            case "double":
            case "float":
            case "short":
            case "bigdecimal":
            case "char":
            case "string":
            case "boolean":
            case "byte":
            case "java.sql.timestamp":
            case "java.util.date":
            case "java.time.localdatetime":
            case "localdatetime":
            case "localdate":
            case "java.time.localdate":
            case "java.math.bigdecimal":
            case "java.math.biginteger":
            case "java.io.serializable":
                return true;
            default:
                return false;
        }
    }


    /**
     * Check if it is the basic data array type of json data
     *
     * @param type0 java class name
     * @return boolean
     */
    public static boolean isPrimitiveArray(String type0) {
        String type = type0.contains("java.lang") ? type0.substring(type0.lastIndexOf(".") + 1, type0.length()) : type0;
        type = type.toLowerCase();
        switch (type) {
            case "integer[]":
            case "void":
            case "int[]":
            case "long[]":
            case "double[]":
            case "float[]":
            case "short[]":
            case "bigdecimal[]":
            case "char[]":
            case "string[]":
            case "boolean[]":
            case "byte[]":
                return true;
            default:
                return false;
        }
    }

    /**
     * get class names by generic class name
     *
     * @param returnType generic class name
     * @return array of string
     */
    public static String[] getSimpleGicName(String returnType) {
        if (returnType.contains("<")) {
            String pre = returnType.substring(0, returnType.indexOf("<"));
            if (DocClassUtil.isMap(pre)) {
                return getMapKeyValueType(returnType);
            }
            String type = returnType.substring(returnType.indexOf("<") + 1, returnType.lastIndexOf(">"));
            if (DocClassUtil.isCollection(pre)) {
                return type.split(" ");
            }
            String[] arr = type.split(",");
            return classNameFix(arr);
        } else {
            return returnType.split(" ");
        }
    }

    /**
     * Get a simple type name from a generic class name
     *
     * @param gicName Generic class name
     * @return String
     */
    public static String getSimpleName(String gicName) {
        if (gicName.contains("<")) {
            return gicName.substring(0, gicName.indexOf("<"));
        } else {
            return gicName;
        }
    }

    /**
     * Automatic repair of generic split class names
     *
     * @param arr arr of class name
     * @return array of String
     */
    private static String[] classNameFix(String[] arr) {
        List<String> classes = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();
        int globIndex = 0;
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            if (classes.size() > 0) {
                int index = classes.size() - 1;
                if (!DocUtil.isClassName(classes.get(index))) {
                    globIndex = globIndex + 1;
                    if (globIndex < length) {
                        indexList.add(globIndex);
                        String className = classes.get(index) + "," + arr[globIndex];
                        classes.set(index, className);
                    }
                } else {
                    globIndex = globIndex + 1;
                    if (globIndex < length) {
                        if (DocUtil.isClassName(arr[globIndex])) {
                            indexList.add(globIndex);
                            classes.add(arr[globIndex]);
                        } else {
                            if (!indexList.contains(globIndex) && !indexList.contains(globIndex + 1)) {
                                indexList.add(globIndex);
                                classes.add(arr[globIndex] + "," + arr[globIndex + 1]);
                                globIndex = globIndex + 1;
                                indexList.add(globIndex);
                            }
                        }
                    }
                }
            } else {
                if (DocUtil.isClassName(arr[i])) {
                    indexList.add(i);
                    classes.add(arr[i]);
                } else {
                    if (!indexList.contains(i) && !indexList.contains(i + 1)) {
                        globIndex = i + 1;
                        classes.add(arr[i] + "," + arr[globIndex]);
                        indexList.add(i);
                        indexList.add(i + 1);
                    }
                }
            }
        }
        return classes.toArray(new String[classes.size()]);
    }

    /**
     * get map key and value type name populate into array.
     *
     * @param gName generic class name
     * @return array of string
     */
    public static String[] getMapKeyValueType(String gName) {
        if (gName.contains("<")) {
            String[] arr = new String[2];
            String key = gName.substring(gName.indexOf("<") + 1, gName.indexOf(","));
            String value = gName.substring(gName.indexOf(",") + 1, gName.lastIndexOf(">"));
            arr[0] = key;
            arr[1] = value;
            return arr;
        } else {
            return new String[0];
        }

    }

    /**
     * Convert the parameter types exported to the api document
     *
     * @param javaTypeName java simple typeName
     * @return String
     */
    public static String processTypeNameForParams(String javaTypeName) {
        if (StringUtil.isEmpty(javaTypeName)) {
            return "Object";
        }
        if (javaTypeName.length() == 1) {
            return "Object";
        }
        if (javaTypeName.contains("[]")) {
            return "Array";
        }
        switch (javaTypeName) {
            case "java.lang.String":
                return "String";
            case "string":
                return "String";
            case "char":
                return "String";
            case "java.util.List":
                return "Array";
            case "list":
                return "Array";
            case "java.lang.Integer":
                return "Int";
            case "integer":
                return "Int";
            case "int":
                return "Int";
            case "short":
                return "Short";
            case "java.lang.Short":
                return "Short";
            case "double":
                return "Double";
            case "java.lang.Long":
                return "Long";
            case "long":
                return "Long";
            case "java.lang.Float":
                return "Float";
            case "float":
                return "Float";
            case "bigdecimal":
                return "BigDecimal";
            case "biginteger":
                return "BigDecimal";
            case "java.lang.Boolean":
                return "Boolean";
            case "boolean":
                return "Boolean";
            case "java.util.Byte":
                return "Byte";
            case "byte":
                return "Byte";
            case "map":
                return "Map";
            case "date":
                return "String";
            case "localdatetime":
                return "String";
            case "localdate":
                return "String";
            case "timestamp":
                return "String";
            case "multipartfile":
                return "File";
            default:
                return "Object";
        }

    }

    /**
     * validate java collection
     *
     * @param type java typeName
     * @return boolean
     */
    public static boolean isCollection(String type) {
        switch (type) {
            case "java.util.List":
                return true;
            case "java.util.LinkedList":
                return true;
            case "java.util.ArrayList":
                return true;
            case "java.util.Set":
                return true;
            case "java.util.TreeSet":
                return true;
            case "java.util.HashSet":
                return true;
            case "java.util.SortedSet":
                return true;
            case "java.util.Collection":
                return true;
            case "java.util.ArrayDeque":
                return true;
            case "java.util.PriorityQueue":
                return true;
            default:
                return false;
        }
    }

    /**
     * Check if it is an map
     *
     * @param type java type
     * @return boolean
     */
    public static boolean isMap(String type) {
        switch (type) {
            case "java.util.Map":
                return true;
            case "java.util.SortedMap":
                return true;
            case "java.util.TreeMap":
                return true;
            case "java.util.LinkedHashMap":
                return true;
            case "java.util.HashMap":
                return true;
            case "java.util.concurrent.ConcurrentHashMap":
                return true;
            case "java.util.concurrent.ConcurrentMap":
                return true;
            case "java.util.Properties":
                return true;
            case "java.util.Hashtable":
                return true;
            default:
                return false;
        }
    }

    /**
     * check array
     *
     * @param type type name
     * @return boolean
     */
    public static boolean isArray(String type) {
        return type.contains("[]");
    }

    /**
     * check JSR303
     *
     * @param annotationSimpleName annotation name
     * @return boolean
     */
    public static boolean isJSR303Required(String annotationSimpleName) {
        switch (annotationSimpleName) {
            case "NotNull":
                return true;
            case "NotEmpty":
                return true;
            case "NotBlank":
                return true;
            case "Required":
                return true;
            default:
                return false;
        }
    }

    /**
     * custom tag
     *
     * @param tagName custom field tag
     * @return boolean
     */
    public static boolean isRequiredTag(String tagName) {
        switch (tagName) {
            case "required":
                return true;
            default:
                return false;
        }
    }

    /**
     * ignore tag request field
     *
     * @param tagName custom field tag
     * @return boolean
     */
    public static boolean isIgnoreTag(String tagName) {
        switch (tagName) {
            case "ignore":
                return true;
            default:
                return false;
        }
    }

    /**
     * ignore param of spring mvc
     *
     * @param paramType param type name
     * @return boolean
     */
    public static boolean isMvcIgnoreParams(String paramType) {
        switch (paramType) {
            case "org.springframework.ui.Model":
                return true;
            case "org.springframework.ui.ModelMap":
                return true;
            case "org.springframework.web.servlet.ModelAndView":
                return true;
            case "org.springframework.validation.BindingResult":
                return true;
            case "javax.servlet.http.HttpServletRequest":
                return true;
            case "org.springframework.web.context.request.WebRequest":
                return true;
            case "javax.servlet.http.HttpServletResponse":
                return true;
            default:
                return false;
        }
    }

    /**
     * ignore field type name
     *
     * @param typeName field type name
     * @return String
     */
    public static boolean isIgnoreFieldTypes(String typeName) {
        switch (typeName) {
            case "org.slf4j.Logger":
                return true;
            case "org.apache.ibatis.logging.Log":
                return true;
            default:
                return false;
        }
    }

    /**
     * process return type
     *
     * @param fullyName fully name
     * @return ApiReturn
     */
    public static ApiReturn processReturnType(String fullyName) {
        //System.out.println("返回类的类名："+fullyName);
        ApiReturn apiReturn = new ApiReturn();

        //support web flux
        if (fullyName.startsWith("reactor.core.publisher.Flux")) {
            fullyName = fullyName.replace("reactor.core.publisher.Flux", DocGlobalConstants.JAVA_LIST_FULLY);
            apiReturn.setGenericCanonicalName(fullyName);
            apiReturn.setSimpleName(DocGlobalConstants.JAVA_LIST_FULLY);
            return apiReturn;
        }
        if (fullyName.startsWith("java.util.concurrent.Callable") ||
                fullyName.startsWith("java.util.concurrent.Future") ||
                fullyName.startsWith("java.util.concurrent.CompletableFuture") ||
                fullyName.startsWith("org.springframework.web.context.request.async.DeferredResult") ||
                fullyName.startsWith("org.springframework.web.context.request.async.WebAsyncTask") ||
                fullyName.startsWith("reactor.core.publisher.Mono") ||
                fullyName.startsWith("org.springframework.http.ResponseEntity")) {
            if (fullyName.contains("<")) {
                String[] strings = getSimpleGicName(fullyName);
                String newFullName = strings[0];
                if (newFullName.contains("<")) {
                    apiReturn.setGenericCanonicalName(newFullName);
                    apiReturn.setSimpleName(newFullName.substring(0, newFullName.indexOf("<")));
                } else {
                    apiReturn.setGenericCanonicalName(newFullName);
                    apiReturn.setSimpleName(newFullName);
                }
            } else {
                //directly return Java Object
                apiReturn.setGenericCanonicalName(DocGlobalConstants.JAVA_OBJECT_FULLY);
                apiReturn.setSimpleName(DocGlobalConstants.JAVA_OBJECT_FULLY);
                return apiReturn;
            }

        } else {
            apiReturn.setGenericCanonicalName(fullyName);
            if (fullyName.contains("<")) {
                apiReturn.setSimpleName(fullyName.substring(0, fullyName.indexOf("<")));
            } else {
                apiReturn.setSimpleName(fullyName);
            }
        }
        return apiReturn;
    }

    /**
     * Get annotation simpleName
     *
     * @param annotationName annotationName
     * @return String
     */
    public static String getAnnotationSimpleName(String annotationName) {
        return getClassSimpleName(annotationName);
    }

    /**
     * Get className
     * @param className className
     * @return String
     */
    public static String getClassSimpleName(String className) {
        if (className.contains(".")) {
            int index = className.lastIndexOf(".");
            className = className.substring(index + 1, className.length());
        }
        return className;
    }
}
