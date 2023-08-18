package team.opentech.usher.pojo.entity;

import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月23日 17时33分
 */
public class Temp {


    @Test
    public void test() {
        String ss = "tag1||tag2||tag3||";
        String substring = ss.substring(0, ss.lastIndexOf("||") - 1);
        System.out.println(substring);
    }


}
