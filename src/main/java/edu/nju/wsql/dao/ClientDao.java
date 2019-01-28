package edu.nju.wsql.dao;

import edu.nju.wsql.exceptions.SystemBusyException;
import edu.nju.wsql.model.Client;

public interface ClientDao {
    String getPassword(long id);

    double getBalance(long id);

    boolean deductBalance(long id, double total) throws SystemBusyException;

    Client findOne(long id);

    /**
     * 用于测试hibernate orm统计函数
     * @return 客户总数
     */
    long getCount();
}
