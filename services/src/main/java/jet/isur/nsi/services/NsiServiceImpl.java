package jet.isur.nsi.services;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.Timer;
import com.google.common.base.Preconditions;

import jet.isur.nsi.api.NsiGenericService;
import jet.isur.nsi.api.NsiService;
import jet.isur.nsi.api.NsiServiceException;
import jet.isur.nsi.api.data.BoolExpBuilder;
import jet.isur.nsi.api.data.DictMergeOperation;
import jet.isur.nsi.api.data.DictRow;
import jet.isur.nsi.api.data.NsiConfigAttr;
import jet.isur.nsi.api.data.NsiConfigDict;
import jet.isur.nsi.api.data.NsiQuery;
import jet.isur.nsi.api.model.BoolExp;
import jet.isur.nsi.api.model.DictRowAttr;
import jet.isur.nsi.api.model.MetaParamValue;
import jet.isur.nsi.api.model.SortExp;
import jet.isur.nsi.api.sql.SqlDao;
import jet.isur.nsi.api.tx.NsiTransaction;
import jet.isur.nsi.api.tx.NsiTransactionService;
import jet.scdp.metrics.api.Metrics;
import jet.scdp.metrics.api.MetricsDomain;

@MetricsDomain(name = "nsiService")
public class NsiServiceImpl implements NsiService {

    private final Timer dictCountTimer;
    private final Timer dictListTimer;
    private final Timer dictGetTimer;
    private final Timer dictSaveTimer;
    private final Timer dictBatchSaveTimer;
    private final Timer dictDeleteTimer;
    private final Timer dictMergeByExternalId;
    
    public NsiServiceImpl(Metrics metrics) {
        dictCountTimer = metrics.timer(getClass(), "dictCount");
        dictListTimer = metrics.timer(getClass(), "dictList");
        dictGetTimer = metrics.timer(getClass(), "dictGet");
        dictSaveTimer = metrics.timer(getClass(), "dictSave");
        dictBatchSaveTimer = metrics.timer(getClass(), "dictBatchSave");
        dictDeleteTimer = metrics.timer(getClass(), "dictDelete");
        dictMergeByExternalId = metrics.timer(getClass(), "dictMergeByExternalId");
    }

    private static final Logger log = LoggerFactory.getLogger(NsiServiceImpl.class);
    
    private SqlDao sqlDao;
    private NsiGenericService nsiGenericService;
    private NsiTransactionService transactionService;
    
    public void setSqlDao(SqlDao sqlDao) {
        this.sqlDao = sqlDao;
    }

    
    
    public void setNsiGenericService(NsiGenericService nsiGenericService) {
        this.nsiGenericService = nsiGenericService;
    }

    public void setNsiTransactionService(NsiTransactionService transactionService) {
    	this.transactionService = transactionService; 
    }
    
    @Override
    public long dictCount(String requestId, NsiQuery query, BoolExp filter) {
        return dictCount(requestId, query, filter, null, null);
    }

    @Override
    public long dictCount(NsiTransaction tx, NsiQuery query, BoolExp filter) {
        return dictCount(tx, query, filter, null, null);
    }

    @Override
    public long dictCount(String requestId, NsiQuery query, BoolExp filter,
            String sourceQueryName, Collection<MetaParamValue> sourceQueryParams) {
        final Timer.Context t = dictCountTimer.time();
        try {
            return nsiGenericService.dictCount(requestId, query, filter, sqlDao, sourceQueryName, sourceQueryParams);
        } finally {
            t.stop();
        }
    }

    @Override
    public long dictCount(NsiTransaction tx, NsiQuery query, BoolExp filter,
            String sourceQueryName, Collection<MetaParamValue> sourceQueryParams) {
        final Timer.Context t = dictCountTimer.time();
        try {
            return nsiGenericService.dictCount(tx, query, filter, sqlDao, sourceQueryName, sourceQueryParams);
        } finally {
            t.stop();
        }
    }

    @Override
    public List<DictRow> dictList(String requestId, NsiQuery query,
            BoolExp filter, List<SortExp> sortList, long offset, int size) {
        return dictList(requestId, query, filter, sortList, offset, size, null, null);
    }

    @Override
    public List<DictRow> dictList(NsiTransaction tx, NsiQuery query,
            BoolExp filter, List<SortExp> sortList, long offset, int size) {
        return dictList(tx, query, filter, sortList, offset, size, null, null);
    }

    @Override
    public List<DictRow> dictList(String requestId, NsiQuery query,
            BoolExp filter, List<SortExp> sortList, long offset, int size,
            String sourceQueryName, Collection<MetaParamValue> sourceQueryParams) {
        final Timer.Context t = dictListTimer.time();
        try {
            return nsiGenericService.dictList(requestId, query, filter, sortList, offset, size, sqlDao, sourceQueryName, sourceQueryParams);
        } finally {
            t.stop();
        }
    }

    @Override
    public List<DictRow> dictList(NsiTransaction tx, NsiQuery query,
            BoolExp filter, List<SortExp> sortList, long offset, int size,
            String sourceQueryName, Collection<MetaParamValue> sourceQueryParams) {
        final Timer.Context t = dictListTimer.time();
        try {
            return nsiGenericService.dictList(tx, query, filter, sortList, offset, size, sqlDao, sourceQueryName, sourceQueryParams);
        } finally {
            t.stop();
        }
    }

    @Override
    public DictRow dictGet(String requestId, NsiConfigDict dict, DictRowAttr id) {
        final Timer.Context t = dictGetTimer.time();
        try {
            return nsiGenericService.dictGet(requestId, dict, id, sqlDao);
        } finally {
            t.stop();
        }
    }

    @Override
    public DictRow dictGet(NsiTransaction tx, NsiConfigDict dict, DictRowAttr id) {
        final Timer.Context t = dictGetTimer.time();
        try {
            return nsiGenericService.dictGet(tx, dict, id, sqlDao);
        } finally {
            t.stop();
        }
    }

    @Override
    public DictRow dictSave(String requestId, DictRow data) {
        final Timer.Context t = dictSaveTimer.time();
        try {
            return nsiGenericService.dictSave(requestId, data, sqlDao);
        } finally {
            t.stop();
        }
    }

    @Override
    public DictRow dictSave(NsiTransaction tx, DictRow data) {
        final Timer.Context t = dictSaveTimer.time();
        try {
            return nsiGenericService.dictSave(tx, data, sqlDao);
        } finally {
            t.stop();
        }
    }

    @Override
    public DictRow dictDelete(String requestId, NsiConfigDict dict,
            DictRowAttr id, Boolean value) {
        final Timer.Context t = dictDeleteTimer.time();
        try {
            return nsiGenericService.dictDelete(requestId, dict, id, value, sqlDao);
        } finally {
            t.stop();
        }
    }

    @Override
    public DictRow dictDelete(NsiTransaction tx, NsiConfigDict dict,
            DictRowAttr id, Boolean value) {
        final Timer.Context t = dictDeleteTimer.time();
        try {
            return nsiGenericService.dictDelete(tx, dict, id, value, sqlDao);
        } finally {
            t.stop();
        }
    }

    @Override
    public List<DictRow> dictBatchSave(String requestId, List<DictRow> dataList) {
        final Timer.Context t = dictBatchSaveTimer.time();
        try {
            return nsiGenericService.dictBatchSave(requestId, dataList, sqlDao);
        } finally {
            t.stop();
        }
    }

    @Override
    public List<DictRow> dictBatchSave(NsiTransaction tx, List<DictRow> dataList) {
        final Timer.Context t = dictBatchSaveTimer.time();
        try {
            return nsiGenericService.dictBatchSave(tx, dataList, sqlDao);
        } finally {
            t.stop();
        }
    }

   
    public DictMergeOperation dictMergeByExternalId(String requestId, DictRow data, boolean doInsert) {
        final Timer.Context t = dictMergeByExternalId.time();
        try (NsiTransaction tx = transactionService.createTransaction(requestId)) {
            try {
                return dictMergeByExternalId(tx, data, doInsert);
            } catch (Exception e) {
                log.error("dictMergeByExternalId [{},{},{}] -> error", requestId, data.getDict().getName(), doInsert , e);
                tx.rollback();
                throw new NsiServiceException(e.getMessage());
            }
        } catch (Exception e) {
            log.error("dictMergeByExternalId [{},{},{}] -> error", requestId, data.getDict().getName(), doInsert, e);
            throw new NsiServiceException(e.getMessage());
        } finally {
            t.stop();
        }
    }

    
    public DictMergeOperation dictMergeByExternalId(NsiTransaction tx, DictRow data, boolean doInsert) {
        final Timer.Context t = dictMergeByExternalId.time();
        try {
        	NsiConfigDict dict = data.getDict();
        	List<NsiConfigAttr> mAttrs = dict.getMergeExternalAttrs();
        	Preconditions.checkArgument(mAttrs != null && mAttrs.size() > 0, "MergeExternalAttrs is not exist fot dict %s", dict.getName());
        	StringBuilder externalKeyStr = new StringBuilder();
        	
        	BoolExpBuilder fb = dict.filter().and().expList();
        	for(NsiConfigAttr configAttr : mAttrs) {
        		DictRowAttr rowAttr = data.getAttr(configAttr.getName());
        		Preconditions.checkNotNull(rowAttr, "Attributi %s is not exists", configAttr.getName()) ;
        		fb.key(configAttr.getName()).eq().value(rowAttr).add();
        		externalKeyStr.append(rowAttr.getString()).append(" ");
        	}
        	BoolExp filter = fb.end().build();          
        	List<DictRow> rows = dictList(tx, dict.query().addTableObjectAttrs(), filter, null, -1, -1);
        	
        	if(rows == null || rows.size() == 0) {
        		if(doInsert) {
        			data.builder().idAttrNull();
        			dictSave(tx, data);
        			return DictMergeOperation.INSERT;
        		}
        		return DictMergeOperation.NOTHING;
        	} else if(rows.size() == 1) {
        		DictRow mRow = mergeDictRow(data, rows.get(0), dict.getIdAttr().getName());
        		this.dictSave(tx, mRow);
        		return DictMergeOperation.UPDATE;
        	} else {
        		throw new NsiServiceException("Select " +rows.size()+ " rows for external key " +externalKeyStr);
        	}
        } finally {
            t.stop();
        }
    }

	private DictRow mergeDictRow(DictRow source, DictRow target, String idAttrName) {
		Preconditions.checkNotNull(idAttrName, "id attr is null");
		
		for(Map.Entry<String, DictRowAttr> entry : source.getAttrs().entrySet()) {
			if(idAttrName.equals(entry.getKey())) {
				continue;
			}			
			target.setAttr(entry.getKey(), entry.getValue());
		}
		
		return target;
	}
    

}
