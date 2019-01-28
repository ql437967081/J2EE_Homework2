package edu.nju.wsql.service.impl;

import edu.nju.wsql.dao.ClientDao;
import edu.nju.wsql.dao.OrderDao;
import edu.nju.wsql.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    @Transactional
    public boolean checkIdAndPassword(int id, String password) {
        System.out.println(orderDao.getGoodsName(45));
        System.out.println(clientDao.getCount());
        return password.equals(clientDao.getPassword(id));
    }
}
