package org.openoa.engine.factory;


import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpfService {
    Class<? extends TagParser<?,?>> tagParser();
}
