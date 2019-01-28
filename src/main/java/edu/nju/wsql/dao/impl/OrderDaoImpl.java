package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.OrderDao;
import edu.nju.wsql.exceptions.SystemBusyException;
import edu.nju.wsql.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends AbstractHibernateDAO<Order> implements OrderDao {
    public OrderDaoImpl() {
        setClazz(Order.class);
    }

    @Override
    public void insert(Order order) throws SystemBusyException {
        try {
            super.save(order);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemBusyException();
        }
    }

    @Override
    public String getGoodsName(long id) {
        return getOneAttribute(id, "goods.name", String.class);
    }
}
