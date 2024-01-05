package com.packages.ejbs;

import com.packages.entities.Package;
import com.packages.entities.PackageType;
import com.packages.entities.Value;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

@Stateless
public class ValueBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(long id, String sensorType, String value, String dataType, int maxValue, int minValue, long timestamp, Package packageRef) {

        Value value_ = new Value(id, sensorType, value, dataType, maxValue, minValue, timestamp, packageRef);
        entityManager.persist(value_);
    }

    public List<Value> all() {
        return entityManager.createNamedQuery("getAllValues", Value.class).getResultList();
    }

    public Value find(Long ValueId) throws Exception {

        Value value_ = entityManager.find(Value.class, ValueId);
        if (value_ == null) {
            throw new Exception("Value '" + ValueId + "' not found");
        }
        return entityManager.find(Value.class, ValueId);
    }

    public void remove(Long id) throws Exception {
        Value value = find(id);

        if (value != null) {
            entityManager.remove(value);
        }
    }

    public void update(long id, String sensorType, String value, String dataType, int maxValue, int minValue, long timestamp, Package packageRef) throws Exception {
        
        Value value_ = find(id);

        try {
            entityManager.lock(value_, LockModeType.OPTIMISTIC);

            value_.setSensorType(sensorType);
            value_.setValue(value);
            value_.setDataType(dataType);
            value_.setMaxValue(maxValue);
            value_.setMinValue(minValue);
            value_.setTimestamp(timestamp);
            value_.setPackageRef(packageRef);
            
            entityManager.merge(value_);
            
        } catch (ConstraintViolationException e) {
            throw new Exception(e);
        }
    }
}