package com.benet.system.service.impl;

import java.util.List;
import com.benet.common.configure.GlobalConfig;
import com.benet.common.core.pager.PagingModel;
import com.benet.common.utils.string.StringUtils;
import com.benet.common.utils.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.benet.system.mapper.SysContentinfoMapper;
import com.benet.system.domain.SysContentinfo;
import com.benet.system.service.ISysContentinfoService;

/**
 * 内容信息Service业务层处理
 * 
 * @author yoxking
 * @date 2020-04-06
 */
@Service
public class SysContentinfoServiceImpl implements ISysContentinfoService 
{
    @Autowired
    private SysContentinfoMapper sysContentinfoMapper;

    /**
     * 查询所有内容信息列表
     *
     * @return 内容信息集合
     */
    @Override
    public List<SysContentinfo> getAllRecords() {
        return sysContentinfoMapper.getAllRecords(GlobalConfig.getAppCode());
    }

    /**
     * 按分类查询内容信息列表
     *
     * @param classNo 分类编号
     * @return 内容信息集合
     */
    @Override
    public List<SysContentinfo> getRecordsByClassNo(String classNo) {
        if (StringUtils.isNotEmpty(classNo)) {
            return sysContentinfoMapper.getRecordsByClassNo(GlobalConfig.getAppCode(),classNo);
        }
        return null;
    }

    /**
     * 分页查询内容信息列表
     *
     * @param model 分页模型
     * @return 内容信息集合
     */
    @Override
    public List<SysContentinfo> getRecordsByPaging(PagingModel model) {
        if (StringUtils.isNotNull(model)) {
            model.setPageIndex((model.getPageIndex()-1)*model.getPageSize());
            return sysContentinfoMapper.getRecordsByPaging(GlobalConfig.getAppCode(),model);
        }
        return null;
    }


    /**
     * 分页查询内容信息列表
     *
     * @param pageIndex 当前页起始索引
     * @param pageSize 页面大小
     * @param condition 条件
     * @param orderField 排序列
     * @param orderType 排序类型
     * @return 内容信息集合
     */
    @Override
    public List<SysContentinfo> getRecordsByPaging(int pageIndex,int pageSize,String condition,String orderField,String orderType) {

        PagingModel model = new PagingModel();
        model.setPageIndex((pageIndex-1) * pageSize);
        model.setPageSize(pageSize);
        model.setCondition(condition);
        if (StringUtils.isEmpty(orderField)) {
            model.setOrderField("id");
        } else {
            model.setOrderField(orderField);
        }
        if (StringUtils.isEmpty(orderType)) {
            model.setOrderType("Asc");
        } else {
            model.setOrderType(orderType);
        }
        return sysContentinfoMapper.getRecordsByPaging(GlobalConfig.getAppCode(),model);
    }

    /**
     * 查询内容信息
     *
     * @param no 内容信息ID
     * @return 内容信息
     */
    @Override
    public SysContentinfo getRecordByNo(String no) {
        if (StringUtils.isNotEmpty(no)) {
            return sysContentinfoMapper.getRecordByNo(GlobalConfig.getAppCode(),no);
        }
        return null;
    }

    /**
     * 查询内容信息名称
     *
     * @param no 内容信息ID
     * @return 名称
     */
    @Override
    public String getRecordNameByNo(String no) {
        if (StringUtils.isNotEmpty(no)) {
            return sysContentinfoMapper.getRecordNameByNo(GlobalConfig.getAppCode(),no);
        }
        return null;
    }

    /**
     * 查询内容信息计数
     *
     * @param condition 查询条件
     * @return 结果
     */
    @Override
    public int getCountByCondition(String condition) {
        return sysContentinfoMapper.getCountByCondition(GlobalConfig.getAppCode(),condition);
    }

    /**
     * 新增内容信息
     *
     * @param info 内容信息
     * @return 结果
     */
    @Override
    public int AddNewRecord(SysContentinfo info) {
        info.setCreateTime(DateUtils.getNowDate());
        info.setUpdateTime(DateUtils.getNowDate());
        info.setAppCode(GlobalConfig.getAppCode());
        info.setVersion(1L);
        return sysContentinfoMapper.AddNewRecord(info);
    }

    /**
     * 更新内容信息
     *
     * @param info 内容信息
     * @return 结果
     */
    @Override
    public int UpdateRecord(SysContentinfo info) {
        info.setUpdateTime(DateUtils.getNowDate());
        info.setAppCode(GlobalConfig.getAppCode());
        return sysContentinfoMapper.UpdateRecord(info);
    }

    /**
     * 硬删除内容信息
     *
     * @param no 内容信息ID
     * @return 结果
     */
    @Override
    public int HardDeleteByNo(String no) {
        if (StringUtils.isNotEmpty(no)) {
            return sysContentinfoMapper.HardDeleteByNo(GlobalConfig.getAppCode(),no);
        }
        return 0;
    }

    /**
     * 批量硬删除内容信息
     *
     * @param nos 内容信息IDs
     * @return 结果
     */
    @Override
    public int HardDeleteByNos(String[] nos) {
        if (StringUtils.isNotEmpty(nos)) {
            return sysContentinfoMapper.HardDeleteByNos(GlobalConfig.getAppCode(),nos);
        }
        return 0;
    }

    /**
     * 按条件硬删除内容信息
     *
     * @param condition 条件
     * @return 结果
     */
    @Override
    public int HardDeleteByCondition(String condition) {
        return sysContentinfoMapper.HardDeleteByCondition(GlobalConfig.getAppCode(),condition);
    }

    /**
     * 软删除内容信息
     *
     * @param no 内容信息ID
     * @return 结果
     */
    @Override
    public int SoftDeleteByNo(String no) {
        if (StringUtils.isNotEmpty(no)) {
            return sysContentinfoMapper.SoftDeleteByNo(GlobalConfig.getAppCode(),no);
        }
        return 0;
    }

    /**
     * 批量软删除内容信息
     *
     * @param nos 内容信息IDs
     * @return 结果
     */
    @Override
    public int SoftDeleteByNos(String[] nos) {
        if (StringUtils.isNotEmpty(nos)) {
            return sysContentinfoMapper.SoftDeleteByNos(GlobalConfig.getAppCode(),nos);
        }
        return 0;
    }

    /**
     * 按条件软删除内容信息
     *
     * @param condition 条件
     * @return 结果
     */
    @Override
    public int SoftDeleteByCondition(String condition) {
        return sysContentinfoMapper.SoftDeleteByCondition(GlobalConfig.getAppCode(),condition);
    }
}
