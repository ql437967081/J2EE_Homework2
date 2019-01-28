package edu.nju.wsql.dao;

import edu.nju.wsql.model.Goods;

import java.util.List;

public interface GoodsDao {
    List<Goods> findAll();

    double getPrice(long id);

    Goods findOne(long id);
}
