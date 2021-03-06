package jet.nsi.api.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import jet.nsi.api.NsiServiceException;
import jet.nsi.api.model.BoolExp;
import jet.nsi.api.model.DictRowAttr;
import jet.nsi.api.model.OperationType;

public class BoolExpBuilder {

    private final NsiConfigDict dict;
    private final BoolExpBuilder owner;
    private BoolExp prototype;

    public BoolExpBuilder(NsiConfigDict dict) {
        this(dict, null);
    }

    BoolExpBuilder(NsiConfigDict dict, BoolExpBuilder owner) {
        this.dict = dict;
        this.owner = owner;
    }

    private BoolExp getPrototype() {
        if(prototype == null) {
            prototype = new BoolExp();
        }
        return prototype;
    }

    public List<BoolExp> getExpList() {
        if(getPrototype().getExpList() == null) {
            getPrototype().setExpList(new ArrayList<BoolExp>());
        }
        return getPrototype().getExpList();
    }

    public BoolExpBuilder specialKey(String key) {
        getPrototype().setKey(key);
        return this;
    }

    public BoolExpBuilder key(String key) {
        return key(getDictAttr(key));
    }

    private NsiConfigAttr getDictAttr(String key) {
        NsiConfigAttr result = dict.getAttr(key);
        if(result == null) {
            throw new NsiServiceException("dict %s has not attr %s",dict.getName(),key);
        }
        return result;
    }

    public BoolExpBuilder key(NsiConfigAttr attr) {
        getPrototype().setKey(attr.getName());
        return this;
    }

    public BoolExpBuilder func(String func) {
        getPrototype().setFunc(func);
        return this;
    }

    public BoolExpBuilder and() {
        getPrototype().setFunc(OperationType.AND);
        prototype.setValue(null);
        return this;
    }

    public BoolExpBuilder eq() {
        getPrototype().setFunc(OperationType.EQUALS);
        return this;
    }
    public BoolExpBuilder in() {
        getPrototype().setFunc(OperationType.IN);
        return this;
    }

    public BoolExpBuilder notEq() {
        getPrototype().setFunc(OperationType.NOT_EQUALS);
        return this;
    }
    
    public BoolExpBuilder ge() {
        getPrototype().setFunc(OperationType.GE);
        return this;
    }

    public BoolExpBuilder gt() {
        getPrototype().setFunc(OperationType.GT);
        return this;
    }

    public BoolExpBuilder le() {
        getPrototype().setFunc(OperationType.LE);
        return this;
    }

    public BoolExpBuilder like() {
        getPrototype().setFunc(OperationType.LIKE);
        return this;
    }
    
    public BoolExpBuilder contains() {
        getPrototype().setFunc(OperationType.CONTAINS);
        return this;
    }

    public BoolExpBuilder lt() {
        getPrototype().setFunc(OperationType.LT);
        return this;
    }

    public BoolExpBuilder notAnd() {
        getPrototype().setFunc(OperationType.NOTAND);
        prototype.setValue(null);
        return this;
    }

    public BoolExpBuilder notNull() {
        getPrototype().setFunc(OperationType.NOTNULL);
        prototype.setValue(null);
        return this;
    }

    public BoolExpBuilder notOr() {
        getPrototype().setFunc(OperationType.NOTOR);
        prototype.setValue(null);
        return this;
    }

    public BoolExpBuilder or() {
        getPrototype().setFunc(OperationType.OR);
        prototype.setValue(null);
        return this;
    }

    public BoolExpBuilder value(DictRowAttr value) {
        getPrototype().setValue(value);
        return this;
    }

    public BoolExpBuilder value(String value) {
        getPrototype().setValue(DictRowAttrBuilder.from(value));
        return this;
    }

    public BoolExpBuilder value(DateTime value) {
        getPrototype().setValue(DictRowAttrBuilder.from(value));
        return this;
    }

    public BoolExpBuilder value(Integer value) {
        getPrototype().setValue(DictRowAttrBuilder.from(value));
        return this;
    }

    public BoolExpBuilder value(Long value) {
        getPrototype().setValue(DictRowAttrBuilder.from(value));
        return this;
    }

    public BoolExpBuilder value(Double value) {
        getPrototype().setValue(DictRowAttrBuilder.from(value));
        return this;
    }

    public BoolExpBuilder value(Boolean value) {
        getPrototype().setValue(DictRowAttrBuilder.from(value));
        return this;
    }

    public BoolExpBuilder values(List<String> values) {
        getPrototype().setValue(DictRowAttrBuilder.from(values));
        return this;
    }

    public BoolExpBuilder expList() {
        return new BoolExpBuilder(dict, this);
    }

    public BoolExpBuilder add() {
        return add(getPrototype());
    }

    public BoolExpBuilder end() {
        return owner;
    }

    public BoolExp build() {
        return getPrototype();
    }

    public BoolExpBuilder add(BoolExp filter) {
        BoolExp ownerPrototype = owner.getPrototype();
        if(ownerPrototype.getExpList() == null) {
            ownerPrototype.setExpList(new ArrayList<BoolExp>());
        }
        ownerPrototype.getExpList().add(filter);
        prototype = null;
        return this;
    }

    public BoolExpBuilder deleteMark(boolean value) {
        if(dict.getDeleteMarkAttr() == null) {
            throw new NsiServiceException("dict %s have not deleteMarkAttr",dict.getName());
        }
        return key(dict.getDeleteMarkAttr()).eq().value(value);
    }
    
    public BoolExpBuilder uniqueAttr(Map<String, DictRowAttr> rowAttrs) {
        if(dict.getUniqueAttr() == null) {
            throw new NsiServiceException("dict %s have not uniqueAttr",dict.getName());
        }
        
        for(NsiConfigAttr configAttr : dict.getUniqueAttr()) {
            DictRowAttr ra = rowAttrs.get(configAttr.getName());
            if(ra == null || ra.isEmpty()) {
                throw new NsiServiceException("attr %s is null or empty", configAttr.getName());
            }
            key(configAttr).eq().value(ra).add();
        }
        return this;
    }
}
