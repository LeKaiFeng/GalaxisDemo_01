package com.galaxis.wcs.yanfeng.database.oes.service;

import com.galaxis.wcs.yanfeng.database.oes.domain.Box;
import com.galaxis.wcs.yanfeng.database.oes.domain.BoxExample;

import java.util.List;
import java.util.Map;

public interface BoxService extends BaseService<Box, Integer, BoxExample> {

    /**
     * 判断箱子是否是长箱
     *
     * @param box box对象
     * @return 是否长箱
     */
    Boolean isLength(Box box);

    /**
     * 清除box上的任务
     *
     * @param cartonNo 箱号
     * @return 更新的条数
     */
    int cleanTask(String cartonNo);

    /**
     * 通过箱号查询箱子
     *
     * @param cartonNo 箱号
     * @return 箱子
     */
    Box getBoxByCartonNo(String cartonNo);

    /**
     * 根据箱号更新有的数据
     *
     * @param box 箱号
     * @return 更新的条数
     */
    int updateByCartonNo(Box box);

    /**
     * 获得在库的该物料最老的收货批次
     *
     * @param partNo 物料号
     * @return 最老的收货批次
     */
    String getOldestRecLot(String partNo);

    /**
     * 查询该物料在各层的数量
     *
     * @param partNo 物料号
     * @return map level->count
     */
    Map<Integer, Integer> mapLevelCountByPartNo(String partNo);

    /**
     * 选择某种物料进行出KT
     *
     * @param partNo    物料号
     * @param level     KT层数
     * @param cartonNos 排除的号列表
     * @return 出KT的箱子
     */
    Box getBoxToKt(String partNo, Integer level, String... cartonNos);

    /**
     * 清除该box的历史占用, 并包括解锁7/8
     *
     * @param box 要清除历史占用的box
     */
    void clearOccupy(Box box);

    /**
     * 双深货位, 根据1/2货位, 解锁7/8货位的料箱
     * 主要用在1/2货位的料箱被搬走时
     *
     * @param level    层
     * @param location 货位
     * @param boxIds   如果不为空, 则只有被这些指定的ids锁定的box才能被解锁
     */
    void unlock78(Integer level, Integer location, Integer... boxIds);

    List<Box> listFreeBox(Box box, Integer level, List<Integer> locationEndWith);

    Box getBoxByLevelLocation(Integer level, Integer location);
}
