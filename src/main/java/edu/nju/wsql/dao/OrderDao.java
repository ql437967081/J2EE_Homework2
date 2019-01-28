package edu.nju.wsql.dao;

import edu.nju.wsql.exceptions.SystemBusyException;
import edu.nju.wsql.model.Order;

public interface OrderDao {
    void insert(Order order) throws SystemBusyException;

    /**
     * 测试hibernate orm外键访问属性
     * @param id 订单id
     * @return 订单中商品的名称
     */
    String getGoodsName(long id);
}
