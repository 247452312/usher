package team.opentech.usher.util;

import team.opentech.usher.pojo.entity.DbInformation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.core.io.ClassPathResource;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年02月08日 15时48分
 */
public final class KproUtil {

    static {
        //初始化vm方法
        Properties p = new Properties();
        // 加载classpath目录下的vm文件
        p.setProperty("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        // 定义字符集
        p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        // 初始化Velocity引擎，指定配置Properties
        Velocity.init(p);
    }

    /**
     * 获取日常
     *
     * @param dbInformation
     *
     * @return
     */
    public static Map<String, String> getMySqlKpro(DbInformation dbInformation) {
        dbInformation.connect();
        dbInformation.fillTableInfos();
        switch (Objects.requireNonNull(dbInformation.getType())) {
            case MYSQL:
                return KproUtil.getMysqlKproResult(dbInformation);
            case ORACLE:
            case SQLITE:
            default:
                Asserts.throwException("暂时不支持数据库类型");
                return null;
        }
    }

    /**
     * 保存到本地
     *
     * @param path
     * @param fileContext
     */
    public static void saveToLocal(String path, Map<String, String> fileContext) {
        path = path.replace("\\", "/");
        if (!path.endsWith("/")) {
            path = path + "/";
        }
        for (Entry<String, String> entry : fileContext.entrySet()) {
            String filePath = entry.getKey();
            String fileContent = entry.getValue();
            try {
                filePath = path + filePath;
                File file = new File(filePath);
                String dir = filePath.substring(0, filePath.lastIndexOf("/"));
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(filePath);
                fw.write(fileContent);
                fw.close();
            } catch (IOException e) {
                LogUtil.error(e);
            }
        }

    }

    /**
     * 获取mysql解析后的结果
     *
     * @return
     */
    private static Map<String, String> getMysqlKproResult(DbInformation dbInformation) {
        List<VelocityContext> contexts = dbInformation.parseToVelocityContext();
        Map<String, String> resultMap = new LinkedHashMap<>();
        // 获取模板列表
        List<String> templates = getTemplateList();
        for (VelocityContext context : contexts) {
            for (String template : templates) {
                // 渲染模板
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, "UTF-8");
                tpl.merge(context, sw);
                String classPath = template;
                String[] keys = context.internalGetKeys();
                for (String key : keys) {
                    String value = context.get(key).toString();
                    classPath = classPath.replace(String.format("{%s}", key), value);
                }
                classPath = classPath.replace(".vm", "");
                resultMap.put(classPath, sw.toString());
            }
        }
        return resultMap;
    }

    /**
     * 获取模板列表
     *
     * @return
     */
    private static List<String> getTemplateList() {
        List<String> results = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("vm");
        try {
            File file = resource.getFile();
            List<File> files = parseFiles(file);
            for (File itemFile : files) {
                String replace = itemFile.getPath().replace(file.getPath(), "").replace("\\", "/");
                results.add("vm" + replace);
            }
            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<File> parseFiles(File file) {
        if (file.isFile()) {
            List<File> files = new ArrayList<>();
            files.add(file);
            return files;
        }
        List<File> result = new ArrayList<>();
        for (File listFile : file.listFiles()) {
            result.addAll(parseFiles(listFile));
        }
        return result;
    }

}
