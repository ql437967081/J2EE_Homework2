package edu.nju.wsql.beans;

import edu.nju.wsql.model.Goods;

import java.util.List;
import java.util.Map;

public class CartBean {
    private List<Goods> list;
    private Map<Integer, Integer> order;

    public CartBean() {
    }

    public List<Goods> getList() {
        return list;
    }

    public void setList(List<Goods> list) {
        this.list = list;
    }

    public Map<Integer, Integer> getOrder() {
        return order;
    }

    public void setOrder(Map<Integer, Integer> order) {
        this.order = order;
    }
}
