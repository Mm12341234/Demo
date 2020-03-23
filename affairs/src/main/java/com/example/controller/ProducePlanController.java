package com.example.controller;

import com.example.entity.ProducePlanEntity;
import com.example.service.ProducePlanService;
import com.example.utils.Query;
import com.example.utils.R;
import com.example.utils.PageMap;
import com.example.utils.PageUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Author Liumq
 * Date  2020-03-05
 */
@RestController
@RequestMapping("produceplan")
public class ProducePlanController {
    @Autowired
    private ProducePlanService producePlanService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("produceplan:list")
    public R list(@RequestBody PageMap pageMap) {
        //查询列表数据
        Query query = new Query(pageMap);

        List<ProducePlanEntity> producePlanList = producePlanService.queryList(query);
        int total = producePlanService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(producePlanList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
    * 查看信息
    */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("produceplan:info")
    public R info(@PathVariable("id") Integer id) {
       ProducePlanEntity producePlan = producePlanService.queryObject(id);
        return R.ok().put("producePlan", producePlan);
    }

    /**
    * 保存
    */
    @RequestMapping("/save")
    @RequiresPermissions("produceplan:save")
    public R save(@RequestBody ProducePlanEntity producePlan) {
        producePlanService.save(producePlan);
        return R.ok();
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    @RequiresPermissions("produceplan:update")
    public R update(@RequestBody ProducePlanEntity producePlan) {
        producePlanService.update(producePlan);
        return R.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    @RequiresPermissions("produceplan:delete")
    public R delete(@RequestBody Integer[] ids) {
        producePlanService.deleteBatch(ids);
        return R.ok();
    }

    /**
    * 查看所有列表
    */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {
    List<ProducePlanEntity> list = producePlanService.queryList(params);
        return R.ok().put("list", list);
    }


}
