package com.evol.pichincha.util;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;

@SuppressWarnings("serial")
public class Criterio implements CriteriaSpecification, Serializable {

    private final CriteriaImpl impl;
    private final Criteria criteria;

    private int maxResults = -1;

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
        this.criteria.setMaxResults(maxResults);
    }

    private int firstResult = 0;

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    protected Criterio(String entityName) {
        impl = new CriteriaImpl(entityName, null);
        criteria = impl;
    }

    protected Criterio(String entityName, String alias) {
        impl = new CriteriaImpl(entityName, alias, null);
        criteria = impl;
    }

    protected Criterio(CriteriaImpl impl, Criteria criteria) {
        this.impl = impl;
        this.criteria = criteria;
    }

    public Criteria getExecutableCriteria(Session session) {
        impl.setSession((SessionImplementor) session);
        return impl;
    }

    public static Criterio forEntityName(String entityName) {
        return new Criterio(entityName);
    }

    public static Criterio forEntityName(String entityName, String alias) {
        return new Criterio(entityName, alias);
    }

    @SuppressWarnings("rawtypes")
    public static Criterio forClass(Class clase) {
        return new Criterio(clase.getName());
    }

    @SuppressWarnings("rawtypes")
    public static Criterio forClass(Class clase, String alias) {
        return new Criterio(clase.getName(), alias);
    }

    public Criterio add(Criterion criterion) {
        criteria.add(criterion);
        return this;
    }

    public Criterio addOrder(Order order) {
        criteria.addOrder(order);
        return this;
    }

    public Criterio createAlias(String associationPath, String alias) throws HibernateException {
        criteria.createAlias(associationPath, alias);
        return this;
    }

    public Criterio createCriteria(String associationPath, String alias) throws HibernateException {
        return new Criterio(impl, criteria.createCriteria(associationPath,
                alias));
    }

    public Criterio createCriteria(String associationPath) throws HibernateException {
        return new Criterio(impl, criteria.createCriteria(associationPath));
    }

    public String getAlias() {
        return criteria.getAlias();
    }

    public Criterio setFetchMode(String associationPath, FetchMode mode) throws HibernateException {
        criteria.setFetchMode(associationPath, mode);
        return this;
    }

    public Criterio setProjection(Projection projection) {
        criteria.setProjection(projection);
        return this;
    }

    public Criterio setResultTransformer(ResultTransformer resultTransformer) {
        criteria.setResultTransformer(resultTransformer);
        return this;
    }

    public Criterio createAlias(String associationPath, String alias, JoinType joinType) throws HibernateException {
        criteria.createAlias(associationPath, alias, joinType);
        return this;
    }

    public Criterio createAlias(String associationPath, String alias, JoinType joinType, Criterion withCause) throws HibernateException {
        criteria.createAlias(associationPath, alias, joinType, withCause);
        return this;
    }

    public Criterio createCriteria(String associationPath, JoinType joinType) throws HibernateException {
        return new Criterio(impl, criteria.createCriteria(associationPath, joinType));
    }

    public Criterio createCriteria(String associationPath, String alias, JoinType joinType) throws HibernateException {
        return new Criterio(impl, criteria.createCriteria(associationPath, alias, joinType));
    }

    public Criterio setComment(String comment) {
        criteria.setComment(comment);
        return this;
    }

    public Criterio setLockMode(LockMode lockMode) {
        criteria.setLockMode(lockMode);
        return this;
    }

    public Criterio setLockMode(String alias, LockMode lockMode) {
        criteria.setLockMode(alias, lockMode);
        return this;
    }

/*
    public void calcularDatosParaPaginacion(BusquedaPaginada busquedaPaginada) {
        this.setMaxResults(busquedaPaginada.getCantidadPorPagina().intValue());
        this.setFirstResult((busquedaPaginada.getPaginaActual().intValue() - 1)
                * busquedaPaginada.getCantidadPorPagina().intValue());
    }
*/
    @Override
    public String toString() {
        return criteria.toString();
    }
    
}
