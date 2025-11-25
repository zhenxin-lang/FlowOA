package org.openoa.base.interf.anno;

import java.lang.annotation.*;


@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoParse {
}
