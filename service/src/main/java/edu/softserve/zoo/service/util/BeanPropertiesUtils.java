package edu.softserve.zoo.service.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class to provide special functionality, which is not implemented in {@link BeanUtils}
 *
 * @author Ilya Doroshenko
 */
public class BeanPropertiesUtils {

    /**
     * Copies non-null properties from one bean to another
     *
     * @param src    source bean
     * @param target target bean
     */
    public static void copyPropertiesIgnoringNulls(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullProperties(src));
    }

    private static String[] getNullProperties(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        return emptyNames.toArray(new String[emptyNames.size()]);
    }
}
