package top.uhyils.usher.extensions.java.lang.Object;

import java.util.Optional;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.Self;
import manifold.ext.rt.api.This;

@Extension
public class ObjectExt {

    public static Optional<@Self Object> optional(@This Object obj) {
        return Optional.ofNullable(obj);
    }

    public static void main(String[] args) {
        String s = "hello world";
        Optional<String> optional = s.optional();
        String s1 = optional.get();
        System.out.println(s1);


    }
}
