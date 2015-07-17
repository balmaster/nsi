package jet.isur.nsi.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jet.isur.nsi.api.data.NsiConfigAttr;
import jet.isur.nsi.api.data.NsiConfigDict;
import jet.isur.nsi.api.data.NsiConfigField;
import jet.isur.nsi.api.model.MetaAttr;
import jet.isur.nsi.api.model.MetaDict;
import jet.isur.nsi.api.model.MetaDictRef;
import jet.isur.nsi.api.model.MetaField;

public class DictSerializer {
    public static MetaDict serialize(NsiConfigDict dict) {
        MetaDict result = new MetaDict();
        result.setAttrs(serializeAttrs(dict.getAttrs()));
        result.setCaption(dict.getCaption());
        result.setName(dict.getName());
        if(dict.getDeleteMarkAttr()!=null) {
            result.setDeleteMarkAttr(dict.getDeleteMarkAttr().getName());
        }
        if(dict.getIsGroupAttr()!=null) {
            result.setDeleteMarkAttr(dict.getIsGroupAttr().getName());
        }
        if(dict.getIdAttr()!=null) {
            result.setDeleteMarkAttr(dict.getIdAttr().getName());
        }
        if(dict.getLastChangeAttr()!=null) {
            result.setDeleteMarkAttr(dict.getLastChangeAttr().getName());
        }
        if(dict.getLastUserAttr()!=null) {
            result.setDeleteMarkAttr(dict.getLastUserAttr().getName());
        }
        if(dict.getParentAttr()!=null) {
            result.setDeleteMarkAttr(dict.getParentAttr().getName());
        }
        if(dict.getOwnerAttr()!=null) {
            result.setDeleteMarkAttr(dict.getOwnerAttr().getName());
        }
        result.setFields(serializeFields(dict.getFields()));
        result.setTable(dict.getTable());
        return result;
    }

    public static List<MetaField> serializeFields(List<NsiConfigField> fields) {
        if(fields == null) {
            return null;
        }
        ArrayList<MetaField> result = new ArrayList<MetaField>(fields.size());
        for (NsiConfigField field : fields) {
            result.add(serializeField(field));
        }
        return result;
    }

    public static MetaField serializeField(NsiConfigField field) {
        MetaField result = new MetaField();
        result.setName(field.getName());
        result.setPrecision(field.getPrecision());
        result.setSize(field.getSize());
        result.setType(field.getType());
        return result;
    }

    public static List<MetaAttr> serializeAttrs(List<NsiConfigAttr> attrs) {
        if(attrs == null) {
            return null;
        }
        ArrayList<MetaAttr> result = new ArrayList<MetaAttr>(attrs.size());
        for (NsiConfigAttr attr : attrs) {
            result.add(serializeAttr(attr));
        }
        return result;
    }

    public static MetaAttr serializeAttr(NsiConfigAttr attr) {
        MetaAttr result = new MetaAttr();
        result.setCaption(attr.getCaption());
        result.setHidden(attr.getHidden());
        result.setName(attr.getName());
        if(attr.getRefDict()!=null) {
            result.setRefDict(attr.getRefDict().getName());
        }
        result.setType(attr.getType());
        result.setValueType(attr.getValueType());
        result.setFields(serializeFieldNames(attr.getFields()));
        return result;
    }

    public static List<String> serializeFieldNames(List<NsiConfigField> fields) {
        ArrayList<String> result = new ArrayList<String>(fields.size());
        for (NsiConfigField field : fields) {
            result.add(field.getName());
        }
        return result;
    }

    public static List<MetaDictRef> serializeRefs(
            Collection<NsiConfigDict> dicts) {
        ArrayList<MetaDictRef> result = new ArrayList<MetaDictRef>(dicts.size());
        for (NsiConfigDict dict : dicts) {
            result.add(serializeRef(dict));
        }
        return result;
    }

    public static MetaDictRef serializeRef(NsiConfigDict dict) {
        MetaDictRef result = new MetaDictRef();
        result.setCaption(dict.getCaption());
        result.setName(dict.getName());
        if(dict.getOwnerAttr()!=null) {
            result.setOwnerDict(dict.getOwnerAttr().getRefDict().getName());
        }
        return result;
    }

}