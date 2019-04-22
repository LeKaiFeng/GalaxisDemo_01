package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.BaseService;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractServiceImpl<Model, PK extends Serializable, E>
        implements BaseService<Model, PK, E> {
    /**
     * 子类实现该抽象方法即可
     *
     * @return 公共的Mapper实现类
     */
    protected abstract MyBatisBaseDao<Model, PK, E> getMapper();

    @Override
    public long countByExample(E example) {
        return getMapper().countByExample(example);
    }

    @Override
    public int deleteByExample(E example) {
        return getMapper().deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(PK id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Model record) {
        return getMapper().insert(record);
    }

    @Override
    public int insertSelective(Model record) {
        return getMapper().insertSelective(record);
    }

    @Override
    public List<Model> selectByExample(E example) {
        return getMapper().selectByExample(example);
    }

    @Override
    public Model selectByPrimaryKey(PK id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(Model record, E example) {
        return getMapper().updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(Model record, E example) {
        return getMapper().updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(Model record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Model record) {
        return getMapper().updateByPrimaryKey(record);
    }
}
