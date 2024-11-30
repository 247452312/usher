package top.uhyils.usher.mysql.util;

import java.util.ArrayList;
import java.util.List;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.util.CollectionUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年05月16日 14时50分
 */
public final class StringUtil {

    /**
     * sql引用
     */
    private static final String PREFIX = "`";


    /**
     * 去除sql引用标识
     * `name` -> name
     * A.`name` -> A.name
     * `A`.`name` -> A.name
     *
     * @param str
     *
     * @return
     */
    public static String cleanQuotation(String str) {
        if (top.uhyils.usher.util.StringUtil.isEmpty(str)) {
            return str;
        }
        /*1.确认是否存在点(.) 在符号(`)外面,我们需要根据点进行分割*/
        List<Integer> quIndexList = findAllIndex(str, PREFIX);
        if (CollectionUtil.isEmpty(quIndexList)) {
            return str;
        }
        List<Integer> dotIndexList = findAllIndex(str, ".");
        List<Integer> needSplitDot = new ArrayList<>();
        out:
        for (Integer dotIndex : dotIndexList) {
            boolean init = false;
            for (Integer quIndex : quIndexList) {
                if (dotIndex < quIndex && !init) {
                    needSplitDot.add(dotIndex);
                    continue out;
                }
                init = !init;
            }
        }
        List<String> targetStrList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(needSplitDot)) {
            int index = 0;
            for (Integer i : needSplitDot) {
                String substring = str.substring(index, i);
                targetStrList.add(substring);
                index = i + 1;
            }
        } else {
            targetStrList.add(str);
        }
        StringBuilder sb = new StringBuilder();
        for (String s : targetStrList) {
            if (s.startsWith(PREFIX)) {
                s = s.substring(1);
            }
            if (s.endsWith(PREFIX)) {
                s = s.substring(0, s.length() - 1);
            }
            sb.append(s);
            sb.append(".");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    /**
     * 去除sql引用标识
     *
     * @param str
     *
     * @return
     */
    public static String cleanSingleQuotationMark(String str) {
        if (top.uhyils.usher.util.StringUtil.isEmpty(str)) {
            return str;
        }
        if (str.startsWith("'")) {
            str = str.substring(1);
        }
        if (str.endsWith("'")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * 切割出字段的index
     *
     * @param fieldName
     *
     * @return
     */
    public static Integer subFieldIndex(String fieldName) {
        int left = fieldName.indexOf("(");
        int right = fieldName.indexOf(")");
        if (left == -1 || right == -1) {
            return null;
        }
        String index = fieldName.substring(left + 1, right);
        return Integer.parseInt(index);
    }

    @NotNull
    private static List<Integer> findAllIndex(String str, String prefix) {
        List<Integer> quIndexList = new ArrayList<>();
        int index = 0;
        while (true) {
            int quIndex = str.indexOf(prefix, index);
            if (quIndex == -1) {
                break;
            }
            quIndexList.add(quIndex);
            index = quIndex + 1;
        }
        return quIndexList;
    }
}
