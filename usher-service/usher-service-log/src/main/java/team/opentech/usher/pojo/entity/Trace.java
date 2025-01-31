package team.opentech.usher.pojo.entity;

import team.opentech.usher.pojo.entity.base.AbstractEntity;
import team.opentech.usher.repository.TraceInfoRepository;
import team.opentech.usher.util.Asserts;
import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 18时39分
 */
public class Trace extends AbstractEntity {

    private Long traceId;

    private String rpcId;

    public Trace(Long traceId, String rpcId) {
        this.traceId = traceId;
        this.rpcId = rpcId;
    }

    public List<TraceInfo> findTraceInfoByTraceIdAndRpcId(TraceInfoRepository rep) {
        Asserts.assertTrue(traceId != null, "traceId不能为空");
        Asserts.assertTrue(rpcId != null, "rocId不能为空");
        return rep.findTraceInfoByTraceIdAndRpcId(this);
    }

    public Long traceId() {
        return traceId;
    }

    public String rpcId() {
        return rpcId;
    }
}
