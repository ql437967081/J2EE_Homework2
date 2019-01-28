package edu.nju.wsql.service.impl;

import edu.nju.wsql.dao.GoodsDao;
import edu.nju.wsql.model.Goods;
import edu.nju.wsql.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    @Override
    @Transactional
    public List<Goods> getGoodsList() {
        return goodsDao.findAll();
    }
}
