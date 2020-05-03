package com.benet.sys.controller;

import java.util.ArrayList;
import java.util.List;
import com.benet.common.core.pager.PageRequest;
import com.benet.common.utils.uuid.UuidUtils;
import com.benet.common.utils.web.ServletUtils;
import com.benet.framework.security.LoginUser;
import com.benet.framework.security.service.MyJwtokenService;
import com.benet.sys.vmodel.DeptmentVo;
import com.benet.sys.vmodel.PermitInfoVo;
import com.benet.system.domain.SysDepartment;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.benet.system.domain.SysPermitinfo;
import com.benet.system.service.ISysPermitinfoService;
import com.benet.common.annotation.Oplog;
import com.benet.common.core.controller.BaseController;
import com.benet.common.core.domain.AjaxResult;
import com.benet.common.enums.BusinessType;
import com.benet.common.utils.poi.ExcelUtils;
import com.benet.common.utils.string.StringUtils;
import com.benet.common.core.pager.TableDataInfo;

/**
 * 菜单权限Controller
 * 
 * @author yoxking
 * @date 2020-04-20
 */
@RestController
@RequestMapping("/sys/permitinfo")
public class SysPermitinfoController extends BaseController
{
    @Autowired
    private MyJwtokenService tokenService;

    @Autowired
    private ISysPermitinfoService sysPermitinfoService;
    /**
     * 首页
     */
    @PreAuthorize("@ps.hasPermit('system:permitinfo:index')")
    @GetMapping(value="/index")
    public ModelAndView index()
    {
        ModelAndView mv=new ModelAndView("index");
        return mv;
    }

    /**
     * 查询菜单权限列表
     */
    //@PreAuthorize("@ps.hasPermit('system:permitinfo:list')")
    @PostMapping(value = "/list")
    public TableDataInfo list(@RequestBody PageRequest pRequest)
    {
        int count = sysPermitinfoService.getCountByCondition(pRequest.getCondition());
        List<SysPermitinfo> list = sysPermitinfoService.getRecordsByPaging(pRequest.getPageIndex(), pRequest.getPageSize(), pRequest.getCondition(), "id", "Asc");
        return getDataTable(list, count);
    }

    /**
     * 查询部门信息树形列表
     */
    //@PreAuthorize("@ps.hasPermit('system:permitinfo:tree')")
    @GetMapping(value = "/tree")
    public TableDataInfo tree() {
        int count = sysPermitinfoService.getCountByCondition("");
        List<PermitInfoVo> list = buildPermitTree("0");
        return getDataTable(list, count);
    }

    private List<PermitInfoVo> buildPermitTree(String parentNo) {

        List<PermitInfoVo> permitTree = null;
        PermitInfoVo permit = null;
        List<SysPermitinfo> infoList = sysPermitinfoService.getRecordsByClassNo(parentNo);

        if (infoList != null && infoList.size() > 0) {
            permitTree = new ArrayList<>();
            for (SysPermitinfo info : infoList) {
                permit = new PermitInfoVo();
                permit.setId(info.getPermitNo());
                permit.setLabel(info.getPermitName());
                permit.setPermitNo(info.getPermitNo());
                permit.setPermitName(info.getPermitName());
                permit.setParentNo(info.getParentNo());
                permit.setOrderNo(info.getOrderNo());
                permit.setComments(info.getComments());
                permit.setChildren(buildPermitTree(info.getPermitNo()));
                permitTree.add(permit);
            }
        }
        return permitTree;
    }


    /**
     * 新增菜单权限
     */
    //@PreAuthorize("@ps.hasPermit('system:permitinfo:insert')")
    @Oplog(title = "菜单权限", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult insert(@RequestBody SysPermitinfo sysPermitinfo) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        sysPermitinfo.setPermitNo(UuidUtils.shortUUID());
        sysPermitinfo.setCreateBy(loginUser.getUser().getUserNo());
        sysPermitinfo.setUpdateBy(loginUser.getUser().getUserNo());
        return toAjax(sysPermitinfoService.AddNewRecord(sysPermitinfo));
    }

    /**
     * 编辑菜单权限
     */
    //@PreAuthorize("@ps.hasPermit('system:permitinfo:update')")
    @Oplog(title = "菜单权限", businessType = BusinessType.UPDATE)
    @PutMapping
        public AjaxResult update(@RequestBody SysPermitinfo sysPermitinfo) {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        sysPermitinfo.setUpdateBy(loginUser.getUser().getUserNo());
            return toAjax(sysPermitinfoService.UpdateRecord(sysPermitinfo));
        }

    /**
     * 保存菜单权限
     */
    //@PreAuthorize("@ps.hasPermit('system:permitinfo:save')")
    @Oplog(title = "菜单权限", businessType = BusinessType.SAVE)
    @PostMapping(value = "/save")
    public AjaxResult save(@RequestBody SysPermitinfo sysPermitinfo) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNull(sysPermitinfoService.getRecordByNo(sysPermitinfo.getPermitNo()))) {
            sysPermitinfo.setPermitNo(UuidUtils.shortUUID());
            sysPermitinfo.setCreateBy(loginUser.getUser().getUserNo());
            sysPermitinfo.setUpdateBy(loginUser.getUser().getUserNo());
            return toAjax(sysPermitinfoService.AddNewRecord(sysPermitinfo));
        } else {
            sysPermitinfo.setUpdateBy(loginUser.getUser().getUserNo());
            return toAjax(sysPermitinfoService.UpdateRecord(sysPermitinfo));
        }
    }

    /**
     * 删除菜单权限
     */
    //@PreAuthorize("@ps.hasPermit('system:permitinfo:delete')")
    @Oplog(title = "菜单权限", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable("ids") String[] ids)
    {
        return toAjax(sysPermitinfoService.SoftDeleteByNos(ids));
    }

    /**
     * 获取菜单权限详细信息
     */
    //@PreAuthorize("@ps.hasPermit('system:permitinfo:detail')")
    @GetMapping(value = "/{id}")
    public AjaxResult detail(@PathVariable("id") String id)
    {
        return AjaxResult.success(sysPermitinfoService.getRecordByNo(id));
    }

    /**
     * 导出菜单权限列表
     */
    //@PreAuthorize("@ps.hasPermit('system:permitinfo:export')")
    @Oplog(title = "菜单权限", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@RequestBody PageRequest pRequest)
    {
        int count = sysPermitinfoService.getCountByCondition(pRequest.getCondition());

        List<SysPermitinfo> list = sysPermitinfoService.getRecordsByPaging(1,count,pRequest.getCondition(),"id","Asc");
        ExcelUtils<SysPermitinfo> util = new ExcelUtils<SysPermitinfo>(SysPermitinfo.class);
        return util.exportExcel(list, "SysPermitinfo");
    }

}