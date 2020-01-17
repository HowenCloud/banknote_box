package com.ixilink.banknote_box.service.service;

import com.ixilink.banknote_box.common.pojo.OutLibraryLine;
import com.ixilink.banknote_box.common.pojo.OutLibraryTask;
import com.ixilink.banknote_box.service.parameter_model.banknote_line.FindLineModel;
import com.ixilink.banknote_box.service.parameter_model.out_library.FindHandoverLineModel;
import com.ixilink.banknote_box.service.parameter_model.out_library.FindLineAtmModel;
import com.ixilink.banknote_box.service.parameter_model.out_library.FindTaskModel;
import com.ixilink.banknote_box.service.parameter_model.out_library.LibraryLineModel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: 张皓峰
 * @date: 2019-12-04 15:07
 */
public interface OutLibraryService {
    List<LibraryLineModel> importExcel(MultipartFile file, HttpServletRequest request, HttpServletResponse response);

    void createTask(Map<String,List<LibraryLineModel>> param, HttpServletRequest request, HttpServletResponse response) throws Exception;

    List<OutLibraryTask> findTask(FindTaskModel param, HttpServletRequest request, HttpServletResponse response);

    List<OutLibraryTask> findHandoverTask(Integer checkSchedule, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> findTaskDetails(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    List<Map<String,Object>> findLine(FindLineModel param, HttpServletRequest request, HttpServletResponse response);

    List<Map<String,Object>> findBoxingLine(FindLineModel param, HttpServletRequest request, HttpServletResponse response) throws Exception;

    List<OutLibraryLine> findHandoverLine(FindHandoverLineModel param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> findLineDetails(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    List<Map<String,Object>> findLineAtm(FindLineAtmModel param, HttpServletRequest request, HttpServletResponse response);

    void approvalTask(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response);

    void boxing(Map<String, Integer> param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> details(Integer id, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> supplementBox(String number, HttpServletRequest request, HttpServletResponse response);

    void supplement(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response);

    void next(Map<String, Integer> param, HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> findTaskDetailsAll(HttpServletRequest request, HttpServletResponse response);
}
