package org.openoa.base.service.empinfoprovider;

import java.util.Collection;
import java.util.Map;


public interface BpmnRoleInfoProviderService {
    Map<String,String> provideRoleInfo(Collection<String> roleIds);
    Map<String,String> provideRoleEmployeeInfo(Collection<String> roleIds);
}
