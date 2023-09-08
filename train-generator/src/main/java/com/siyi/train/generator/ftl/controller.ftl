package com.siyi.train.${module}.controller;

import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.${module}.dto.${Domain}QueryDto;
import com.siyi.train.${module}.dto.${Domain}SaveDto;
import com.siyi.train.${module}.vo.${Domain}QueryVo;
import com.siyi.train.${module}.service.${Domain}Service;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/${do_main}")
public class ${Domain}Controller {

    private final ${Domain}Service ${domain}Service;

    public ${Domain}Controller(${Domain}Service ${domain}Service) {
        this.${domain}Service = ${domain}Service;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody ${Domain}SaveDto dto) {
        ${domain}Service.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<${Domain}QueryVo>> queryList(@Valid ${Domain}QueryDto dto) {
        PageVo<${Domain}QueryVo> list = ${domain}Service.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        ${domain}Service.delete(id);
        return Result.success();
    }

}
