package org.activiti.engine.repository;

import org.activiti.engine.query.NativeQuery;

/**
 * Allows querying of {@link Model}s via native (SQL) queries
 * @author Henry Yan(http://www.kafeitu.me)
 */
public interface NativeModelQuery extends NativeQuery<NativeModelQuery, Model> {

}