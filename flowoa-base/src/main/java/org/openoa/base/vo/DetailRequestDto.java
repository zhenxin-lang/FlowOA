package org.openoa.base.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;
import org.openoa.base.dto.PageDto;
import org.openoa.base.util.PageUtils;



@Getter
@Setter
public class DetailRequestDto {
    private PageDto pageDto= PageUtils.getPageDto(new Page());
    private TaskMgmtVO taskMgmtVO;
}
