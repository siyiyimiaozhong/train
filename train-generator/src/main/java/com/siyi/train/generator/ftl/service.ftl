package com.siyi.train.${module}.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siyi.train.common.vo.PageVo;
import com.siyi.train.common.util.SnowUtil;
import com.siyi.train.${module}.pojo.${Domain};
import com.siyi.train.${module}.pojo.${Domain}Example;
import com.siyi.train.${module}.mapper.${Domain}Mapper;
import com.siyi.train.${module}.dto.${Domain}QueryDto;
import com.siyi.train.${module}.dto.${Domain}SaveDto;
import com.siyi.train.${module}.vo.${Domain}Queryvo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Sl4j
@Service
public class ${Domain}Service {

    private final ${Domain}Mapper ${domain}Mapper;

    public ${Domain}Service(${Domain}Mapper ${domain}Mapper) {
        this.${domain}Mapper = ${domain}Mapper;
    }

    public void save(${Domain}SaveDto dto) {
        DateTime now = DateTime.now();
        ${Domain} ${domain} = BeanUtil.copyProperties(dto, ${Domain}.class);
        if (ObjectUtil.isNull(${domain}.getId())) {
            ${domain}.setId(SnowUtil.getSnowflakeNextId());
            ${domain}.setCreateTime(now);
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.insert(${domain});
        } else {
            ${domain}.setUpdateTime(now);
            ${domain}Mapper.updateByPrimaryKey(${domain});
        }
    }

    public PageVo<${Domain}QueryVo> queryList(${Domain}QueryDto dto) {
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.setOrderByClause("id desc");
        ${Domain}Example.Criteria criteria = ${domain}Example.createCriteria();

        log.info("查询页码：{}", req.getPage());
        log.info("每页条数：{}", req.getSize());
        PageHelper.startPage(dto.getPage(), dto.getSize());
        List<${Domain}> ${domain}List = ${domain}Mapper.selectByExample(${domain}Example);

        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}List);
        log.info("总行数：{}", pageInfo.getTotal());
        log.info("总页数：{}", pageInfo.getPages());

        List<${Domain}QueryVo> list = BeanUtil.copyToList(${domain}List, ${Domain}QueryVo.class);

        PageVo<${Domain}QueryVo> pageVo = new PageResp<>();
        pageVo.setTotal(pageInfo.getTotal());
        pageVo.setList(list);
        return pageVo;
    }

    public void delete(Long id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}
