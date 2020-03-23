package com.example.controller;

import com.example.entity.ChainspeedEntity;
import com.example.service.ChainspeedService;
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
@RequestMapping("chainspeed")
public class ChainspeedController {
    @Autowired
    private ChainspeedService chainspeedService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("chainspeed:list")
    public R list(@RequestBody PageMap pageMap) {
        //查询列表数据
        Query query = new Query(pageMap);

        List<ChainspeedEntity> chainspeedList = chainspeedService.queryList(query);
        int total = chainspeedService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(chainspeedList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
    * 查看信息
    */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("chainspeed:info")
    public R info(@PathVariable("id") Integer id) {
       ChainspeedEntity chainspeed = chainspeedService.queryObject(id);
        return R.ok().put("chainspeed", chainspeed);
    }

    /**
    * 保存
    */
    @RequestMapping("/save")
    @RequiresPermissions("chainspeed:save")
    public R save(@RequestBody ChainspeedEntity chainspeed) {
        chainspeedService.save(chainspeed);
        return R.ok();
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    @RequiresPermissions("chainspeed:update")
    public R update(@RequestBody ChainspeedEntity chainspeed) {
        chainspeedService.update(chainspeed);
        return R.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    @RequiresPermissions("chainspeed:delete")
    public R delete(@RequestBody Integer[] ids) {
        chainspeedService.deleteBatch(ids);
        return R.ok();
    }

    /**
    * 查看所有列表
    */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {
    List<ChainspeedEntity> list = chainspeedService.queryList(params);
        return R.ok().put("list", list);
    }


}
