package team.opentech.usher.mysql.pojo.response.impl;

import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.enums.MysqlServerStatusEnum;
import team.opentech.usher.mysql.pojo.DTO.FieldInfo;
import team.opentech.usher.mysql.pojo.response.AbstractMysqlResponse;
import team.opentech.usher.mysql.util.MysqlUtil;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.MapUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年11月03日 20时39分
 */
public class ResultSetResponse extends AbstractMysqlResponse {

    /**
     * 列类型
     */
    private final List<FieldInfo> fields;

    /**
     * 要返回的信息
     */
    private final List<Map<String, Object>> jsonInfo;

    /**
     * 数据库状态
     */
    private final MysqlServerStatusEnum serverStatus;

    /**
     * 告警计数
     */
    private final int warnCount;

    public ResultSetResponse(List<FieldInfo> fields, List<Map<String, Object>> jsonInfo, MysqlServerStatusEnum serverStatus) {
        this(fields, jsonInfo, serverStatus, MysqlContent.MYSQL_TCP_INFO.get().warnCount());
    }

    public ResultSetResponse(List<FieldInfo> fields, List<Map<String, Object>> jsonInfo, MysqlServerStatusEnum serverStatus, int warnCount) {
        super();
        this.fields = fields;
        this.jsonInfo = jsonInfo;
        this.serverStatus = serverStatus;
        this.warnCount = warnCount;
    }

    public ResultSetResponse(List<FieldInfo> fields, List<Map<String, Object>> jsonInfo) {
        this(fields, jsonInfo, MysqlServerStatusEnum.SERVER_STATUS_NO_BACKSLASH_ESCAPES, MysqlContent.MYSQL_TCP_INFO.get().warnCount());
    }

    @Override
    public byte getFirstByte() {
        return (byte) 0xFF;
    }

    @Override
    public List<byte[]> toByteNoMarkIndex() {
        List<byte[]> results = new ArrayList<>(5);
        List<byte[]> fields = toFields();
        byte[] eof = toEof();
        List<byte[]> rowDatas = toRowData();
        // todo 这里不正确, 正确的resultSet应该返回多个数组, 1.col条数 2.col信息们 3.eof 4.result们 5.eof
        /*1.col条数*/
        results.add(makeColSizeByteArray(fields.size()));
        /*2.col信息*/
        results.addAll(fields);
        /*3.eof*/
        results.add(eof);
        /*4.result*/
        results.addAll(rowDatas);
        /*5.eof*/
        results.add(eof);
        return results;
    }


    /**
     * 获取列信息
     *
     * @return
     */
    private List<byte[]> toFields() {
        List<byte[]> results = new ArrayList<>(jsonInfo.size());
        for (FieldInfo field : fields) {
            byte[] bytes = field.toFieldBytes();
            results.add(bytes);
        }
        return results;
    }

    /**
     * eof
     *
     * @return
     */
    private byte[] toEof() {
        List<byte[]> results = new ArrayList<>(3);
        results.add(new byte[]{(byte) 0xfe});
        results.add(MysqlUtil.buildFixedInt(2, warnCount));
        results.add(MysqlUtil.buildFixedInt(2, serverStatus.getCode()));
        return MysqlUtil.mergeListBytes(results);
    }

    /**
     * 转换为row数据
     *
     * @return
     */
    private List<byte[]> toRowData() {
        List<byte[]> results = new ArrayList<>();

        for (int i = 0; i < jsonInfo.size(); i++) {
            Map<String, Object> jsonObject = jsonInfo.get(i);
            if (MapUtil.isEmpty(jsonObject)) {
                continue;
            }
            List<byte[]> row = new ArrayList<>();
            for (FieldInfo field : fields) {
                String fieldName = field.getFieldName();
                Object o = jsonObject.get(fieldName);
                byte[] byteData = transObjToByte(o);
                row.add(byteData);
            }
            results.add(MysqlUtil.mergeListBytes(row));
        }
        return results;
    }

    /**
     * resultSet返回中的长度
     *
     * @param size
     *
     * @return
     */
    private byte[] makeColSizeByteArray(int size) {
        return MysqlUtil.toBytes(size);
    }

    /**
     * 转换obj为data
     *
     * @param obj
     *
     * @return
     */
    private byte[] transObjToByte(Object obj) {
        if (obj == null) {
            return new byte[]{(byte) 0xfb};
        }
        if (obj instanceof Date) {
            Date dateValue = (Date) obj;
            return MysqlUtil.mergeLengthCodedBinary(dateValue.getTime());
        }
        if (obj instanceof String) {
            return MysqlUtil.varString((String) obj);
        }
        if (obj instanceof Long) {
            return MysqlUtil.mergeLengthCodedBinary((Long) obj);
        }
        if (obj instanceof Double) {
            return MysqlUtil.mergeLengthCodedBinary((double) obj);
        }
        if (obj instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) obj;
            return MysqlUtil.mergeLengthCodedBinary(bigDecimal.floatValue());
        }
        if (obj instanceof Integer) {
            return MysqlUtil.mergeLengthCodedBinary((int) obj);
        }
        if (obj instanceof Boolean) {
            return MysqlUtil.mergeLengthCodedBinary((Boolean) obj ? 1 : 0);
        }
        Asserts.assertTrue(false, "mysql 数据暂未支持类型:" + obj.getClass().getName());
        return null;
    }

}