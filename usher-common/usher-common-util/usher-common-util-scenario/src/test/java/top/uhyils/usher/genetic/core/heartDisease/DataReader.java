package top.uhyils.usher.genetic.core.heartDisease;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import top.uhyils.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年03月26日 09时33分
 */
public class DataReader {


    public String read(String path) throws FileNotFoundException {
        FileReader fileReader = new FileReader(path);
        BufferedReader br = new BufferedReader(fileReader);
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            LogUtil.error(this, e);
        }
        return sb.toString();
    }
}
