package team.opentech.usher.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.commons.io.HexDump;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月05日 08时46分
 */
public final class ByteUtil {

    private ByteUtil() {
        throw new RuntimeException("ByteUtil不能创建实例");
    }

    /**
     * 将协议解析为差不多看得懂的东西,但不能用 只能输出
     *
     * @param packet
     *
     * @return
     */
    public static String dump(byte[] packet) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            HexDump.dump(packet, 0, out, 0);
            return out.toString();
        } catch (IOException e) {
            LogUtil.error(e);
            return "";
        }
    }

}
