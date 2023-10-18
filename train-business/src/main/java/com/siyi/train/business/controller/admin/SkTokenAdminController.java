package com.siyi.train.business.controller.admin;

import com.siyi.train.common.vo.Result;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.business.dto.SkTokenQueryDto;
import com.siyi.train.business.dto.SkTokenSaveDto;
import com.siyi.train.business.vo.SkTokenQueryVo;
import com.siyi.train.business.service.SkTokenService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/sk-token")
public class SkTokenAdminController {

    private final SkTokenService skTokenService;

    public SkTokenAdminController(SkTokenService skTokenService) {
        this.skTokenService = skTokenService;
    }

    @PostMapping("/save")
    public Result<Object> save(@Valid @RequestBody SkTokenSaveDto dto) {
        this.skTokenService.save(dto);
        return Result.success();
    }

    @GetMapping("/query-list")
    public Result<PageVo<SkTokenQueryVo>> queryList(@Valid SkTokenQueryDto dto) {
        PageVo<SkTokenQueryVo> list = this.skTokenService.queryList(dto);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        this.skTokenService.delete(id);
        return Result.success();
    }

}
