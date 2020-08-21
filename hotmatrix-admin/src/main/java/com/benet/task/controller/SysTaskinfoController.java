package com.benet.task.controller;

import java.util.List;
import com.benet.common.core.pager.PageRequest;
import com.benet.common.utils.uuid.UuidUtils;
import com.benet.common.utils.web.ServletUtils;
import com.benet.framework.security.LoginUser;
import com.benet.framework.security.service.MyJwtokenService;
import com.benet.task.domain.SysTaskinfo;
import com.benet.task.service.ISysTaskinfoService;
import com.benet.task.vmodel.TaskObjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.benet.common.annotation.Oplog;
import com.benet.common.core.controller.BaseController;
import com.benet.common.core.domain.AjaxResult;
import com.benet.common.enums.BusinessType;
import com.benet.common.utils.poi.ExcelUtils;
import com.benet.common.utils.string.StringUtils;
import com.benet.common.core.pager.TableDataInfo;

/**
 * 定时任务调度Controller
 * 
 * @author yoxking
 * @date 2020-04-20
 */
@RestController
@RequestMapping("/task/taskinfo")
public class SysTaskinfoController extends BaseController
{
    @Autowired
    private MyJwtokenService tokenService;

    @Autowired
    private ISysTaskinfoService sysTaskinfoService;
    /**
     * 首页
     */
    //@PreAuthorize("@ps.hasPermit('system:taskinfo:index')")
    @GetMapping(value="/index")
    public ModelAndView index()
    {
        ModelAndView mv=new ModelAndView("index");
        return mv;
    }

    /**
     * 查询定时任务调度列表
     */
    //@PreAuthorize("@ps.hasPermit('system:taskinfo:list')")
    @PostMapping(value = "/list")
    public TableDataInfo list(@RequestBody PageRequest pRequest)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        int count = sysTaskinfoService.getCountByCondition(loginUser.getUser().getAppCode(),pRequest.getCondition());
        List<SysTaskinfo> list = sysTaskinfoService.getRecordsByPaging(loginUser.getUser().getAppCode(),pRequest.getPageIndex(), pRequest.getPageSize(), pRequest.getCondition(), "id", "Asc");
        return getDataTable(list, count);
    }

    /**
     * 新增定时任务调度
     */
    //@PreAuthorize("@ps.hasPermit('system:taskinfo:insert')")
    @Oplog(title = "定时任务调度", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult insert(@RequestBody SysTaskinfo sysTaskinfo) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        sysTaskinfo.setTaskNo(UuidUtils.shortUUID());
        sysTaskinfo.setCreateBy(loginUser.getUser().getUserNo());
        sysTaskinfo.setUpdateBy(loginUser.getUser().getUserNo());
        return toAjax(sysTaskinfoService.AddNewRecord(loginUser.getUser().getAppCode(),sysTaskinfo));
    }

    /**
     * 编辑定时任务调度
     */
    //@PreAuthorize("@ps.hasPermit('system:taskinfo:update')")
    @Oplog(title = "定时任务调度", businessType = BusinessType.UPDATE)
    @PutMapping
        public AjaxResult update(@RequestBody SysTaskinfo sysTaskinfo) {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        sysTaskinfo.setUpdateBy(loginUser.getUser().getUserNo());
            return toAjax(sysTaskinfoService.UpdateRecord(loginUser.getUser().getAppCode(),sysTaskinfo));
        }

    /**
     * 保存定时任务调度
     */
    //@PreAuthorize("@ps.hasPermit('system:taskinfo:save')")
    @Oplog(title = "定时任务调度", businessType = BusinessType.SAVE)
    @PostMapping(value = "/save")
    public AjaxResult save(@RequestBody SysTaskinfo sysTaskinfo) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNull(sysTaskinfoService.getRecordByNo(loginUser.getUser().getAppCode(),sysTaskinfo.getTaskNo()))) {
            sysTaskinfo.setTaskNo(UuidUtils.shortUUID());
            sysTaskinfo.setCreateBy(loginUser.getUser().getUserNo());
            sysTaskinfo.setUpdateBy(loginUser.getUser().getUserNo());
            return toAjax(sysTaskinfoService.AddNewRecord(loginUser.getUser().getAppCode(),sysTaskinfo));
        } else {
            sysTaskinfo.setUpdateBy(loginUser.getUser().getUserNo());
            return toAjax(sysTaskinfoService.UpdateRecord(loginUser.getUser().getAppCode(),sysTaskinfo));
        }
    }

    /**
     * 删除定时任务调度
     */
    //@PreAuthorize("@ps.hasPermit('system:taskinfo:delete')")
    @Oplog(title = "定时任务调度", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable("ids") String[] ids)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return toAjax(sysTaskinfoService.SoftDeleteByNos(loginUser.getUser().getAppCode(),ids));
    }

    /**
     * 获取定时任务调度详细信息
     */
    //@PreAuthorize("@ps.hasPermit('system:taskinfo:detail')")
    @GetMapping(value = "/{id}")
    public AjaxResult detail(@PathVariable("id") String id)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return AjaxResult.success(sysTaskinfoService.getRecordByNo(loginUser.getUser().getAppCode(),id));
    }

    /**
     * 导出定时任务调度列表
     */
    //@PreAuthorize("@ps.hasPermit('system:taskinfo:export')")
    @Oplog(title = "定时任务调度", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@RequestBody PageRequest pRequest)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        int count = sysTaskinfoService.getCountByCondition(loginUser.getUser().getAppCode(),pRequest.getCondition());

        List<SysTaskinfo> list = sysTaskinfoService.getRecordsByPaging(loginUser.getUser().getAppCode(),1,count,pRequest.getCondition(),"id","Asc");
        ExcelUtils<SysTaskinfo> util = new ExcelUtils<SysTaskinfo>(SysTaskinfo.class);
        return util.exportExcel(list, "SysTaskinfo");
    }


    /**
     * 任务调度启动执行
     */
    //@PreAuthorize("@ps.hasPermit('system:taskinfo:export')")
    @Oplog(title = "定时任务调度", businessType = BusinessType.UPDATE)
    @PostMapping("/startTask")
    public AjaxResult startTask(@RequestBody TaskObjectVo taskObject)
    {
        return toAjax(sysTaskinfoService.start(taskObject.getTaskNo()));
    }

    /**
     * 任务调度状态修改
     */
    //@PreAuthorize("@ps.hasPermit('system:taskinfo:export')")
    @Oplog(title = "定时任务调度", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody TaskObjectVo taskObject)
    {
        return toAjax(sysTaskinfoService.changeStatus(taskObject.getTaskNo(),taskObject.getTaskStatus()));
    }


    /**
     * 校验cron表达式是否有效
     */
    @PostMapping("/checkExpress")
    public boolean checkExpress(@RequestBody String express)
    {
        return sysTaskinfoService.checkExpression(express);
    }

}