package com.benet.wkflow.service.impl;

import java.util.List;

import com.benet.common.core.pager.PagingModel;
import com.benet.common.utils.string.StringUtils;
import com.benet.common.utils.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.benet.wkflow.mapper.FlwFlowbuttonMapper;
import com.benet.wkflow.domain.FlwFlowbutton;
import com.benet.wkflow.service.IFlwFlowbuttonService;

/**
 * 工作流程按钮Service业务层处理
 * 
 * @author yoxking
 * @date 2020-05-17
 */
@Service
public class FlwFlowbuttonServiceImpl implements IFlwFlowbuttonService 
{
    @Autowired
    private FlwFlowbuttonMapper flwFlowbuttonMapper;

    /**
     * 查询所有工作流程按钮列表
     *
     * @param appCode 应用编号
     * @return 工作流程按钮集合
     */
    @Override
    public List<FlwFlowbutton> getAllRecords(String appCode) {
        return flwFlowbuttonMapper.getAllRecords(appCode);
    }

    /**
     * 按分类查询工作流程按钮列表
     *
     * @param appCode 应用编号
     * @param classNo 分类编号
     * @return 工作流程按钮集合
     */
    @Override
    public List<FlwFlowbutton> getRecordsByClassNo(String appCode,String classNo) {
        if (StringUtils.isNotEmpty(classNo)) {
            return flwFlowbuttonMapper.getRecordsByClassNo(appCode,classNo);
        }
        return null;
    }

    /**
     * 分页查询工作流程按钮列表
     *
     * @param appCode 应用编号
     * @param model 分页模型
     * @return 工作流程按钮集合
     */
    @Override
    public List<FlwFlowbutton> getRecordsByPaging(String appCode,PagingModel model) {
        if (StringUtils.isNotNull(model)) {
            model.setPageIndex((model.getPageIndex()-1)*model.getPageSize());
            return flwFlowbuttonMapper.getRecordsByPaging(appCode,model);
        }
        return null;
    }


    /**
     * 分页查询工作流程按钮列表
     *
     * @param appCode 应用编号
     * @param pageIndex 当前页起始索引
     * @param pageSize 页面大小
     * @param condition 条件
     * @param orderField 排序列
     * @param orderType 排序类型
     * @return 工作流程按钮集合
     */
    @Override
    public List<FlwFlowbutton> getRecordsByPaging(String appCode,int pageIndex,int pageSize,String condition,String orderField,String orderType) {

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
        return flwFlowbuttonMapper.getRecordsByPaging(appCode,model);
    }

    /**
     * 查询工作流程按钮
     *
     * @param appCode 应用编号
     * @param no 工作流程按钮ID
     * @return 工作流程按钮
     */
    @Override
    public FlwFlowbutton getRecordByNo(String appCode,String no) {
        if (StringUtils.isNotEmpty(no)) {
            return flwFlowbuttonMapper.getRecordByNo(appCode,no);
        }
        return null;
    }

    /**
     * 查询工作流程按钮名称
     *
     * @param appCode 应用编号
     * @param no 工作流程按钮ID
     * @return 名称
     */
    @Override
    public String getRecordNameByNo(String appCode,String no) {
        if (StringUtils.isNotEmpty(no)) {
            return flwFlowbuttonMapper.getRecordNameByNo(appCode,no);
        }
        return null;
    }

    /**
     * 查询工作流程按钮计数
     *
     * @param appCode 应用编号
     * @param condition 查询条件
     * @return 结果
     */
    @Override
    public int getCountByCondition(String appCode,String condition) {
        return flwFlowbuttonMapper.getCountByCondition(appCode,condition);
    }

    /**
     * 新增工作流程按钮
     *
     * @param appCode 应用编号
     * @param info 工作流程按钮
     * @return 结果
     */
    @Override
    public int AddNewRecord(String appCode,FlwFlowbutton info) {
        info.setCreateTime(DateUtils.getNowDate());
        info.setUpdateTime(DateUtils.getNowDate());
        info.setAppCode(appCode);
        info.setVersion(1L);
        return flwFlowbuttonMapper.AddNewRecord(info);
    }

    /**
     * 更新工作流程按钮
     *
     * @param appCode 应用编号
     * @param info 工作流程按钮
     * @return 结果
     */
    @Override
    public int UpdateRecord(String appCode,FlwFlowbutton info) {
        info.setUpdateTime(DateUtils.getNowDate());
        info.setAppCode(appCode);
        return flwFlowbuttonMapper.UpdateRecord(info);
    }

    /**
     * 硬删除工作流程按钮
     *
     * @param appCode 应用编号
     * @param no 工作流程按钮ID
     * @return 结果
     */
    @Override
    public int HardDeleteByNo(String appCode,String no) {
        if (StringUtils.isNotEmpty(no)) {
            return flwFlowbuttonMapper.HardDeleteByNo(appCode,no);
        }
        return 0;
    }

    /**
     * 批量硬删除工作流程按钮
     *
     * @param appCode 应用编号
     * @param nos 工作流程按钮IDs
     * @return 结果
     */
    @Override
    public int HardDeleteByNos(String appCode,String[] nos) {
        if (StringUtils.isNotEmpty(nos)) {
            return flwFlowbuttonMapper.HardDeleteByNos(appCode,nos);
        }
        return 0;
    }

    /**
     * 按条件硬删除工作流程按钮
     *
     * @param appCode 应用编号
     * @param condition 条件
     * @return 结果
     */
    @Override
    public int HardDeleteByCondition(String appCode,String condition) {
        return flwFlowbuttonMapper.HardDeleteByCondition(appCode,condition);
    }

    /**
     * 软删除工作流程按钮
     *
     * @param appCode 应用编号
     * @param no 工作流程按钮ID
     * @return 结果
     */
    @Override
    public int SoftDeleteByNo(String appCode,String no) {
        if (StringUtils.isNotEmpty(no)) {
            return flwFlowbuttonMapper.SoftDeleteByNo(appCode,no);
        }
        return 0;
    }

    /**
     * 批量软删除工作流程按钮
     *
     * @param appCode 应用编号
     * @param nos 工作流程按钮IDs
     * @return 结果
     */
    @Override
    public int SoftDeleteByNos(String appCode,String[] nos) {
        if (StringUtils.isNotEmpty(nos)) {
            return flwFlowbuttonMapper.SoftDeleteByNos(appCode,nos);
        }
        return 0;
    }

    /**
     * 按条件软删除工作流程按钮
     *
     * @param appCode 应用编号
     * @param condition 条件
     * @return 结果
     */
    @Override
    public int SoftDeleteByCondition(String appCode,String condition) {
        return flwFlowbuttonMapper.SoftDeleteByCondition(appCode,condition);
    }
}
