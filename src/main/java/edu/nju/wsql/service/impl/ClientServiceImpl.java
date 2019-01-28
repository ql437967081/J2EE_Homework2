package edu.nju.wsql.service.impl;

import edu.nju.wsql.dao.ClientDao;
import edu.nju.wsql.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Override
    @Transactional
    public double checkBalance(int id) {
        return clientDao.getBalance(id);
    }
}
