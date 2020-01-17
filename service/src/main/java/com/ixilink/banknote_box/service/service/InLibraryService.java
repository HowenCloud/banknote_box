package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.common.model.UserSimple;
import com.ixilink.banknote_box.common.pojo.InLibraryTask;
import com.ixilink.banknote_box.service.parameter_model.banknote_line.FindLineModel;
import com.ixilink.banknote_box.service.parameter_model.in_library.FindAtmModel;
import com.ixilink.banknote_box.service.parameter_model.in_library.FindBoxModel;
import com.ixilink.banknote_box.service.parameter_model.in_library.FindTaskModel;
import com.ixilink.banknote_box.service.parameter_model.in_library.SupplementBox;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2020-01-10 14:41
 */
public interface InLibraryService {

    List<InLibraryTask> findTask(FindTaskModel param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> findInTaskDetails(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    List<UserSimple> getLineUser(Integer id, HttpServletRequest request, HttpServletResponse response);

    List<Map<String,Object>> findInLine(FindLineModel param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> findInLineDetails(Integer id, HttpServletRequest request, HttpServletResponse response);

    List<Map<String,Object>> findAtm(FindAtmModel param, HttpServletRequest request, HttpServletResponse response);

    List<Map<String,Object>> findNoDepositAtm(FindAtmModel param, HttpServletRequest request, HttpServletResponse response);

    List<Map<String,Object>> findNoDepositBox(FindBoxModel param, HttpServletRequest request, HttpServletResponse response) throws Exception;

    void getBox(Integer id, HttpServletRequest request, HttpServletResponse response);

    List<InLibraryTask> findHandoverTask(HttpServletRequest request, HttpServletResponse response);

    List<Map<String,Object>> findHandoverLine(Integer taskId, HttpServletRequest request, HttpServletResponse response);

    List<Map<String,Object>> findHandoverTakeBox(Integer lineId, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> findHandoverNoDeposit(Integer lineId, HttpServletRequest request, HttpServletResponse response);

    void supplementIn(SupplementBox param, HttpServletRequest request, HttpServletResponse response);
}
