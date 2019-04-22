package com.galaxis.wcs.yanfeng.database.oes.service.impl;

import com.galaxis.wcs.yanfeng.database.oes.domain.Kt;
import com.galaxis.wcs.yanfeng.database.oes.domain.KtExample;
import com.galaxis.wcs.yanfeng.database.oes.mapper.KtMapper;
import com.galaxis.wcs.yanfeng.database.oes.mapper.MyBatisBaseDao;
import com.galaxis.wcs.yanfeng.database.oes.service.KtService;
import com.galaxis.wcs.yanfeng.util.Config;
import com.galaxis.wcs.yanfeng.util.Constance;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class KtServiceImpl extends AbstractServiceImpl<Kt, Integer, KtExample> implements KtService {
    private static final Logger log = LoggerFactory.getLogger(KtServiceImpl.class);

    @Autowired
    private ConfigManager configManager;
    @Autowired
    private KtMapper ktMapper;

    @Override
    protected MyBatisBaseDao getMapper() {
        return ktMapper;
    }

    @Override
    public String checkAllow() {
        String enableKt = configManager.getConfigure(Config.KEY_ENABLE_KT);
        if (!Config.Y.equalsIgnoreCase(enableKt)) {
            return String.format("KT补料功能已关闭, 请检查配置 enable_kt: %s", enableKt);
        }
        return Constance.SUCCESS;
    }

    @Override
    public Kt getByLevelLocation(Integer level, Integer location) {
        KtExample ktExample = new KtExample();
        ktExample.createCriteria()
                .andLevelEqualTo(level)
                .andLocationEqualTo(location);
        List<Kt> kts = selectByExample(ktExample);
        if (kts.size() > 0) {
            return kts.get(0);
        }

        return null;
    }

    @Override
    public int incrementNumber(Integer ktId, Integer inc, String... columns) {
        return ktMapper.updateNumber(ktId, inc, Arrays.asList(columns));
    }

    @Override
    public List<Kt> listBySensor(Integer sensor) {
        KtExample ktExample = new KtExample();
        ktExample.createCriteria()
                .andSensorEqualTo(sensor);
        return ktMapper.selectByExample(ktExample);
    }

    @Override
    public List<Kt> listByStatus(Integer status) {
        KtExample ktExample = new KtExample();
        ktExample.createCriteria()
                .andStatusEqualTo(status);
        return ktMapper.selectByExample(ktExample);
    }
}
