package edu.nju.wsql.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractHibernateDAO<T extends Serializable> {
    private Class< T > clazz;

    @Autowired
    private SessionFactory sessionFactory;

    void setClazz(final Class<T> clazzToSet){
        clazz = clazzToSet;
    }

    <R> R getOneAttribute(final long id, final String attribute, final Class<R> attributeClass) {
        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<R> criteriaQuery = criteriaBuilder.createQuery(attributeClass);
        Root<T> root = criteriaQuery.from(clazz);
        Path path = root;
        for (String attr: attribute.split("\\.")) {
            path = path.get(attr);
        }
        criteriaQuery.select(path);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        Query<R> query = session.createQuery(criteriaQuery);
        query.setMaxResults(1);
        List<R> list = query.getResultList();
        return list.get(0);
    }

    public T findOne( final long id ){
        return (T) getCurrentSession().get( clazz, id );
    }
    public List< T > findAll(){
        Session session = getCurrentSession();
        CriteriaQuery<T> criteriaQuery = session.getCriteriaBuilder().createQuery(clazz);
        criteriaQuery.from(clazz);
        return session.createQuery(criteriaQuery).getResultList();
    }

    public void save( final T entity ){
        getCurrentSession().persist( entity );
    }

    public T update( final T entity ){
        return (T) getCurrentSession().merge( entity );
    }

    public void delete( final T entity ){
        getCurrentSession().delete( entity );
    }
    public void deleteById( final long id ){
        final T entity = findOne( id);
        delete( entity );
    }

    public long getCount() {
        Session session = getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(criteriaBuilder.count(root));
        Query<Long> query = session.createQuery(criteriaQuery);
        query.setMaxResults(1);
        List<Long> list = query.getResultList();
        return list.get(0);
    }

    final Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
