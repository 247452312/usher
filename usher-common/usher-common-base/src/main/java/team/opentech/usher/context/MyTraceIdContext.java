package team.opentech.usher.context;

import team.opentech.usher.UsherThreadLocal;
import team.opentech.usher.enums.LogDetailTypeEnum;
import team.opentech.usher.enums.LogTypeEnum;
import team.opentech.usher.util.IdUtil;
import team.opentech.usher.util.IpUtil;
import team.opentech.usher.util.LogUtil;
import team.opentech.usher.util.SpringUtil;
import team.opentech.usher.util.SupplierWithException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * traceId生成的地方
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月22日 13时05分
 */
public class MyTraceIdContext {

    /**
     * rpc_trace 信息
     */
    public static final String RPC_HEADER_TRACE_INFO = "rpcTraceInfo";

    /**
     * 分隔符
     */
    public static final String PIPE_SYMBOL = "|";

    /**
     * hash起始符号
     */
    public static final String HASH_SYMBOL = "^";

    /**
     * 主线程名称
     */
    private static final String MAIN_THREAD_NAME = "main";

    /**
     * 保存traceId的地方
     */
    private static final UsherThreadLocal<Long> thraceId = new UsherThreadLocal<>();

    /**
     * 保存上一次调用链顺序的地方
     */
    private static final UsherThreadLocal<List<Integer>> traceId = new UsherThreadLocal<>();

    /**
     * 这一次调用的RPCid
     */
    private static final UsherThreadLocal<AtomicInteger> thisTraceId = new UsherThreadLocal<>();

    /**
     * 项目名称
     */
    private volatile static String projectName;

    public static String getProjectName() {
        if (projectName == null) {
            projectName = SpringUtil.getProperty("rpc.application.name", "NoName");
        }
        return projectName;
    }

    public static void setProjectName(String projectName) {
        MyTraceIdContext.projectName = projectName;
    }

    /**
     * 获取日志详情
     *
     * @param logTypeEnum
     * @param startTime
     * @param timeConsuming
     * @param otherInfo
     *
     * @return
     */
    public static String getLogInfo(String traceStr, LogTypeEnum logTypeEnum, long startTime, long timeConsuming, String... otherInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append(LogDetailTypeEnum.LINK.getCode());
        sb.append(getThraceId());
        sb.append(PIPE_SYMBOL);
        sb.append(startTime);
        sb.append(PIPE_SYMBOL);
        sb.append(logTypeEnum.getCode());
        sb.append(PIPE_SYMBOL);
        sb.append(IpUtil.getIp());
        sb.append(PIPE_SYMBOL);
        sb.append(traceStr);
        sb.append(PIPE_SYMBOL);
        String threadName = Thread.currentThread().getName();
        sb.append(threadName);
        sb.append(PIPE_SYMBOL);
        sb.append(getProjectName());
        sb.append(PIPE_SYMBOL);
        sb.append(timeConsuming);
        for (String info : otherInfo) {
            sb.append(PIPE_SYMBOL);
            sb.append(info);
        }
        return sb.toString();
    }

    /**
     * 打印链路跟踪日志
     *
     * @param logTypeEnum
     * @param startTime
     * @param timeConsuming
     * @param otherInfo
     *
     * @return MD5 唯一值
     */
    public static String printLogInfo(String traceStr, LogTypeEnum logTypeEnum, long startTime, long timeConsuming, String... otherInfo) {
        String logInfo = getLogInfo(traceStr, logTypeEnum, startTime, timeConsuming, otherInfo);
        LogUtil.link(logInfo);
        return logInfo;
    }

    /**
     * 获取ThraceId
     *
     * @return
     */
    public static Long getThraceId() {
        if (thraceId.get() == null) {
            if (checkMainThread()) {
                return 1L;
            }
            synchronized (MyTraceIdContext.class) {
                if (thraceId.get() == null) {
                    IdUtil bean = SpringUtil.getBean(IdUtil.class);
                    if (bean == null) {
                        bean = new IdUtil();
                        bean.setCode(1L);
                    }
                    thraceId.set(bean.newId());
                }
            }
        }

        return thraceId.get();
    }

    /**
     * 设置ThraceId
     *
     * @param thraceId
     */
    public static void setThraceId(Long thraceId) {
        MyTraceIdContext.thraceId.set(thraceId);
    }

    /**
     * 获取traceId
     *
     * @return
     */
    public static String getAndAddTraceIdStr() {
        List<Integer> lastTraceIds = getTraceId();
        int traceId = getThisTraceId().getAndAdd(1);
        StringBuilder sb = mergeTraceId(lastTraceIds, traceId);
        return sb.toString();
    }

    /**
     * 获取traceId
     *
     * @return
     */
    public static String getTraceIdStr() {
        List<Integer> lastTraceIds = getTraceId();
        int traceId = getThisTraceId().get();
        StringBuilder sb = mergeTraceId(lastTraceIds, traceId);
        return sb.toString();
    }

    public static void clean() {
        thraceId.remove();
        traceId.remove();
        thisTraceId.remove();
    }

    /**
     * 获取下一个trace应该使用的traceId
     *
     * @return
     */
    public static List<Integer> getNextTraceIds() {
        List<Integer> nestTraceIds = new ArrayList<>(getTraceId());
        AtomicInteger atomicInteger = getThisTraceId();
        nestTraceIds.add(atomicInteger.get());
        return nestTraceIds;
    }

    /**
     * 输出日志
     *
     * @param logType    日志类型
     * @param supplier   要执行的东西
     * @param other      其他加入主日志的东西
     * @param additional 详情日志
     * @param <T>        返回值
     *
     * @return
     */
    public static <T> T printLogInfo(LogTypeEnum logType, SupplierWithException<T> supplier, String[] other, String... additional) throws Throwable {
        String traceIdStr = getAndAddTraceIdStr();
        long startTime = System.currentTimeMillis();
        try {
            return supplier.get();
        } finally {
            long useTime = System.currentTimeMillis() - startTime;
            String hash = null;
            if (additional != null && additional.length != 0) {
                String[] realOther = new String[other.length + 1];
                System.arraycopy(other, 0, realOther, 1, other.length);
                hash = hash(additional);
                realOther[0] = hash;
                other = realOther;
            }
            printLogInfo(traceIdStr, logType, startTime, useTime, other);
            assert additional != null;
            switch (logType) {
                case DB:
                    LogUtil.sql(getThraceId(), hash, useTime, additional[0]);
                    break;
                case MQ:
                    LogUtil.mq(getThraceId(), hash, useTime, additional[0], additional[1]);
                    break;
                case RPC:
                    LogUtil.rpc(getThraceId(), hash, useTime, additional[0], additional[1]);
                    break;
                case TASK:
                    LogUtil.task(getThraceId(), hash, useTime, additional[0], additional[1]);
                    break;
                case CONTROLLER:
                    LogUtil.controller(getThraceId(), hash, useTime, additional[0], additional[1]);
                    break;
                default:
                    break;
            }

        }

    }

    /**
     * 入口.并且输出日志完成后清理掉
     *
     * @param logType    日志类型
     * @param supplier   要执行的东西
     * @param other      其他加入主日志的东西
     * @param additional 详情日志
     * @param <T>        返回值
     *
     * @return
     */
    public static <T> T onlyOnePrintLogInfo(LogTypeEnum logType, SupplierWithException<T> supplier, String[] other, String... additional) {
        try {
            return printLogInfo(logType, supplier, other, additional);
        } catch (Throwable throwable) {
            LogUtil.error(MyTraceIdContext.class, throwable);
            return null;
        } finally {
            MyTraceIdContext.clean();
        }

    }

    private static List<Integer> getTraceId() {
        if (traceId.get() == null) {
            if (checkMainThread()) {
                ArrayList<Integer> integers = new ArrayList<>();
                integers.add(-1);
                return integers;
            }
            synchronized (MyTraceIdContext.class) {
                if (traceId.get() == null) {
                    ArrayList<Integer> value = new ArrayList<>();
                    value.add(1);
                    traceId.set(value);
                    thisTraceId.set(new AtomicInteger(1));
                }
            }
        }
        return traceId.get();
    }

    /**
     * 设置TraceId
     *
     * @param lastTraceIds
     */
    public static void setTraceId(List<Integer> lastTraceIds) {
        traceId.set(lastTraceIds);
        thisTraceId.set(new AtomicInteger(1));
    }

    private static boolean checkMainThread() {
        return MAIN_THREAD_NAME.equals(Thread.currentThread().getName());
    }

    /**
     * traceId
     *
     * @param lastTraceIds 上一层traceId
     * @param traceId      这一层的traceId
     *
     * @return
     */
    private static StringBuilder mergeTraceId(List<Integer> lastTraceIds, int traceId) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lastTraceIds.size(); i++) {
            sb.append(lastTraceIds.get(i));
            sb.append(".");
        }
        sb.append(traceId);
        return sb;
    }

    private static AtomicInteger getThisTraceId() {
        if (thisTraceId.get() == null) {
            if (checkMainThread()) {
                return new AtomicInteger(-1);
            }
            synchronized (MyTraceIdContext.class) {
                if (thisTraceId.get() == null) {
                    AtomicInteger integer = new AtomicInteger(1);
                    thisTraceId.set(integer);
                }
            }
        }
        return thisTraceId.get();
    }

    private static String hash(String[] additionals) {
        StringBuilder sb = new StringBuilder();
        for (String additional : additionals) {
            sb.append(additional);
            sb.append(PIPE_SYMBOL);
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        int hash = sb.toString().hashCode() & 0xFFFFFFF;
        return HASH_SYMBOL + Integer.toUnsignedString(hash, 16);

    }
}
