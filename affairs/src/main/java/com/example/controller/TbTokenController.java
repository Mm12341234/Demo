package com.example.controller;

import com.example.entity.TbTokenEntity;
import com.example.service.TbTokenService;
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
 * Date  2019-11-05
 */
@RestController
@RequestMapping("tbtoken")
public class TbTokenController {
    @Autowired
    private TbTokenService tbTokenService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("tbtoken:list")
    public R list(@RequestBody PageMap pageMap) {
        //查询列表数据
        Query query = new Query(pageMap);

        List<TbTokenEntity> tbTokenList = tbTokenService.queryList(query);
        int total = tbTokenService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(tbTokenList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
    * 查看信息
    */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("tbtoken:info")
    public R info(@PathVariable("userId") Long userId) {
       TbTokenEntity tbToken = tbTokenService.queryObject(userId);
        return R.ok().put("tbToken", tbToken);
    }

    /**
    * 保存
    */
    @RequestMapping("/save")
    @RequiresPermissions("tbtoken:save")
    public R save(@RequestBody TbTokenEntity tbToken) {
        tbTokenService.save(tbToken);
        return R.ok();
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    @RequiresPermissions("tbtoken:update")
    public R update(@RequestBody TbTokenEntity tbToken) {
        tbTokenService.update(tbToken);
        return R.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    @RequiresPermissions("tbtoken:delete")
    public R delete(@RequestBody Long[] userIds) {
        tbTokenService.deleteBatch(userIds);
        return R.ok();
    }

    /**
    * 查看所有列表
    */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {
    List<TbTokenEntity> list = tbTokenService.queryList(params);
        return R.ok().put("list", list);
    }


}
