package top.uhyils.usher.ql;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.IExpressContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ql引擎池
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 10时27分
 */
public class QlPool {

    private final Integer coreSize;

    private volatile List<QlExpressRunnerCloseable> allQlList;

    private volatile List<QlExpressRunnerCloseable> freeQlList;


    public QlPool(Integer coreSize) {
        this.coreSize = coreSize;
        this.allQlList = new ArrayList<>(coreSize);
        this.freeQlList = new ArrayList<>();
    }

    /**
     * 获取引擎, 通过close关闭
     *
     * @return
     */
    public synchronized QlExpressRunnerCloseable get() {
        if (!freeQlList.isEmpty()) {
            return freeQlList.remove(0);
        }
        if (allQlList.size() < coreSize) {
            QlExpressRunnerCloseable qlExpressRunnerCloseable = new QlExpressRunnerCloseable(this);
            allQlList.add(qlExpressRunnerCloseable);
            return qlExpressRunnerCloseable;
        }
        return new QlExpressRunnerCloseable(null);
    }

    /**
     * 执行ql逻辑代码
     */
    public Object execute(String expressString, IExpressContext<String, Object> context) throws Exception {
        QlExpressRunnerCloseable qlExpressRunnerCloseable = get();
        Object execute = qlExpressRunnerCloseable.execute(expressString, context, null, true, false);
        qlExpressRunnerCloseable.close();
        return execute;
    }

    /**
     * 执行ql逻辑代码
     */
    public Object execute(String expressString, Map<String, Object> context) throws Exception {
        IExpressContext<String, Object> params = new DefaultContext<>();
        for (Entry<String, Object> entry : context.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        QlExpressRunnerCloseable qlExpressRunnerCloseable = get();
        Object execute = qlExpressRunnerCloseable.execute(expressString, params, null, true, false);
        qlExpressRunnerCloseable.close();
        return execute;
    }

    /**
     * 归还引擎
     */
    protected synchronized void recovery(QlExpressRunnerCloseable qlExpressRunnerCloseable) {
        freeQlList.add(qlExpressRunnerCloseable);
    }
}
