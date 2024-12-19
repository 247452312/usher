package top.uhyils.usher.extensions.java.lang.String;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.apache.commons.lang3.StringUtils;
import top.uhyils.usher.ustream.UStream;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年06月11日 08时43分
 */
@Extension
public class StringExt {

    public static String[] splitt(@This String str, String separator) {
        return StringUtils.split(str, separator);
    }

    public static UStream<Character> stream(@This String str) {
        return t -> {
            for (int i = 0; i < str.length(); i++) {
                t.accept(str.charAt(i));
            }
        };
    }

    public static void main(String[] args) {
        String str = "123,456,789";
        String[] split = str.splitt(",");
        System.out.println(split.toString());
    }
}
