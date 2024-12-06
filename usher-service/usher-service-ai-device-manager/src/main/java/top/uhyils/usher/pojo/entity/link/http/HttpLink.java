package top.uhyils.usher.pojo.entity.link.http;

import java.util.function.Consumer;
import top.uhyils.usher.pojo.cqe.HttpRequestInfo;
import top.uhyils.usher.pojo.entity.link.AbstractLink;
import top.uhyils.usher.util.HttpUtil;
import top.uhyils.usher.util.LogUtil;
import top.uhyils.usher.util.StringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月05日 15时04分
 */
public class HttpLink extends AbstractLink<HttpRequestInfo, Object> {

    /**
     * 地址
     */
    private final String address;

    public HttpLink(String address) {
        this.address = address;
    }


    @Override
    public void onMessage(Consumer<Object> consumer) {
    }

    @Override
    public void request(HttpRequestInfo request) {
    }

    @Override
    public Object requestSync(HttpRequestInfo request) {
        try {
            String url = request.getUrl();
            if (Boolean.TRUE.equals(request.getUseHttps())) {
                if (StringUtil.equalsIgnoreCase(request.getType(), "post")) {
                    return HttpUtil.sendHttpsPost(address + url, request.getHeads(), request.getParams());
                } else if (StringUtil.equalsIgnoreCase(request.getType(), "get")) {
                    return HttpUtil.sendHttpsGet(address + url, request.getHeads());
                }
            } else {
                if (StringUtil.equalsIgnoreCase(request.getType(), "post")) {
                    return HttpUtil.sendHttpPost(address + url, request.getHeads(), request.getParams());
                } else if (StringUtil.equalsIgnoreCase(request.getType(), "get")) {
                    return HttpUtil.sendHttpGet(address + url, request.getHeads());
                }
            }
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
        return null;
    }

    @Override
    public String ip() {
        String[] split = address.split(":");
        return split[0];
    }

    @Override
    public void tryLink() {
        try {
            HttpUtil.ping(ip());
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
    }
}
