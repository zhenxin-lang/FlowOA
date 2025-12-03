package org.openoa.base.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.openoa.base.dto.PageDto;
import org.openoa.base.util.PageUtils;


@Data
public class AbstractPagingRequestDto<TEntity> {
    private PageDto pageDto= PageUtils.getPageDto(new Page());
    private TEntity entity;
}
