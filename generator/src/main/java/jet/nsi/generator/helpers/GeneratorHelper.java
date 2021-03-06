package jet.nsi.generator.helpers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import jet.nsi.api.data.DictRow;
import jet.nsi.api.data.NsiConfig;
import jet.nsi.api.data.NsiConfigDict;
import jet.nsi.common.data.DictDependencyGraph;
import jet.nsi.generator.GeneratorException;
import jet.nsi.generator.GeneratorParams;
import jet.nsi.generator.StaticContent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratorHelper {
    private static final Logger log = LoggerFactory.getLogger(GeneratorHelper.class);

    private final GeneratorParams params;
    private final NsiConfig config;

    public GeneratorHelper(NsiConfig config, GeneratorParams params) {
        this.config = config;
        this.params = params;
    };

    public DictDependencyGraph getGraph() {
        return getGraph(params.getDictList());
    }

    public DictDependencyGraph getGraph(Collection<String> dictNames) {
        List<NsiConfigDict> dicts = new ArrayList<>();
        for (String dictName : dictNames) {
            NsiConfigDict dict = config.getDict(dictName);
            if (dict != null) {
                dicts.add(dict);
            } else {
                throw new GeneratorException("Nsi dict metadata not found for name " + dictName);
            }
        }
        DictDependencyGraph dictGraph = DictDependencyGraph.build(config, dicts);
        return dictGraph;
    }

    public String getDictListAsString(List<NsiConfigDict> dictList) {
        String listStr = "";
        for (NsiConfigDict dict : dictList) {
            listStr += dict.getName() + ", ";
        }
        return listStr.substring(0, listStr.lastIndexOf(','));
    }

    public int getDictCount(NsiConfigDict dict) {
        if (StaticContent.checkPredefinedNames(dict.getName())) {
            return StaticContent.getPredefinedSize(dict.getName());
        } else {
            return params.getDictCount(dict);
        }
    }

    public List<Long> addIds(NsiConfigDict dict, List<Long> ids, Map<NsiConfigDict, List<Long>> dictsIds) {
        List<Long> targetIds = dictsIds.get(dict);
        if(targetIds == null) {
            targetIds = new ArrayList<Long>(ids.size());
            dictsIds.put(dict, targetIds);
        }
        targetIds.addAll(ids);
        return targetIds;
    }

//    public List<Long> addIds(NsiQuery query, NsiConfigDict dict, List<DictRow> dataList, Map<NsiConfigDict, List<Long>> dictsIds) {
//        if(!dictsIds.containsKey(dict)) {
//            dictsIds.put(dict, new ArrayList<Long>(dataList.size()));
//        }
//        List<Long> ids = dictsIds.get(dict);
//        ids.addAll(getIds(query, dict, dataList));
//        dictsIds.put(dict, ids);
//        return ids;
//    }

    public List<Long> getIds(List<DictRow> dataList) {
        List<Long> ids = new ArrayList<>(dataList.size());
        for (DictRow data : dataList) {
            ids.add(data.getIdAttrLong());
        }
        return ids;
    }


}
