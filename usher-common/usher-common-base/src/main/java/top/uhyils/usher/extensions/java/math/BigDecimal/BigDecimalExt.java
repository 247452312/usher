package top.uhyils.usher.extensions.java.math.BigDecimal;

import java.math.BigDecimal;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

@Extension
public class BigDecimalExt {

    public static BigDecimal plus(@This BigDecimal thiz, BigDecimal twoParam) {
        return thiz.add(twoParam);
    }

    public static void main(String[] args) {
        BigDecimal one = new BigDecimal("1");
        BigDecimal two = new BigDecimal("2");
        BigDecimal three = one + two;
        System.out.println(three);
    }
}
