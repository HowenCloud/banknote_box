package com.ixilink.banknote_box.service.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ixilink.banknote_box.common.exception.BusinessException;
import com.ixilink.banknote_box.common.model.Code;
import com.ixilink.banknote_box.common.model.Result;
import com.ixilink.banknote_box.common.pojo.CarBox;
import com.ixilink.banknote_box.common.util.annotation.SysLogAnnotation;
import com.ixilink.banknote_box.service.service.CarBoxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;



@Log4j2
@RestController
@RequestMapping("carBox")
@Api(value = "/carBox", description = "钞箱管理")
public class CarBoxController {
    @Resource
    private CarBoxService carBoxService;

    @SysLogAnnotation("5:添加钞箱")
    @ApiOperation(value = "添加钞箱", notes = "serialNumber(编号) boxType（类型 int） activeRfid（有源rfid） passiveRfid（无源rfid）")//serialNumber boxType activeRfid passiveRfid
    @PutMapping(value = "insert")
    public Result insert(@RequestBody CarBox param, HttpServletRequest request, HttpServletResponse response){
        try {
            param.setCreateTime(new Date().getTime());
            carBoxService.insert(param,request);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            return result;
        } catch(DuplicateKeyException e) {
            String re ="";
            if(e.getMessage().contains("serialNumberShouldDifferet")){
                re="钞箱编号不能重复!";
            }else if(e.getMessage().contains("activeRfidShouldDifferet")){
                re="钞箱有源编号不能重复!";
            }else if(e.getMessage().contains("passiveRfidShouldDifferet")){
                re="钞箱无源编号不能重复!";
            }
            return new Result(666,re);

        } catch(BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }
    @ApiOperation(value = "钞箱查询", notes = "传入参数：pageNumber  pageSize serialNumber(钞箱编号) boxType（钞箱类型） boxUseState（钞箱使用状态 :1空闲 2待使用 3待交接 4待加钞 5已加钞 6待清机） boxTransportState（钞箱运输状态 1正常 2低电量）\n"
            + "返回参数：id serialNumber(钞箱编号) boxType钞箱类型） site(位置信息) boxUseState(钞箱使用状态） boxTransportState（钞箱运输状态）activeRfid（有源rfid） passiveRfid（无源rfid) boxUseStateId(使用状态 id) boxTransportStateId(钞箱运输状态 id)  boxTypeId(钞箱类型)")//serialNumber boxType boxUseState boxTransportState //id serialNumber boxType site boxUseState boxTransportState activeRfid passiveRfid
    @PostMapping(value = "select")
    public Result select(@RequestBody Map param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        PageHelper.startPage((Integer) param.get("pageNumber"),(Integer) param.get("pageSize"));

        List<Map<String, Object>> selectCarBox = carBoxService.select(param,request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("total",((Page<?>) selectCarBox).getTotal());
        result.getData().put("returnObject", selectCarBox);
        return result;
    }
    @ApiOperation(value = "钞箱修改", notes = "id(id) activeRfid(有源rfid) passiveRfid（无源rfid） boxType（钞箱类型） serialNumber（钞箱编号）")
    @PutMapping(value = "updateByPrimaryKey")//id activeRfid passiveRfid boxType serialNumber
    public Result updateByPrimaryKey(@RequestBody CarBox param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            carBoxService.updateByPrimaryKey(param);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "钞箱删除", notes = "id")
    @DeleteMapping(value = "delete/{id}")
    public Result deleteByPrimaryKey(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            carBoxService.deleteByPrimaryKey(id);
            Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
            return result;
        }catch (BusinessException e){
            return new Result(e.getCode(),e.getErrorMessage());
        }catch (Exception e){
            return new Result(Code.SYSTEM_ERROR.getCode(), Code.SYSTEM_ERROR.getMessage());
        }
    }

    @ApiOperation(value = "钞箱类型查询", notes = "传入参数：无")
    @GetMapping(value = "selectCarBoxType")
    public Result selectCarBoxType( HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        List<Map<String, Object>> carBoxType= carBoxService.selectCarBoxType();
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("baggingPersonnel", carBoxType);
        return result;
    }
    @ApiOperation(value = "各种钞箱数量查询", notes = "传入参数：无\n"
            + "allNumber(钞箱总数量)  DBNumber(DB钞箱总数量)  DBAbandonNumber(DB费钞箱总数量)  DFNumber(DF钞箱总数量)  DFAbandonNumber(DF费钞箱总数量)  CRSNumber(CRS钞箱总数量)  CRSAbandonNumber(CRS费钞箱总数量)  SCRSNumber(SCRS钞箱总数量)  SCRSAbandonNumber(SCRS费钞箱总数量) ")
    @GetMapping(value = "caxBoxNums")
    public Result selectCaxBoxNums( HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Map<String, Object> caxBoxNums = carBoxService.selectCaxBoxNums(request);
        Result result = new Result(Code.SUCCESS.getCode(), Code.SUCCESS.getMessage());
        result.getData().put("caxBoxNums", caxBoxNums);
        return result;
    }
}
