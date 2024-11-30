package top.uhyils.usher.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.uhyils.usher.mongo.config.MongoConn;
import top.uhyils.usher.mongo.config.MongoConnPool;
import top.uhyils.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年07月02日 15时52分
 */
@Component
public class MongoManager {

    @Autowired
    private MongoConnPool pool;

    public boolean addFile(String fileName, String base) {
        try {
            MongoConn conn = pool.getConn();
            return conn.addFile(fileName, base);
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
        return Boolean.FALSE;
    }

    public boolean removeFile(String fileName) {
        MongoConn conn = null;
        try {
            conn = pool.getConn();
            return conn.removeFile(fileName);
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
        return Boolean.FALSE;

    }

    public String getFile(String fileName) {
        MongoConn conn = null;
        try {
            conn = pool.getConn();
            return conn.getFile(fileName);
        } catch (Exception e) {
            LogUtil.error(this, e);
        }

        return null;
    }

}
