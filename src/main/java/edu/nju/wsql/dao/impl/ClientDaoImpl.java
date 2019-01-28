package edu.nju.wsql.dao.impl;

import edu.nju.wsql.dao.ClientDao;
import edu.nju.wsql.exceptions.SystemBusyException;
import edu.nju.wsql.model.Client;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;

@Repository
public class ClientDaoImpl extends AbstractHibernateDAO<Client> implements ClientDao {

    public ClientDaoImpl() {
        setClazz(Client.class);
    }

    @Override
    public String getPassword(long id) {
        return getOneAttribute(id, "password", String.class);
    }

    @Override
    public double getBalance(long id) {
        return getOneAttribute(id, "balance", Double.class);
    }

    @Override
    public boolean deductBalance(long id, double total) throws SystemBusyException {
        try {
            Session session = getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaUpdate<Client> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Client.class);
            Root<Client> root = criteriaUpdate.from(Client.class);
            Path<Double> balancePath = root.get("balance");
            criteriaUpdate.set(balancePath,
                    criteriaBuilder.diff(balancePath, total));
            Predicate idEqual = criteriaBuilder.equal(root.get("id"), id);
            Predicate balanceGreater = criteriaBuilder.ge(balancePath, total);
            criteriaUpdate.where(criteriaBuilder.and(idEqual, balanceGreater));
            Query query = session.createQuery(criteriaUpdate);
            return query.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemBusyException();
        }
    }
}
