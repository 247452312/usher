package team.opentech.usher.mq.elegant;


import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import team.opentech.usher.elegant.ElegantHandler;
import team.opentech.usher.elegant.ElegantHandlerFinder;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月25日 17时07分
 */
public class ElegantMqFinder implements ElegantHandlerFinder {

    @Resource
    private ElegantMqHandler elegantMqHandler;

    @Override
    public List<ElegantHandler> find() {
        return Collections.singletonList(elegantMqHandler);
    }
}
