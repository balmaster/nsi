
package jet.nsi.testkit.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;

import jet.nsi.api.data.DictRow;
import jet.nsi.api.data.NsiQuery;
import jet.nsi.api.model.DictRowAttr;
import jet.nsi.api.model.MetaAttr;
import jet.nsi.api.model.MetaDict;
import jet.nsi.api.model.MetaField;

public class DataUtils {

    public static void assertEqualCommon(MetaDict o1, MetaDict o2) {
        Assert.assertEquals(o1.getCaption(), o2.getCaption());
        Assert.assertEquals(o1.getDeleteMarkAttr(), o2.getDeleteMarkAttr());
        Assert.assertEquals(o1.getIsGroupAttr(), o2.getIsGroupAttr());
        Assert.assertEquals(o1.getIdAttr(), o2.getIdAttr());
        Assert.assertEquals(o1.getLastChangeAttr(), o2.getLastChangeAttr());
        Assert.assertEquals(o1.getLastUserAttr(), o2.getLastUserAttr());
        Assert.assertEquals(o1.getName(), o2.getName());
        Assert.assertEquals(o1.getOwnerAttr(), o2.getOwnerAttr());
        Assert.assertEquals(o1.getOwnerDict(), o2.getOwnerDict());
        Assert.assertEquals(o1.getParentAttr(), o2.getParentAttr());
        Assert.assertEquals(o1.getTable(), o2.getTable());
        
        assertEquals("fields", o1.getFields(), o2.getFields(), new Comparator<MetaField>() {

            @Override
            public int compare(MetaField o1, MetaField o2) {
                assertEquals(o1, o2);
                return 0;
            }

        });
        assertEquals("attrs", o1.getAttrs(), o2.getAttrs(), new Comparator<MetaAttr>() {

            @Override
            public int compare(MetaAttr o1, MetaAttr o2) {
                assertEquals(o1, o2);
                return 0;
            }

        });
    }
    
    public static void assertEqualOwns(MetaDict o1, MetaDict o2) {
        assertEquals("owns", o1.getOwns(), o2.getOwns());
    }
    
    public static void assertEqualInterceptors(MetaDict o1, MetaDict o2) {
        Assert.assertEquals(o1.getInterceptors(), o2.getInterceptors());
    }
    
    public static void assertEqualLabels(MetaDict o1, MetaDict o2) {
        Assert.assertEquals(o1.getLabels(), o2.getLabels());
    }
    
    public static void assertEqualLoadDataAttrs(MetaDict o1, MetaDict o2) {
        Assert.assertEquals(o1.getLoadDataAttrs(), o2.getLoadDataAttrs());
    }
    
    public static void assertEqualCaptionAttrs(MetaDict o1, MetaDict o2) {
        Assert.assertEquals(o1.getCaptionAttrs(), o2.getCaptionAttrs());
    }
    
    public static void assertEqualRefObjectAttrs(MetaDict o1, MetaDict o2) {
        Assert.assertEquals(o1.getRefObjectAttrs(), o2.getRefObjectAttrs());
    }
    
    public static void assertEqualTableObjectAttrs(MetaDict o1, MetaDict o2) {
        Assert.assertEquals(o1.getTableObjectAttrs(), o2.getTableObjectAttrs());
    }
    
    public static void assertEqualAllOptionals(MetaDict o1, MetaDict o2) {
        assertEqualCommon(o1,o2);
        assertEqualRefObjectAttrs(o1, o2);
        assertEqualTableObjectAttrs(o1, o2);
        assertEqualOwns(o1, o2);
        assertEqualInterceptors(o1, o2);
        assertEqualLoadDataAttrs(o1, o2);
        assertEqualCaptionAttrs(o1, o2);
        assertEqualLabels(o1, o2);
    }

    public static <K, V> void assertEquals(final String message,
            final Map<K, V> expected, final Map<K, V> actual) {
        if (expected == actual) {
            return;
        }
        Assert.assertNotNull(message + ": expected is null", expected);
        Assert.assertNotNull(message + "actual is null", actual);
        Assert.assertEquals(message + ": size not match", expected.size(),
                actual.size());
        for (K cur : expected.keySet()) {
            Assert.assertTrue(actual.containsKey(cur));
            Assert.assertTrue(actual.containsValue(expected.get(cur)));
        }
    }

    public static <T> void assertEquals(final String message, final Collection<T> expected, final Collection<T> actual, final Comparator<T> c) {
        if(expected == actual) {
            return;
        }
        Assert.assertNotNull(message + ": expected is null", expected);
        Assert.assertNotNull(message + "actual is null", actual);
        Assert.assertEquals(message + ": size not match", expected.size(), actual.size());
        Iterator<T> ie = expected.iterator();
        Iterator<T> ia = actual.iterator();
        while (ie.hasNext()) {
            T oe = ie.next();
            T oa = ia.next();
            Assert.assertTrue(message + ": item not match",c.compare(oe, oa) == 0);
        }
    }

    public static void assertEquals(MetaAttr o1, MetaAttr o2) {
        if(o1 == o2) {
            return;
        }
        Assert.assertEquals(o1.getType(), o2.getType());
        Assert.assertEquals(o1.getCaption(), o2.getCaption());
        Assert.assertEquals(o1.getName(), o2.getName());
        Assert.assertEquals(o1.getRefDict(), o2.getRefDict());
        Assert.assertEquals(o1.getValueType(), o2.getValueType());
        Assert.assertEquals(o1.getFields(), o2.getFields());
        Assert.assertEquals(o1.getHidden(), o2.getHidden());
        Assert.assertEquals(o1.getCreateOnly(), o2.getCreateOnly());
        Assert.assertEquals(o1.isRequired(), o2.isRequired());
    }

    public static void assertEquals(MetaField o1, MetaField o2) {
        if(o1 == o2) {
            return;
        }
        Assert.assertEquals(o1.getName(), o2.getName());
        Assert.assertEquals(o1.getType(), o2.getType());
        Assert.assertEquals(o1.getPrecision(), o2.getPrecision());
        Assert.assertEquals(o1.getSize(), o2.getSize());
    }

    public static void assertEquals(NsiQuery query, DictRow o1, DictRow o2) {
        assertEquals(query, o1, o2, false);
    }

    public static void assertEquals(NsiQuery query, DictRow o1, DictRow o2, boolean matchOnlyPresent) {
        if(o1 == o2) {
            return;
        }
        Assert.assertNotNull(o1);
        Assert.assertNotNull(o2);
        if(!matchOnlyPresent) {
            Assert.assertEquals(o1.getAttrs().size(), o2.getAttrs().size());
        }
        for ( String attrName : o1.getAttrs().keySet()) {
            DictRowAttr o1AttrValue = o1.getAttr(attrName);
            DictRowAttr o2AttrValue = o2.getAttr(attrName);
            if(!matchOnlyPresent) {
                Assert.assertNotNull("actual attr value not present:" + attrName, o2AttrValue);
            }
            if(matchOnlyPresent && (o1AttrValue == null || o2AttrValue == null) ) {
                continue;
            }
            Assert.assertArrayEquals("actual attr value not match:" + attrName, o1AttrValue.getValues().toArray(), o2AttrValue.getValues().toArray());
            if(o1AttrValue.getRefAttrs() == o2AttrValue.getRefAttrs()) {
                continue;
            }
            if(matchOnlyPresent) {
                if(o1AttrValue.getRefAttrs() != null) {
                    Assert.assertNotNull("actual attr refValues not present:" + attrName, o2AttrValue.getRefAttrs());
                    for ( String rk : o1AttrValue.getRefAttrs().keySet()) {
                        DictRowAttr r1AttrValue = o1AttrValue.getRefAttrs().get(rk);
                        DictRowAttr r2AttrValue = o2AttrValue.getRefAttrs().get(rk);
                        Assert.assertNotNull("actual attr ref value not paresent:" + attrName + ", " + rk, r2AttrValue);
                        Assert.assertArrayEquals("actual attr ref value not match:" + attrName + ", " + rk, r1AttrValue.getValues().toArray(), r2AttrValue.getValues().toArray());
                    }
                }
            } else {
                Assert.assertNotNull("expected attr refValues not present:" + attrName, o1AttrValue.getRefAttrs());
            }
        }
    }

}
