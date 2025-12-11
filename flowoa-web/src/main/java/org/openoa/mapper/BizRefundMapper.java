package org.openoa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.openoa.entity.BizPurchase;
import org.openoa.entity.BizRefund;

@Mapper
public interface BizRefundMapper extends BaseMapper<BizRefund> {
}
