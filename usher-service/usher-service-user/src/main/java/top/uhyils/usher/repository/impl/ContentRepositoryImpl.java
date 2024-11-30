package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.ContentAssembler;
import top.uhyils.usher.dao.ContentDao;
import top.uhyils.usher.pojo.DO.ContentDO;
import top.uhyils.usher.pojo.DTO.ContentDTO;
import top.uhyils.usher.pojo.entity.Content;
import top.uhyils.usher.repository.ContentRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
