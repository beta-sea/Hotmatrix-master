package com.benet.system.service.impl;

import java.util.List;
import com.benet.common.configure.GlobalConfig;
import com.benet.common.core.pager.PagingModel;
import com.benet.common.utils.string.StringUtils;
import com.benet.common.utils.date.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.benet.system.mapper.SysDicttypeMapper;
import com.benet.system.domain.SysDicttype;
import com.benet.system.service.ISysDicttypeService;

/**
 * 字典类型Service业务层处理
 * 
 * @author yoxking
 * @date 2020-04-23
 */
@Service
public class SysDicttypeServiceImpl implements ISysDicttypeService 
{
    @Autowired
    private SysDicttypeMapper sysDicttypeMapper;

    /**
     * 查询所有字典类型列表
     *
     * @return 字典类型集合
     */
    @Override
    public List<SysDicttype> getAllRecords() {
        return sysDicttypeMapper.getAllRecords(GlobalConfig.getAppCode());
    }

    /**
     * 按分类查询字典类型列表
     *
     * @param classNo 分类编号
     * @return 字典类型集合
     */
    @Override
    public List<SysDicttype> getRecordsByClassNo(String classNo) {
        if (StringUtils.isNotEmpty(classNo)) {
            return sysDicttypeMapper.getRecordsByClassNo(GlobalConfig.getAppCode(),classNo);
        }
        return null;
    }

    /**
     * 分页查询字典类型列表
     *
     * @param model 分页模型
     * @return 字典类型集合
     */
    @Override
    public List<SysDicttype> getRecordsByPaging(PagingModel model) {
        if (StringUtils.isNotNull(model)) {
            model.setPageIndex((model.getPageIndex()-1)*model.getPageSize());
            return sysDicttypeMapper.getRecordsByPaging(GlobalConfig.getAppCode(),model);
        }
        return null;
    }


    /**
     * 分页查询字典类型列表
     *
     * @param pageIndex 当前页起始索引
     * @param pageSize 页面大小
     * @param condition 条件
     * @param orderField 排序列
     * @param orderType 排序类型
     * @return 字典类型集合
     */
    @Override
    public List<SysDicttype> getRecordsByPaging(int pageIndex,int pageSize,String condition,String orderField,String orderType) {

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
        return sysDicttypeMapper.getRecordsByPaging(GlobalConfig.getAppCode(),model);
    }

    /**
     * 查询字典类型
     *
     * @param no 字典类型ID
     * @return 字典类型
     */
    @Override
    public SysDicttype getRecordByNo(String no) {
        if (StringUtils.isNotEmpty(no)) {
            return sysDicttypeMapper.getRecordByNo(GlobalConfig.getAppCode(),no);
        }
        return null;
    }

    /**
     * 查询字典类型名称
     *
     * @param no 字典类型ID
     * @return 名称
     */
    @Override
    public String getRecordNameByNo(String no) {
        if (StringUtils.isNotEmpty(no)) {
            return sysDicttypeMapper.getRecordNameByNo(GlobalConfig.getAppCode(),no);
        }
        return null;
    }

    /**
     * 查询字典类型计数
     *
     * @param condition 查询条件
     * @return 结果
     */
    @Override
    public int getCountByCondition(String condition) {
        return sysDicttypeMapper.getCountByCondition(GlobalConfig.getAppCode(),condition);
    }

    /**
     * 新增字典类型
     *
     * @param info 字典类型
     * @return 结果
     */
    @Override
    public int AddNewRecord(SysDicttype info) {
        info.setCreateTime(DateUtils.getNowDate());
        info.setUpdateTime(DateUtils.getNowDate());
        info.setAppCode(GlobalConfig.getAppCode());
        info.setVersion(1L);
        return sysDicttypeMapper.AddNewRecord(info);
    }

    /**
     * 更新字典类型
     *
     * @param info 字典类型
     * @return 结果
     */
    @Override
    public int UpdateRecord(SysDicttype info) {
        info.setUpdateTime(DateUtils.getNowDate());
        info.setAppCode(GlobalConfig.getAppCode());
        return sysDicttypeMapper.UpdateRecord(info);
    }

    /**
     * 硬删除字典类型
     *
     * @param no 字典类型ID
     * @return 结果
     */
    @Override
    public int HardDeleteByNo(String no) {
        if (StringUtils.isNotEmpty(no)) {
            return sysDicttypeMapper.HardDeleteByNo(GlobalConfig.getAppCode(),no);
        }
        return 0;
    }

    /**
     * 批量硬删除字典类型
     *
     * @param nos 字典类型IDs
     * @return 结果
     */
    @Override
    public int HardDeleteByNos(String[] nos) {
        if (StringUtils.isNotEmpty(nos)) {
            return sysDicttypeMapper.HardDeleteByNos(GlobalConfig.getAppCode(),nos);
        }
        return 0;
    }

    /**
     * 按条件硬删除字典类型
     *
     * @param condition 条件
     * @return 结果
     */
    @Override
    public int HardDeleteByCondition(String condition) {
        return sysDicttypeMapper.HardDeleteByCondition(GlobalConfig.getAppCode(),condition);
    }

    /**
     * 软删除字典类型
     *
     * @param no 字典类型ID
     * @return 结果
     */
    @Override
    public int SoftDeleteByNo(String no) {
        if (StringUtils.isNotEmpty(no)) {
            return sysDicttypeMapper.SoftDeleteByNo(GlobalConfig.getAppCode(),no);
        }
        return 0;
    }

    /**
     * 批量软删除字典类型
     *
     * @param nos 字典类型IDs
     * @return 结果
     */
    @Override
    public int SoftDeleteByNos(String[] nos) {
        if (StringUtils.isNotEmpty(nos)) {
            return sysDicttypeMapper.SoftDeleteByNos(GlobalConfig.getAppCode(),nos);
        }
        return 0;
    }

    /**
     * 按条件软删除字典类型
     *
     * @param condition 条件
     * @return 结果
     */
    @Override
    public int SoftDeleteByCondition(String condition) {
        return sysDicttypeMapper.SoftDeleteByCondition(GlobalConfig.getAppCode(),condition);
    }
}
