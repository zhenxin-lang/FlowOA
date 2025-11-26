package org.openoa.base.service.empinfoprovider;

import java.util.Collection;
import java.util.Map;


public interface BpmnEmployeeInfoProviderService {
    Map<String,String> provideEmployeeInfo(Collection<String> empIds);
}
