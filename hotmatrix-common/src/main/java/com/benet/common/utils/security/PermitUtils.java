package com.benet.common.utils.security;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import com.benet.common.constant.PmtConstants;
import com.benet.common.utils.data.MessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * permission 工具类
 * 
 * @author yoxking
 */
public class PermitUtils
{
    private static final Logger log = LoggerFactory.getLogger(PermitUtils.class);

    /**
     * 查看数据的权限
     */
    public static final String VIEW_PERMISSION = "no.view.permission";

    /**
     * 创建数据的权限
     */
    public static final String CREATE_PERMISSION = "no.create.permission";

    /**
     * 修改数据的权限
     */
    public static final String UPDATE_PERMISSION = "no.update.permission";

    /**
     * 删除数据的权限
     */
    public static final String DELETE_PERMISSION = "no.delete.permission";

    /**
     * 导出数据的权限
     */
    public static final String EXPORT_PERMISSION = "no.export.permission";

    /**
     * 其他数据的权限
     */
    public static final String PERMISSION = "no.permission";

    /**
     * 权限错误消息提醒
     * 
     * @param permissionsStr 错误信息
     * @return 提示信息
     */
    public static String getMsg(String permissionsStr)
    {
        String permission = StringUtils.substringBetween(permissionsStr, "[", "]");
        String msg = MessageUtils.message(PERMISSION, permission);
        if (StringUtils.endsWithIgnoreCase(permission, PmtConstants.ADD_PERMISSION))
        {
            msg = MessageUtils.message(CREATE_PERMISSION, permission);
        }
        else if (StringUtils.endsWithIgnoreCase(permission, PmtConstants.EDIT_PERMISSION))
        {
            msg = MessageUtils.message(UPDATE_PERMISSION, permission);
        }
        else if (StringUtils.endsWithIgnoreCase(permission, PmtConstants.REMOVE_PERMISSION))
        {
            msg = MessageUtils.message(DELETE_PERMISSION, permission);
        }
        else if (StringUtils.endsWithIgnoreCase(permission, PmtConstants.EXPORT_PERMISSION))
        {
            msg = MessageUtils.message(EXPORT_PERMISSION, permission);
        }
        else if (StringUtils.endsWithAny(permission,
                new String[] { PmtConstants.VIEW_PERMISSION, PmtConstants.LIST_PERMISSION }))
        {
            msg = MessageUtils.message(VIEW_PERMISSION, permission);
        }
        return msg;
    }
}
