package jet.nsi.api.data;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jet.nsi.api.model.DictRowAttr;
import jet.nsi.api.model.MetaAttr;
import jet.nsi.api.model.MetaAttrType;

public class NsiConfigAttr {

    private MetaAttrType type;
    private final String valueType;
    private final String name;
    private final String caption;
    private Boolean hidden;
    private final String refDictName;
    private boolean required;
    private boolean readonly;
    private Boolean createOnly;
    private boolean refAttrHidden;
    private boolean persist;

    private List<NsiConfigField> fields = new ArrayList<>();
    @JsonIgnore
    private NsiConfigDict refDict;

    public NsiConfigAttr(@JsonProperty("valueType") String valueType,
                         @JsonProperty("name") String name,
                         @JsonProperty("caption") String caption,
                         @JsonProperty("refDictName") String refDictName) {
        this.valueType = valueType;
        this.name = name;
        this.caption = caption;
        this.refDictName = refDictName;
    }

    public NsiConfigAttr(MetaAttr metaAttr) {
        type = metaAttr.getType();
        valueType = metaAttr.getValueType();
        name = metaAttr.getName();
        caption = metaAttr.getCaption();
        hidden = metaAttr.getHidden() == Boolean.TRUE;
        refDictName = metaAttr.getRefDict();
        required = metaAttr.isRequired();
        readonly = metaAttr.getReadonly() == Boolean.TRUE;
        createOnly = metaAttr.getCreateOnly() == Boolean.TRUE;
        refAttrHidden = metaAttr.isRefAttrHidden();
        persist = metaAttr.isPersist();
    }
    public MetaAttrType getType() {
        return type;
    }
    public String getValueType() {
        return valueType;
    }
    public String getName() {
        return name;
    }
    public String getCaption() {
        return caption;
    }
    public Boolean getHidden() {
        return hidden;
    }
    public List<NsiConfigField> getFields() {
        return fields;
    }
    public void setFields(List<NsiConfigField> fields) {
        this.fields = fields;
    }
    public NsiConfigDict getRefDict() {
        return refDict;
    }
    public void setRefDict(NsiConfigDict refDict) {
        this.refDict = refDict;
    }
    public String getRefDictName() {
        return refDictName;
    }
    public void addField(NsiConfigField field) {
        fields.add(field);
    }
    public boolean isRequired() {
        return required;
    }
    @Override
    public String toString() {
        return "NsiConfigAttr [type=" + type.toString() + ", valueType=" + valueType
                + ", name=" + name + ", caption=" + caption + ", hidden="
                + hidden + ", refDictName=" + refDictName + ", required="
                + required + ", fields=" + fields + ", refDict=" + refDict
                + "]";
    }
    public boolean isReadonly() {
        return readonly;
    }
    public void setRequired(boolean required) {
        this.required = required;
    }
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
    public void setType(MetaAttrType type) {
        this.type = type;
    }
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getCreateOnly() {
        return createOnly;
    }

    public void setCreateOnly(Boolean createOnly) {
        this.createOnly = createOnly;
    }
    public boolean isRefAttrHidden() {
        return refAttrHidden;
    }
    public void setRefAttrHidden(boolean refAttrHidden) {
        this.refAttrHidden = refAttrHidden;
    }
    public boolean isPersist() {
        return persist;
    }
    public void setPersist(boolean persist) {
        this.persist = persist;
    }

    @JsonIgnore
    public DictRowAttr getDefaultValue() {
        DictRowAttr res = null;
        for(NsiConfigField field : fields) {
            if(field.getDefaultValue() != null) {
                if(res == null) {
                    res = new DictRowAttr();
                }
                res.addValue(field.getDefaultValue());
            }
        }
        
        return res;
    }
}
