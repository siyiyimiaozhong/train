package com.siyi.train.business.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationSaveDto implements Serializable {

    /**
    * id
    */
    private Long id;
    /**
    * 站名
    */
                @NotBlank(message = "【站名】不能为空")
    private String name;
    /**
    * 站名拼音
    */
                @NotBlank(message = "【站名拼音】不能为空")
    private String namePinyin;
    /**
    * 站名拼音首字母
    */
                @NotBlank(message = "【站名拼音首字母】不能为空")
    private String namePy;
    /**
    * 新增时间
    */
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
    * 修改时间
    */
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
}
