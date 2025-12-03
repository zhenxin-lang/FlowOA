package org.openoa.engine.factory;

public interface TagParser<TBean,TParam> {
    TBean parseTag(TParam data);
}
