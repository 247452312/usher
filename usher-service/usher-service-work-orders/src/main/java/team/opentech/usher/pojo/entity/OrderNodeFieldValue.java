package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.context.MyContext;
import team.opentech.usher.enums.OrderNodeFieldValueTypeEnum;
import team.opentech.usher.pojo.DO.OrderNodeFieldDO;
import team.opentech.usher.pojo.DO.OrderNodeFieldValueDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.util.Asserts;

/**
 * 订单节点属性真实值表(OrderNodeFieldValue)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月31日 19时59分27秒
 */
public class OrderNodeFieldValue extends AbstractDoEntity<OrderNodeFieldValueDO> {

    private OrderNodeField field;

    @Default
    public OrderNodeFieldValue(OrderNodeFieldValueDO data) {
        super(data);
    }

    public OrderNodeFieldValue(Long id) {
        super(id, new OrderNodeFieldValueDO());
    }

    public OrderNodeFieldValue(OrderNodeField field, String realValue) {
        super(parseToNodeFieldValue(field, realValue));
        this.field = field;
    }

    private static OrderNodeFieldValueDO parseToNodeFieldValue(OrderNodeField field, String realValue) {
        OrderNodeFieldValueDO orderNodeFieldValueDO = new OrderNodeFieldValueDO();
        orderNodeFieldValueDO.setNodeFieldId(field.getUnique().map(Identifier::getId).orElseThrow(Asserts::throwOptionalException));
        orderNodeFieldValueDO.setRealValue(realValue);
        return orderNodeFieldValueDO;
    }

    public void assertSelf() {
        Asserts.assertTrue(field != null, "属性不能为空");
        String realValue = data.getRealValue();

        OrderNodeFieldDO orderNodeFieldDO = field.toData().orElseThrow(Asserts::throwOptionalException);
        OrderNodeFieldValueTypeEnum parse = OrderNodeFieldValueTypeEnum.parse(orderNodeFieldDO.getValueType());
        switch (parse) {
            case DATE:
                Asserts.assertTrue(realValue.matches(MyContext.DATE_REGEX), "类型错误,应该为日期类型:" + orderNodeFieldDO.getName());
                break;
            case EMAIL:
                Asserts.assertTrue(realValue.matches(MyContext.EMAIL_REGEX), "类型错误,应该为email类型:" + orderNodeFieldDO.getName());
                break;
            case VALUE:
                Asserts.assertTrue(realValue.matches(MyContext.VALUE_REGEX), "类型错误,应该为数字类型:" + orderNodeFieldDO.getName());
                break;
            case STRING:
                break;
            case ENGLISH:
                Asserts.assertTrue(realValue.matches(MyContext.ENGLISH_REGEX), "类型错误,应该为英文类型:" + orderNodeFieldDO.getName());
                break;
            default:
                Asserts.throwException("类型错误,找不到指定类型:" + orderNodeFieldDO.getName());
                break;

        }
    }
}
