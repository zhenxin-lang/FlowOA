package org.activiti.engine.history;

import org.activiti.engine.query.NativeQuery;

/**
 * Allows querying of {@link HistoricVariableInstance}s via native (SQL) queries
 * @author Henry Yan(http://www.kafeitu.me)
 */
public interface NativeHistoricVariableInstanceQuery
    extends NativeQuery<NativeHistoricVariableInstanceQuery, HistoricVariableInstance> {

}