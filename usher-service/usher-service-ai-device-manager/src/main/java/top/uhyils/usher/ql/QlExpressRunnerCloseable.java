package top.uhyils.usher.ql;

import com.ql.util.express.ExpressRunner;
import java.io.Closeable;
import java.io.IOException;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 10时28分
 */
public class QlExpressRunnerCloseable extends ExpressRunner implements Closeable {

    private final QlPool obv;

    public QlExpressRunnerCloseable(QlPool obv) {
        this.obv = obv;
    }

    @Override
    public void close() throws IOException {
        if (obv != null) {
            obv.recovery(this);
        }
    }
}
