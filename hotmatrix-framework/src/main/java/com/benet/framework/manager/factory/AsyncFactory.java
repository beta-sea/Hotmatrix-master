package com.benet.framework.manager.factory;

import java.util.TimerTask;

import com.benet.common.constant.PubConstants;
import com.benet.common.utils.net.AddressUtils;
import com.benet.common.utils.spring.SpringUtils;
import com.benet.common.utils.web.ServletUtils;
import com.benet.framework.utils.OplogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 异步工厂（产生任务用）
 * 
 * @author yoxking
 *
 */
public class AsyncFactory
{
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");

    /**
     * 同步session到数据库
     * 
     * @param session 在线用户会话
     * @return 任务task
     */
    /*public static TimerTask syncSessionToDb(final OnlineSession session)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {
                SysUserOnline online = new SysUserOnline();
                online.setSessionId(String.valueOf(session.getId()));
                online.setDeptName(session.getDeptName());
                online.setLoginName(session.getLoginName());
                online.setStartTimestamp(session.getStartTimestamp());
                online.setLastAccessTime(session.getLastAccessTime());
                online.setExpireTime(session.getTimeout());
                online.setIpaddr(session.getHost());
                online.setLoginLocation(AddressUtils.getRealAddressByIP(session.getHost()));
                online.setBrowser(session.getBrowser());
                online.setOs(session.getOs());
                online.setStatus(session.getStatus());
                SpringUtils.getBean(ISysUserOnlineService.class).saveOnline(online);

            }
        };
    }*/

    /**
     * 操作日志记录
     * 
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final Object operLog)
    {
        return new TimerTask()
        {
            @Override
            public void run()
            {

            }
        };
    }

    /**
     * 记录登陆信息
     * 
     * @param username 用户名
     * @param status 状态
     * @param message 消息
     * @param args 列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args)
    {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        //final String ip = ShiroUtils.getIp();
        final String ip = "";
        return new TimerTask()
        {
            @Override
            public void run()
            {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(OplogUtils.getBlock(ip));
                s.append(address);
                s.append(OplogUtils.getBlock(username));
                s.append(OplogUtils.getBlock(status));
                s.append(OplogUtils.getBlock(message));
                // 打印信息到日志
                sys_user_logger.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();

            }
        };
    }
}
