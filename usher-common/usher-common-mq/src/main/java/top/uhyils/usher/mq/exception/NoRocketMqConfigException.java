package top.uhyils.usher.mq.exception;

/**
 * rocketMq config没有发现的错误
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月18日 09时21分
 */
public class NoRocketMqConfigException extends Exception {

    public NoRocketMqConfigException() {
        super("rocket配置为空");
    }
}
