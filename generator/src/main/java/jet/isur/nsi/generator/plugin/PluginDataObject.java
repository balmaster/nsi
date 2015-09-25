package jet.isur.nsi.generator.plugin;

import java.util.HashMap;
import java.util.Map;

import jet.isur.nsi.generator.data.DataObject;
import jet.isur.nsi.generator.data.Reference;

public class PluginDataObject extends DataObject {
    
    public static final String REF_PREFIX = "REF_";
    
    private Map<String, Reference> refMap = new HashMap<>();

    public Map<String, Reference> getRefMap() {
        return refMap;
    }
}
