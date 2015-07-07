package jet.isur.nsi.api.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Описание поля таблицы.
 */
public class MetaField implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Уникальное имя поля в таблице.
     */
    private String name;
    /**
     * Тип поля: string | number | date-time | boolean.
     */
    private MetaFieldType type = MetaFieldType.VARCHAR;

    /**
     * Размер.
     */
    private Integer size = 0;
    /**
     * Точность.
     */
    private Integer precision = 0;

    /**
     * Если данный map задан то поле является перечислением.
     */
    private Map<String, String> enumValues;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public MetaFieldType getType() {
        return type;
    }
    public void setType(MetaFieldType type) {
        this.type = type;
    }
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
    public Integer getPrecision() {
        return precision;
    }
    public void setPrecision(Integer precision) {
        this.precision = precision;
    }
    public Map<String, String> getEnumValues() {
        return enumValues;
    }
    public void setEnumValues(Map<String, String> enumValues) {
        this.enumValues = enumValues;
    }


}
