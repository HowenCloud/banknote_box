package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.common.pojo.*;
import com.ixilink.banknote_box.service.parameter_model.readyMoney.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ReadyMoneyTaskService {
    void insert(InsertReadyMoneyTaskModel record, HttpServletRequest request);

    List<ReadyMoneyTaskReturn> selectByInitiator(HttpServletRequest request);

  List<ReadyMoneyTaskReturn> selectApproving(HttpServletRequest request);

    List<ReadyMoneyTaskReturn> selectApprovedAndPerforming(HttpServletRequest request);

    void approve(Map<String,Object> param,HttpServletRequest request);

    List<ReadyMoneyTaskReturn> selectPerformed(HttpServletRequest request);

  Integer insertOperation(ReadyMoneyDetailsModel parm,HttpServletRequest request);

      List<ReadyMoneyExecutorTaskModel> selectExecutorTaskDetails(Integer taskId);

    List<ReadyMoneyReturnDetails> performedDetails(Map<String,Object> param);

    Map<String,Object> performedHead(Integer taskId);

    void handOver(Map<String,Object> param,HttpServletRequest request);

    Map<String,Object> selectUnderGroupLeaderDetails(Map<String,Object> param,HttpServletRequest request);

    Map<String,Object> selectUnderLibraryDetails(LibraryerParamModel param, HttpServletRequest request);

  void insertApply(ReadyMoneyApplyModel readyMoneyApplyModel,HttpServletRequest request);

  List<Map<String,Object>>  selectHandoverPerson(HttpServletRequest request);

    Map<String,Object> selectNumberAndType(String activeRfid);

    List<Map<String,Object>> selectGrouperNameAndId(HttpServletRequest request);

    List<Map<String,Object>> selectLibraryNameAndId(HttpServletRequest request);

  List<Map<String,Object>> selectApplyByGrouper(HttpServletRequest request);

  ReadyMoneyApplyTarget selectApplyTarget(Integer id);

  Map<String,Object> selectApplyCarBox(String carBoxNumber,HttpServletRequest request);


  void refund(Integer fundId,HttpServletRequest request);

  void updateFund(Integer fundId,HttpServletRequest request);

  ReadyMoneyApplyTarget supplement(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response);

  ReadyMoneyApplyTarget clearLastStep(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response);

  Map<String,Object> selectFaceGrouperLibraryer(Integer id, HttpServletRequest request);

  void updateTaskSupplement(Map<String,Object>  param, HttpServletRequest request);



  void outLibraryApply(ReadyMoneyApplyModel readyMoneyApplyModel, HttpServletRequest request);

  void boxHandOver(List<Integer> libraryId,HttpServletRequest request);

}
