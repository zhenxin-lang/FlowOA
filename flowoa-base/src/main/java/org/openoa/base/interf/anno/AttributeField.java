package org.openoa.base.interf.anno;

import org.openoa.base.constant.enums.FieldValueTypeEnum;

import java.lang.annotation.*;


@Documented
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AttributeField {
    String name();
    FieldValueTypeEnum type();
    //if multiple values,split with ","
    String value() default "";
    boolean multipleChoice() default false;
}
