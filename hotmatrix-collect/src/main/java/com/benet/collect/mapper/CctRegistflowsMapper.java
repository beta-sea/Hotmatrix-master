package com.benet.collect.mapper;

import java.util.List;
import com.benet.common.core.pager.PagingModel;
import com.benet.collect.domain.CctRegistflows;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 预约信息Mapper接口
 * 
 * @author yoxking
 * @date 2020-10-14
 */
@Mapper
public interface CctRegistflowsMapper 
{
    /**
     * 查询所有预约信息列表
     *
     * @param appCode 应用编号
     * @return 预约信息集合
     */
    public List<CctRegistflows> getAllRecords(@Param("appCode") String appCode);

    /**
     * 按分类查询预约信息列表
     *
     * @param appCode 应用编号
     * @param classNo 分类编号
     * @return 预约信息集合
     */
    public List<CctRegistflows> getRecordsByClassNo(@Param("appCode") String appCode, @Param("classNo") String classNo);

    /**
     * 分页查询预约信息列表
     *
     * @param appCode 应用编号
     * @param model 分页模型
     * @return 预约信息集合
     */
    public List<CctRegistflows> getRecordsByPaging(@Param("appCode") String appCode, @Param("model") PagingModel model);

    /**
     * 查询预约信息
     *
     * @param appCode 应用编号
     * @param no 预约信息ID
     * @return 预约信息
     */
    public CctRegistflows getRecordByNo(@Param("appCode") String appCode, @Param("no") String no);

    /**
     * 查询预约信息名称
     *
     * @param appCode 应用编号
     * @param no 预约信息ID
     * @return 名称
     */
    public String getRecordNameByNo(@Param("appCode") String appCode, @Param("no") String no);

    /**
     * 查询预约信息计数
     *
     * @param appCode 应用编号
     * @param condition 查询条件
     * @return 结果
     */
    public int getCountByCondition(@Param("appCode") String appCode, @Param("condition") String condition);

    /**
     * 新增预约信息
     *
     * @param info 预约信息
     * @return 结果
     */
    public int AddNewRecord(@Param("info") CctRegistflows info);

    /**
     * 更新预约信息
     *
     * @param info 预约信息
     * @return 结果
     */
    public int UpdateRecord(@Param("info") CctRegistflows info);

    /**
     * 硬删除预约信息
     *
     * @param appCode 应用编号
     * @param no 预约信息ID
     * @return 结果
     */
    public int HardDeleteByNo(@Param("appCode") String appCode, @Param("no") String no);

    /**
     * 批量硬删除预约信息
     *
     * @param appCode 应用编号
     * @param nos 预约信息IDs
     * @return 结果
     */
    public int HardDeleteByNos(@Param("appCode") String appCode, @Param("nos") String[] nos);

    /**
     * 按条件硬删除预约信息
     *
     * @param appCode 应用编号
     * @param condition 条件
     * @return 结果
     */
    public int HardDeleteByCondition(@Param("appCode") String appCode, @Param("condition") String condition);

    /**
     * 软删除预约信息
     *
     * @param appCode 应用编号
     * @param no 预约信息ID
     * @return 结果
     */
    public int SoftDeleteByNo(@Param("appCode") String appCode, @Param("no") String no);

    /**
     * 批量软删除预约信息
     *
     * @param appCode 应用编号
     * @param nos 预约信息IDs
     * @return 结果
     */
    public int SoftDeleteByNos(@Param("appCode") String appCode, @Param("nos") String[] nos);

    /**
     * 按条件软删除预约信息
     *
     * @param appCode 应用编号
     * @param condition 条件
     * @return 结果
     */
    public int SoftDeleteByCondition(@Param("appCode") String appCode, @Param("condition") String condition);

}
