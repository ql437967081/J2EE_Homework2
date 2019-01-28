package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.GoodsDao;
import edu.nju.wsql.model.Goods;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsDaoImpl extends AbstractHibernateDAO<Goods> implements GoodsDao {
    public GoodsDaoImpl() {
        setClazz(Goods.class);
    }

    @Override
    public double getPrice(long id) {
        return getOneAttribute(id, "price", Double.class);
    }

}
