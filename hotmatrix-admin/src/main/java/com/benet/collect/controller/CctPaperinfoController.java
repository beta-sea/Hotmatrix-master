package com.benet.collect.controller;

import java.util.ArrayList;
import java.util.List;

import com.benet.collect.domain.CctQuestinfo;
import com.benet.collect.domain.CctQuestopts;
import com.benet.collect.service.ICctQuestinfoService;
import com.benet.collect.service.ICctQuestoptsService;
import com.benet.collect.vmodel.QuestInfoVo;
import com.benet.collect.vmodel.QuestOptsVo;
import com.benet.common.core.pager.PageRequest;
import com.benet.common.core.pager.TableDataInfo;
import com.benet.common.utils.uuid.UuidUtils;
import com.benet.common.utils.web.ServletUtils;
import com.benet.framework.security.LoginUser;
import com.benet.framework.security.service.MyJwtokenService;
import io.swagger.annotations.Api;
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
import com.benet.collect.domain.CctPaperinfo;
import com.benet.collect.service.ICctPaperinfoService;
import com.benet.common.annotation.Oplog;
import com.benet.common.core.controller.BaseController;
import com.benet.common.core.domain.AjaxResult;
import com.benet.common.enums.BusinessType;
import com.benet.common.utils.poi.ExcelUtils;
import com.benet.common.utils.string.StringUtils;

/**
 * 问卷信息Controller
 * 
 * @author yoxking
 * @date 2020-08-27
 */
@Api(value = "collect/paperinfo", tags = "问卷信息控制器")
@RestController
@RequestMapping("/collect/paperinfo")
public class CctPaperinfoController extends BaseController
{
    @Autowired
    private MyJwtokenService tokenService;

    @Autowired
    private ICctPaperinfoService cctPaperinfoService;

    @Autowired
    private ICctQuestinfoService cctQuestinfoService;

    @Autowired
    private ICctQuestoptsService cctQuestoptsService;
    /**
     * 首页
     */
    @PreAuthorize("@ps.hasPermit('collect:paperinfo:index')")
    @GetMapping(value="/index")
    public ModelAndView index()
    {
        ModelAndView mv=new ModelAndView("index");
        return mv;
    }

    /**
     * 查询问卷信息列表
     */
    @PreAuthorize("@ps.hasPermit('collect:paperinfo:list')")
    @PostMapping(value = "/list")
    public TableDataInfo list(@RequestBody PageRequest pRequest)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        int count = cctPaperinfoService.getCountByCondition(loginUser.getUser().getAppCode(),pRequest.getCondition());
        List<CctPaperinfo> list = cctPaperinfoService.getRecordsByPaging(loginUser.getUser().getAppCode(),pRequest.getPageIndex(), pRequest.getPageSize(), pRequest.getCondition(), "id", "Asc");
        return getDataTable(list, count);
    }

    /**
     * 查询问卷题库集列表
     */
    @PreAuthorize("@ps.hasPermit('collect:paperinfo:list')")
    @GetMapping(value = "/paperQuests/{id}")
    public AjaxResult paperQuests(@PathVariable("id") String id)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        CctPaperinfo paperInfo=cctPaperinfoService.getRecordByNo(loginUser.getUser().getAppCode(),id);

        List<QuestInfoVo> questVoList=new ArrayList<>();
        List<QuestOptsVo> optsVoList=new ArrayList<>();
        QuestInfoVo questVo=null;
        QuestOptsVo optsVo=null;

        List<CctQuestinfo> questList = cctQuestinfoService.getRecordsByClassNo(loginUser.getUser().getAppCode(),paperInfo.getClassNo());
        if(questList!=null&&questList.size()>0){
            for(CctQuestinfo questItem:questList){
                questVo=new QuestInfoVo();
                questVo.setQuestNo(questItem.getQuestNo());
                questVo.setQuestTitle(questItem.getQuestTitle());
                questVo.setQuestDesc(questItem.getQuestDesc());
                questVo.setQuestType(questItem.getQuestType());
                questVo.setQuestMust(questItem.getQuestMust());
                questVo.setClassNo(questItem.getClassNo());
                questVo.setOrderNo(questItem.getOrderNo());
                questVo.setQuestScore(questItem.getQuestTscore());
                questVo.setCheckState(questItem.getCheckState());
                questVo.setComments(questItem.getComments());

                List<CctQuestopts> optsList=cctQuestoptsService.getRecordsByClassNo(loginUser.getUser().getAppCode(),questItem.getQuestNo());
                if(optsList!=null&&optsList.size()>0){
                    optsVoList.clear();
                    for(CctQuestopts optItem:optsList){
                        optsVo=new QuestOptsVo();
                        optsVo.setOptNo(optItem.getOptsNo());
                        optsVo.setOptTitle(optItem.getOptsTitle());
                        optsVo.setOptScore(optItem.getOptsScore());

                        optsVoList.add(optsVo);
                    }
                }
                questVo.setOptions((QuestOptsVo[])optsVoList.toArray(new QuestOptsVo[0]));
                questVoList.add(questVo);
            }
            return AjaxResult.success(questVoList);
        }

        return AjaxResult.success();
    }

    /**
     * 新增问卷信息
     */
    @PreAuthorize("@ps.hasPermit('collect:paperinfo:addnew')")
    @Oplog(title = "问卷信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult insert(@RequestBody CctPaperinfo cctPaperinfo) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        cctPaperinfo.setPaperNo(UuidUtils.shortUUID());
        cctPaperinfo.setCreateBy(loginUser.getUser().getUserNo());
        cctPaperinfo.setUpdateBy(loginUser.getUser().getUserNo());
        return toAjax(cctPaperinfoService.AddNewRecord(loginUser.getUser().getAppCode(),cctPaperinfo));
    }

    /**
     * 编辑问卷信息
     */
    @PreAuthorize("@ps.hasPermit('collect:paperinfo:update')")
    @Oplog(title = "问卷信息", businessType = BusinessType.UPDATE)
    @PutMapping
        public AjaxResult update(@RequestBody CctPaperinfo cctPaperinfo) {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        cctPaperinfo.setUpdateBy(loginUser.getUser().getUserNo());
            return toAjax(cctPaperinfoService.UpdateRecord(loginUser.getUser().getAppCode(),cctPaperinfo));
        }

    /**
     * 保存问卷信息
     */
    @PreAuthorize("@ps.hasPermit('collect:paperinfo:save')")
    @Oplog(title = "问卷信息", businessType = BusinessType.SAVE)
    @PostMapping(value = "/save")
    public AjaxResult save(@RequestBody CctPaperinfo cctPaperinfo) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNull(cctPaperinfoService.getRecordByNo(loginUser.getUser().getAppCode(),cctPaperinfo.getPaperNo()))) {
            cctPaperinfo.setPaperNo(UuidUtils.shortUUID());
            cctPaperinfo.setCreateBy(loginUser.getUser().getUserNo());
            cctPaperinfo.setUpdateBy(loginUser.getUser().getUserNo());
            return toAjax(cctPaperinfoService.AddNewRecord(loginUser.getUser().getAppCode(),cctPaperinfo));
        } else {
            cctPaperinfo.setUpdateBy(loginUser.getUser().getUserNo());
            return toAjax(cctPaperinfoService.UpdateRecord(loginUser.getUser().getAppCode(),cctPaperinfo));
        }
    }

    /**
     * 删除问卷信息
     */
    @PreAuthorize("@ps.hasPermit('collect:paperinfo:delete')")
    @Oplog(title = "问卷信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult delete(@PathVariable("ids") String[] ids)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return toAjax(cctPaperinfoService.SoftDeleteByNos(loginUser.getUser().getAppCode(),ids));
    }

    /**
     * 获取问卷信息详细信息
     */
    @PreAuthorize("@ps.hasPermit('collect:paperinfo:detail')")
    @GetMapping(value = "/{id}")
    public AjaxResult detail(@PathVariable("id") String id)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return AjaxResult.success(cctPaperinfoService.getRecordByNo(loginUser.getUser().getAppCode(),id));
    }

    /**
     * 导出问卷信息列表
     */
    @PreAuthorize("@ps.hasPermit('collect:paperinfo:export')")
    @Oplog(title = "问卷信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(@RequestBody PageRequest pRequest)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        int count = cctPaperinfoService.getCountByCondition(loginUser.getUser().getAppCode(),pRequest.getCondition());

        List<CctPaperinfo> list = cctPaperinfoService.getRecordsByPaging(loginUser.getUser().getAppCode(),1,count,pRequest.getCondition(),"id","Asc");
        ExcelUtils<CctPaperinfo> util = new ExcelUtils<CctPaperinfo>(CctPaperinfo.class);
        return util.exportExcel(list, "CctPaperinfo");
    }

}
