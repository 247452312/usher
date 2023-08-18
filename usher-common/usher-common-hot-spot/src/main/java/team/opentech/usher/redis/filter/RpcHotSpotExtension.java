package team.opentech.usher.redis.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import team.opentech.usher.enums.ServiceCode;
import team.opentech.usher.pojo.DTO.HotSpotDTO;
import team.opentech.usher.pojo.DTO.base.ServiceResult;
import team.opentech.usher.redis.hotspot.HotSpotRedisPool;
import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.exchange.pojo.content.impl.RpcResponseContentImpl;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.netty.spi.step.template.ConsumerResponseObjectExtension;
import team.opentech.usher.util.ObjectByteUtil;
import team.opentech.usher.util.SpringUtil;
import java.nio.charset.StandardCharsets;
import redis.clients.jedis.Jedis;

/**
 * 如果返回值是缓存信息,那么应该获取真实的数据 然后返回
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年02月14日 12时21分
 */
@RpcSpi(order = Integer.MIN_VALUE)
public class RpcHotSpotExtension implements ConsumerResponseObjectExtension {

    /**
     * 如果返回值是缓存,那么应该获取真实的数据 然后返回
     *
     * @param serviceResult
     * @param rpcData
     *
     * @return
     */
    private static Object getRealServiceResult(ServiceResult<?> serviceResult, RpcData rpcData) {
        RpcResponseContentImpl content = (RpcResponseContentImpl) rpcData.content();
        String json = content.getResponseContent();
        // 如果返回值是缓存
        if (serviceResult.getServiceCode().equals(ServiceCode.SUCCESS_REDIS.getText())) {
            HotSpotRedisPool bean = SpringUtil.getBean(HotSpotRedisPool.class);
            try (Jedis jedis = bean.getJedis()) {
                JSONObject jsonObject = JSON.parseObject(json);
                Object data = jsonObject.get("data");
                HotSpotDTO hotSpotResponse = JSON.parseObject(JSON.toJSONString(data), HotSpotDTO.class);
                String key = hotSpotResponse.getKey();
                String hkey = hotSpotResponse.getHkey();
                byte[] hget = jedis.hget(key.getBytes(StandardCharsets.UTF_8), hkey.getBytes(StandardCharsets.UTF_8));
                return ObjectByteUtil.toObject(hget);
            }
        }
        return serviceResult;
    }

    @Override
    public Object doFilter(Object obj, RpcData rpcData) {
        if (obj instanceof ServiceResult) {
            return getRealServiceResult((ServiceResult<?>) obj, rpcData);
        }
        return obj;
    }
}
