package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.ContentAssembler;
import team.opentech.usher.dao.ContentDao;
import team.opentech.usher.pojo.DO.ContentDO;
import team.opentech.usher.pojo.DTO.ContentDTO;
import team.opentech.usher.pojo.entity.Content;
import team.opentech.usher.repository.ContentRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 公共参数(Content)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分18秒
 */
@Repository
public class ContentRepositoryImpl extends AbstractRepository<Content, ContentDO, ContentDao, ContentDTO, ContentAssembler> implements ContentRepository {

    protected ContentRepositoryImpl(ContentAssembler convert, ContentDao dao) {
        super(convert, dao);
    }


    @Override
    public Content getByName(String honeInfo) {
        ContentDO byName = dao.selectOne(Wrappers.<ContentDO>lambdaQuery().eq(ContentDO::getName, honeInfo));
        return assembler.toEntity(byName);
    }
}
