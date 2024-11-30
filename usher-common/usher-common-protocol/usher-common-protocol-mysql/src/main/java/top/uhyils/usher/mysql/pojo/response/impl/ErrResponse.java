package top.uhyils.usher.mysql.pojo.response.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import top.uhyils.usher.mysql.enums.MysqlErrCodeEnum;
import top.uhyils.usher.mysql.enums.MysqlServerStatusEnum;
import top.uhyils.usher.mysql.pojo.response.AbstractMysqlResponse;
import top.uhyils.usher.mysql.util.MysqlUtil;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 20时39分
 */
public class ErrResponse extends AbstractMysqlResponse {

    /**
     * mysql错误返回代码
     */
    private MysqlErrCodeEnum errCode;

    /**
     * mysql服务器状态
     */
    private MysqlServerStatusEnum status;

    /**
     * 服务器消息
     */
    private String msg;

    private ErrResponse(MysqlErrCodeEnum errCode, MysqlServerStatusEnum status, String msg) {
        super();
        this.errCode = errCode;
        this.status = status;
        this.msg = msg;
    }


    private ErrResponse(MysqlErrCodeEnum errCode, MysqlServerStatusEnum status) {
        super();
        this.errCode = errCode;
        this.status = status;
        this.msg = errCode.getMsg();
    }

    public static ErrResponse build(MysqlErrCodeEnum errCode, MysqlServerStatusEnum status, String msg) {
        return new ErrResponse(errCode, status, msg);
    }

    public static ErrResponse build(String msg) {
        return new ErrResponse(MysqlErrCodeEnum.EE_STAT, MysqlServerStatusEnum.SERVER_STATUS_NO_BACKSLASH_ESCAPES, msg);
    }

    @Override
    public byte getFirstByte() {
        return (byte) 0xFF;
    }

    @Override
    public String toResponseStr() {
        return "服务器报错:" + msg;
    }

    @Override
    public List<byte[]> toByteNoMarkIndex() {
        List<byte[]> listResult = new ArrayList<>();
        // header 恒为 FF
        listResult.add(new byte[]{(byte) 0xff});
        // 错误编号
        byte[] e = errCode.getByteCode();
        listResult.add(e);
        // 服务器状态标志,恒为 '#'
        listResult.add(new byte[]{'#'});
        // 服务器状态
        byte[] e1 = "HY000".getBytes(StandardCharsets.UTF_8);
        ;
        listResult.add(e1);
        // 添加服务器消息 EOF类型的字符串
        byte[] bytes1 = msg.getBytes(StandardCharsets.UTF_8);
        listResult.add(bytes1);

        return Arrays.asList(MysqlUtil.mergeListBytes(listResult));
    }


    public MysqlErrCodeEnum getErrCode() {
        return errCode;
    }

    public MysqlServerStatusEnum getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
