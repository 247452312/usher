//package top.uhyils.usher.rpc.exception;
//
//import top.uhyils.usher.rpc.util.ExceptionUtil;
//import top.uhyils.usher.util.Asserts;
//import org.junit.jupiter.api.Test;
//
///**
// * @author uhyils <247452312@qq.com>
// * @date 文件创建日期 2022年06月29日 11时17分
// */
//class TargetExceptionFactoryTest {
//
//    @Test
//    void createExceptionByExceptionStr() throws Throwable {
//String s = ExceptionUtil.parseException(new RuntimeException(new UsherRpcProviderThrowException("hello")));
//UsherRpcProviderThrowException exception = ProviderExceptionFactory.createExceptionByExceptionStr(s);
//        Asserts.assertException(() -> {
//            throw exception;
//        });
//    }
//}
