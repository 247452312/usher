package top.uhyils.usher.mysql.pojo.response;

import java.util.ArrayList;
import java.util.List;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.enums.MysqlHandlerStatusEnum;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;
import top.uhyils.usher.mysql.util.MysqlUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月04日 08时20分
 */
public abstract class AbstractMysqlResponse implements MysqlResponse {

    @Override
    public List<byte[]> toByte() {
        MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();
        MysqlHandlerStatusEnum status = mysqlTcpLink.status();
        boolean b = status == MysqlHandlerStatusEnum.FIRST_SIGHT || status == MysqlHandlerStatusEnum.UNKNOW;

        List<byte[]> bytes = toByteNoMarkIndex();
        List<byte[]> result = new ArrayList<>(bytes.size());
        for (byte[] aByte : bytes) {
            List<byte[]> aByteList = new ArrayList<>();
            aByteList.add(MysqlUtil.toBytes(aByte.length + (b ? 1 : 0), 1));
            aByteList.add(new byte[2]);
            long realResponseIndex = mysqlTcpLink.index() + 1;
            aByteList.add(new byte[]{(byte) realResponseIndex});
            if (b) {
                aByteList.add(new byte[]{getFirstByte()});
            }
            aByteList.add(aByte);
            result.add(MysqlUtil.mergeListBytes(aByteList));
            mysqlTcpLink.addIndex();
        }
        return result;

    }


    /**
     * 返回没有前面长度位或标志位的字节组
     *
     * @return
     */
    protected abstract List<byte[]> toByteNoMarkIndex();
}
