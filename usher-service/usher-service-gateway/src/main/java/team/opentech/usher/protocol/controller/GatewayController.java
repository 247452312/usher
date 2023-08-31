package team.opentech.usher.protocol.controller;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import team.opentech.usher.enums.InvokeTypeEnum;
import team.opentech.usher.pojo.cqe.InvokeCommand;
import team.opentech.usher.pojo.cqe.InvokeCommandBuilder;
import team.opentech.usher.service.GatewaySdkService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月12日 08时44分
 */
@RestController
@CrossOrigin
public class GatewayController {

    /**
     * 外界http调用的时候的前缀
     */
    private static final String INVOKE = "invoke/";

    @Autowired
    private GatewaySdkService gatewaySdkService;

    /**
     * 外界http调用
     *
     * @param httpServletRequest 请求,暂时没用,日后或许有用
     *
     * @return 向界面返回的值
     */
    @ResponseBody
    @RequestMapping("invoke/**")
    public Object postInvoke(HttpServletRequest httpServletRequest) throws IOException {

        // invoke后面的路径
        String outPath = getOutPath(httpServletRequest);
        // 请求头
        Map<String, String> headerParam = getHeaderParam(httpServletRequest);
        // get参数
        Map<String, String[]> getParams = httpServletRequest.getParameterMap();
        // post参数
        Map<String, Object> postParams = getPostParam(httpServletRequest);
        InvokeCommandBuilder invokeCommandBuilder = new InvokeCommandBuilder();
        invokeCommandBuilder.addPostMap(postParams);
        invokeCommandBuilder.addGetMap(getParams);
        invokeCommandBuilder.addPath(outPath);
        invokeCommandBuilder.addHeader(headerParam);
        invokeCommandBuilder.setType(InvokeTypeEnum.HTTP.getCode());
        InvokeCommand build = invokeCommandBuilder.build();
        return gatewaySdkService.invokeInterface(build);
    }

    /**
     * 获取invoke后面的路径
     *
     * @param httpServletRequest
     *
     * @return
     */
    private String getOutPath(HttpServletRequest httpServletRequest) {
        String requestURI = httpServletRequest.getRequestURI();
        return requestURI.substring(INVOKE.length() + 1);
    }

    /**
     * 获取请求头
     *
     * @param httpServletRequest
     *
     * @return
     */
    private Map<String, String> getHeaderParam(HttpServletRequest httpServletRequest) {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        Map<String, String> result = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            String header = httpServletRequest.getHeader(s);
            result.put(s, header);
        }
        return result;
    }

    /**
     * 获取post参数
     *
     * @param httpServletRequest
     *
     * @return
     */
    private Map<String, Object> getPostParam(HttpServletRequest httpServletRequest) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream()));
        String str = "";

        StringBuilder sb = new StringBuilder();
        while ((str = reader.readLine()) != null) {
            sb.append(str);
        }
        String s = sb.toString();
        return JSONObject.parseObject(s);

    }


}
