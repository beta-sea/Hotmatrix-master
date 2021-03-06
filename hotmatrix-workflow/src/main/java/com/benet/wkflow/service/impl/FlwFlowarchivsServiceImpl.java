package com.benet.wkflow.service.impl;

import java.util.List;

import com.benet.common.core.pager.PagingModel;
import com.benet.common.utils.string.StringUtils;
import com.benet.common.utils.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.benet.wkflow.mapper.FlwFlowarchivsMapper;
import com.benet.wkflow.domain.FlwFlowarchivs;
import com.benet.wkflow.service.IFlwFlowarchivsService;

/**
 * 流程归档Service业务层处理
 * 
 * @author yoxking
 * @date 2020-05-17
 */
@Service
public class FlwFlowarchivsServiceImpl implements IFlwFlowarchivsService 
{
    @Autowired
    private FlwFlowarchivsMapper flwFlowarchivsMapper;

    /**
     * 查询所有【请填写功能名称】列表
     *
     * @param appCode 应用编号
     * @return 【请填写功能名称】集合
     */
    @Override
    public List<FlwFlowarchivs> getAllRecords(String appCode) {
        return flwFlowarchivsMapper.getAllRecords(appCode);
    }

    /**
     * 按分类查询【请填写功能名称】列表
     *
     * @param appCode 应用编号
     * @param classNo 分类编号
     * @return 【请填写功能名称】集合
     */
    @Override
    public List<FlwFlowarchivs> getRecordsByClassNo(String appCode,String classNo) {
        if (StringUtils.isNotEmpty(classNo)) {
            return flwFlowarchivsMapper.getRecordsByClassNo(appCode,classNo);
        }
        return null;
    }

    /**
     * 分页查询【请填写功能名称】列表
     *
     * @param appCode 应用编号
     * @param model 分页模型
     * @return 【请填写功能名称】集合
     */
    @Override
    public List<FlwFlowarchivs> getRecordsByPaging(String appCode,PagingModel model) {
        if (StringUtils.isNotNull(model)) {
            model.setPageIndex((model.getPageIndex()-1)*model.getPageSize());
            return flwFlowarchivsMapper.getRecordsByPaging(appCode,model);
        }
        return null;
    }


    /**
     * 分页查询【请填写功能名称】列表
     *
     * @param appCode 应用编号
     * @param pageIndex 当前页起始索引
     * @param pageSize 页面大小
     * @param condition 条件
     * @param orderField 排序列
     * @param orderType 排序类型
     * @return 【请填写功能名称】集合
     */
    @Override
    public List<FlwFlowarchivs> getRecordsByPaging(String appCode,int pageIndex, int pageSize, String condition, String orderField, String orderType) {

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
        return flwFlowarchivsMapper.getRecordsByPaging(appCode,model);
    }

    /**
     * 查询【请填写功能名称】
     *
     * @param appCode 应用编号
     * @param no 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public FlwFlowarchivs getRecordByNo(String appCode,String no) {
        if (StringUtils.isNotEmpty(no)) {
            return flwFlowarchivsMapper.getRecordByNo(appCode,no);
        }
        return null;
    }

    /**
     * 查询【请填写功能名称】名称
     *
     * @param appCode 应用编号
     * @param no 【请填写功能名称】ID
     * @return 名称
     */
    @Override
    public String getRecordNameByNo(String appCode,String no) {
        if (StringUtils.isNotEmpty(no)) {
            return flwFlowarchivsMapper.getRecordNameByNo(appCode,no);
        }
        return null;
    }

    /**
     * 查询【请填写功能名称】计数
     *
     * @param appCode 应用编号
     * @param condition 查询条件
     * @return 结果
     */
    @Override
    public int getCountByCondition(String appCode,String condition) {
        return flwFlowarchivsMapper.getCountByCondition(appCode,condition);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param appCode 应用编号
     * @param info 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int AddNewRecord(String appCode,FlwFlowarchivs info) {
        info.setCreateTime(DateUtils.getNowDate());
        info.setUpdateTime(DateUtils.getNowDate());
        info.setAppCode(appCode);
        info.setVersion(1L);
        return flwFlowarchivsMapper.AddNewRecord(info);
    }

    /**
     * 更新【请填写功能名称】
     *
     * @param appCode 应用编号
     * @param info 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int UpdateRecord(String appCode,FlwFlowarchivs info) {
        info.setUpdateTime(DateUtils.getNowDate());
        info.setAppCode(appCode);
        return flwFlowarchivsMapper.UpdateRecord(info);
    }

    /**
     * 硬删除【请填写功能名称】
     *
     * @param appCode 应用编号
     * @param no 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int HardDeleteByNo(String appCode,String no) {
        if (StringUtils.isNotEmpty(no)) {
            return flwFlowarchivsMapper.HardDeleteByNo(appCode,no);
        }
        return 0;
    }

    /**
     * 批量硬删除【请填写功能名称】
     *
     * @param appCode 应用编号
     * @param nos 【请填写功能名称】IDs
     * @return 结果
     */
    @Override
    public int HardDeleteByNos(String appCode,String[] nos) {
        if (StringUtils.isNotEmpty(nos)) {
            return flwFlowarchivsMapper.HardDeleteByNos(appCode,nos);
        }
        return 0;
    }

    /**
     * 按条件硬删除【请填写功能名称】
     *
     * @param appCode 应用编号
     * @param condition 条件
     * @return 结果
     */
    @Override
    public int HardDeleteByCondition(String appCode,String condition) {
        return flwFlowarchivsMapper.HardDeleteByCondition(appCode,condition);
    }

    /**
     * 软删除【请填写功能名称】
     *
     * @param appCode 应用编号
     * @param no 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int SoftDeleteByNo(String appCode,String no) {
        if (StringUtils.isNotEmpty(no)) {
            return flwFlowarchivsMapper.SoftDeleteByNo(appCode,no);
        }
        return 0;
    }

    /**
     * 批量软删除【请填写功能名称】
     *
     * @param appCode 应用编号
     * @param nos 【请填写功能名称】IDs
     * @return 结果
     */
    @Override
    public int SoftDeleteByNos(String appCode,String[] nos) {
        if (StringUtils.isNotEmpty(nos)) {
            return flwFlowarchivsMapper.SoftDeleteByNos(appCode,nos);
        }
        return 0;
    }

    /**
     * 按条件软删除【请填写功能名称】
     *
     * @param appCode 应用编号
     * @param condition 条件
     * @return 结果
     */
    @Override
    public int SoftDeleteByCondition(String appCode,String condition) {
        return flwFlowarchivsMapper.SoftDeleteByCondition(appCode,condition);
    }
}
