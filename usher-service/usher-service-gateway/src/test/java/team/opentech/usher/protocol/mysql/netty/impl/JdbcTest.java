package team.opentech.usher.protocol.mysql.netty.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.Test;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月23日 08时30分
 */
public class JdbcTest {

    @Test
    public void testJdbcLink() throws Exception {
        String url = "jdbc:mysql://localhost:3300/my?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false";
        String userName = "root";
        String password = "123456";
        Class.forName("com.mysql.cj.jdbc.Driver");
        //连接数据库
        Connection con = DriverManager.getConnection(url, userName, password);
        Asserts.assertTrue(con != null);
        String sql = "select * from information_schema.`TABLES`";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String abc = resultSet.getString("abc");
            LogUtil.info(abc);
        }

    }

}
