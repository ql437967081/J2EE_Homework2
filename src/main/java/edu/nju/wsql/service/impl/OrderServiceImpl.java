package edu.nju.wsql.service.impl;

import edu.nju.wsql.dao.ClientDao;
import edu.nju.wsql.dao.GoodsDao;
import edu.nju.wsql.dao.OrderDao;
import edu.nju.wsql.exceptions.SystemBusyException;
import edu.nju.wsql.model.Client;
import edu.nju.wsql.model.Order;
import edu.nju.wsql.model.enums.PayResult;
import edu.nju.wsql.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Scope("prototype")
@Service
public class OrderServiceImpl implements OrderService {

    private final static double HIGH_AMOUNT = 1000;
    private final static double DISCOUNT = 0.95;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private OrderDao orderDao;

    private Map<Integer, Integer> order;
    private double total;
    private double actual;
    private int cid;

    @Override
    public void setOrder(Map<Integer, Integer> order) {
        this.order = order;
    }

    @Override
    @Transactional
    public double getTotal() {
        total = 0;
        for (Map.Entry<Integer, Integer> entry: order.entrySet()){
            total += goodsDao.getPrice(entry.getKey()) * entry.getValue();
        }
        return total;
    }

    @Override
    public boolean isIncentive() {
        return total >= HIGH_AMOUNT;
    }

    @Override
    public double getActual() {
        actual = total;
        if (isIncentive())
            actual = total * DISCOUNT;
        return actual;
    }

    @Override
    public void setClient(int cid) {
        this.cid = cid;
    }

    @Override
    @Transactional
    public PayResult pay() {
        try {
            if (!clientDao.deductBalance(cid, actual))
                return PayResult.BALANCE_LACK;
            Client client = clientDao.findOne(cid);
            for (Map.Entry<Integer, Integer> entry: order.entrySet()) {
                Order o = new Order();
                o.setClient(client);
                o.setGoods(goodsDao.findOne(entry.getKey()));
                o.setNum(entry.getValue());
                orderDao.insert(o);
            }
            return PayResult.SUCCESS;
        } catch (SystemBusyException e) {
            e.printStackTrace();
        }
        return PayResult.SYSTEM_BUSY;
    }
}
