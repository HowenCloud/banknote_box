package com.ixilink.banknote_box.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @description: 出库线路的各类钞箱应有数量
 * @author: 张皓峰
 * @date: 2019-12-06 11:17
 */
@Data
@ApiModel
public class OutLineNormalResult {

    @ApiModelProperty(value = "DB钞箱数量")
    private Integer dbNum = 0;
    @ApiModelProperty(value = "DB空钞箱数量")
    private Integer dbEmptyNum = 0;
    @ApiModelProperty(value = "DB废钞箱数量")
    private Integer dbScrapNum = 0;

    @ApiModelProperty(value = "DF钞箱数量")
    private Integer dfNum = 0;
    @ApiModelProperty(value = "DF空钞箱数量")
    private Integer dfEmptyNum = 0;
    @ApiModelProperty(value = "DF废钞箱数量")
    private Integer dfScrapNum = 0;

    @ApiModelProperty(value = "CRS钞箱数量")
    private Integer crsNum = 0;
    @ApiModelProperty(value = "CRS空钞箱数量")
    private Integer crsEmptyNum = 0;
    @ApiModelProperty(value = "CRS废钞箱数量")
    private Integer crsScrapNum = 0;

    @ApiModelProperty(value = "SCRS钞箱数量")
    private Integer scrsNum = 0;
    @ApiModelProperty(value = "SCRS空钞箱数量")
    private Integer scrsEmptyNum = 0;
    @ApiModelProperty(value = "SCRS废钞箱数量")
    private Integer scrsScrapNum = 0;

    public OutLineNormalResult() {
    }

    public OutLineNormalResult(List<Map<String, Object>> list) {
        for (Map<String, Object> map:list){
            Integer totalBox = Integer.valueOf(map.get("totalBox").toString());
            switch (map.get("typeName").toString()){
                case "db":
                    this.dbNum = totalBox;
                    break;
                case "dbScrap":
                    this.dbScrapNum = totalBox;
                    break;
                case "df":
                     this.dfNum = totalBox;
                    break;
                case "dfScrap":
                    this.dfScrapNum = totalBox;
                    break;
                case "crs":
                    this.crsNum = totalBox;
                    break;
                case "crsEmpty":
                    this.crsEmptyNum = totalBox;
                    break;
                case "crsScrap":
                     this.crsScrapNum = totalBox;
                    break;
                case "scrs":
                     this.scrsNum = totalBox;
                    break;
                case "scrsEmpty":
                     this.scrsEmptyNum = totalBox;
                    break;
                case "scrsScrap":
                     this.scrsScrapNum = totalBox;
                    break;
            }
        }
    }
}
