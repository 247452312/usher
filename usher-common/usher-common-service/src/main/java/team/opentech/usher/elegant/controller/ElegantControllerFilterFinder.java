package team.opentech.usher.elegant.controller;

import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import team.opentech.usher.elegant.ElegantHandler;
import team.opentech.usher.elegant.ElegantHandlerFinder;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月25日 16时25分
 */
public class ElegantControllerFilterFinder implements ElegantHandlerFinder {

    @Resource
    private ElegantControllerFilter filter;

    @Override
    public List<ElegantHandler> find() {
        return Collections.singletonList(filter);
    }
}
