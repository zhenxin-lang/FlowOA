package org.openoa.engine.factory;

import org.openoa.base.vo.BusinessDataVo;

public interface ButtonPreOperationService {
    BusinessDataVo buttonsPreOperation(String params, String formCode);
}
