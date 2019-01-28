package edu.nju.wsql.service;

import edu.nju.wsql.model.enums.PayResult;

import java.util.Map;

public interface OrderService {
    void setOrder(Map<Integer, Integer> order);

    double getTotal();

    boolean isIncentive();

    double getActual();

    void setClient(int cid);

    PayResult pay();
}
