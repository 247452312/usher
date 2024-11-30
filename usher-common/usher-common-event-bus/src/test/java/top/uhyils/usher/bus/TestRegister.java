package top.uhyils.usher.bus;

import java.util.Arrays;
import java.util.List;
import top.uhyils.usher.pojo.cqe.event.base.BaseEvent;
import top.uhyils.usher.protocol.register.base.Register;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月29日 20时42分
 */
@top.uhyils.usher.annotation.Register
public class TestRegister implements Register {


    @Override
    public List<Class<? extends BaseEvent>> targetEvent() {
        return Arrays.asList(
            TestAEvent.class
        );
    }

    @Override
    public void onEvent(BaseEvent event) {
        if (event instanceof TestAEvent) {
            TestAEvent testEvent = (TestAEvent) event;
            dealTestEvent(testEvent);
        }
    }


    public void dealTestEvent(TestAEvent testEvent) {
        System.out.println("test成功");
        TestAEvent.mark = true;
    }
}
