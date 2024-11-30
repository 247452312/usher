package top.uhyils.usher.mq.util;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;
import top.uhyils.usher.mq.pojo.mqinfo.JvmStartInfoCommand;
import top.uhyils.usher.mq.pojo.mqinfo.JvmStatusInfoCommand;
import top.uhyils.usher.mq.pojo.mqinfo.JvmUniqueMark;

/**
 * Jvm工具
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月18日 12时01分
 */
public class JvmUtil {

    public static JvmStartInfoCommand getJvmStartInfo(JvmUniqueMark jvmUniqueMark) {
        JvmStartInfoCommand jvmStartInfo = new JvmStartInfoCommand();

        List<JvmStatusInfoCommand> statusInfos = JvmStartInfoCommand.getStatusInfos();
        statusInfos.add(getJvmStatusInfo(jvmUniqueMark));
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonUsage = memoryBean.getNonHeapMemoryUsage();

        double heapTotalMem = usage.getMax() / 1024.0 / 1024;
        jvmStartInfo.setHeapTotalMem(heapTotalMem);
        jvmStartInfo.setHeapInitMem(usage.getInit() / 1024.0 / 1024);
        double noHeapTotalMem = nonUsage.getMax() / 1024.0 / 1024;
        jvmStartInfo.setNoHeapTotalMem(noHeapTotalMem);
        jvmStartInfo.setNoHeapInitMem(nonUsage.getInit() / 1024.0 / 1024);
        jvmStartInfo.setJvmTotalMem(heapTotalMem + noHeapTotalMem);
        jvmStartInfo.setJvmUniqueMark(jvmUniqueMark);
        jvmStartInfo.setJvmStatusInfos(statusInfos);
        return jvmStartInfo;
    }

    public static JvmStatusInfoCommand getJvmStatusInfo(JvmUniqueMark jvmUniqueMark) {
        JvmStatusInfoCommand jvmStatusInfo = new JvmStatusInfoCommand();
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonUsage = memoryBean.getNonHeapMemoryUsage();

        double heapUseMem = usage.getUsed() / 1024.0 / 1024;
        jvmStatusInfo.setHeapUseMem(heapUseMem);
        double noHeapUseMem = nonUsage.getUsed() / 1024.0 / 1024;
        jvmStatusInfo.setNoHeapUseMem(noHeapUseMem);
        jvmStatusInfo.setTotalUseMem(heapUseMem + noHeapUseMem);
        jvmStatusInfo.setJvmUniqueMark(jvmUniqueMark);
        jvmStatusInfo.setTime(System.currentTimeMillis());
        return jvmStatusInfo;
    }

}
