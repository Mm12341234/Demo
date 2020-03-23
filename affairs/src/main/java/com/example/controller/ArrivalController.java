package com.example.controller;

import com.example.entity.ArrivalEntity;
import com.example.entity.ChainspeedEntity;
import com.example.entity.ProducePlanEntity;
import com.example.fun0.DemandArrivalVo;
import com.example.service.ArrivalService;
import com.example.service.ChainspeedService;
import com.example.service.ProducePlanService;
import com.example.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author Liumq
 * Date  2020-03-05
 */
@RestController
@RequestMapping("arrival")
public class ArrivalController {
    @Autowired
    private ArrivalService arrivalService;
    @Autowired
    private ProducePlanService producePlanService;
    @Autowired
    private ChainspeedService chainspeedService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("arrival:list")
    public R list(@RequestBody PageMap pageMap) {
        //查询列表数据
        Query query = new Query(pageMap);

        List<ArrivalEntity> arrivalList = arrivalService.queryList(query);
        int total = arrivalService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(arrivalList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
    * 查看信息
    */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("arrival:info")
    public R info(@PathVariable("id") Integer id) {
       ArrivalEntity arrival = arrivalService.queryObject(id);
        return R.ok().put("arrival", arrival);
    }


    /**
     * 查看信息
     */
    @RequestMapping("/test")
    public R test(@RequestParam("parts") String parts) {
        arrivalService.test(parts);
        return R.ok();
    }

    @RequestMapping("/getAllParts")
    @ResponseBody
    public R getAllParts() {
        List<String> partList=arrivalService.getAllParts();
        return R.ok().put("partList",partList);
    }

    @RequestMapping("/getArrivalPartsDetail")
    @ResponseBody
    public R getArrivalPartsDetail(@RequestParam("parts") String parts) {
        //获取需求总数和到货总数
        DemandArrivalVo demandArrivalVo=arrivalService.getArrivalPartsDetail(parts);
        return R.ok().put("demandArrival",demandArrivalVo);
    }

    /**
     *导出数据
     */
    @RequestMapping("/excel")
    public void excel(String parts,HttpServletResponse response) {

        int year=2020,week=6;
        ExcelExport ee1 = new ExcelExport("多落点拆分");
        arrivalService.getData(year,week);
        List colList;
        List<Object[]> alllist = new ArrayList();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int i = 0;
//        for (InventoryRecordDetailVo entity : list) {
//            i++;
//            colList = new ArrayList();
//            colList.add(i);
//            colList.add(entity.getPlanName());
//            colList.add(entity.getParts());
//            colList.add(entity.getColor());
//            colList.add(entity.getFactoryid());
//            colList.add(entity.getBoxNum());
//            colList.add(entity.getPerBoxNum());
//            colList.add(entity.getBulkTotalNum());
//            colList.add(entity.getBulkBoxNum());
//            colList.add(entity.getTotalNum());
//            colList.add(entity.getUserAccount());
//            colList.add(entity.getUserName());
//            colList.add(entity.getRegionNo());
//            colList.add(entity.getStorageNo());
//            colList.add(entity.getType());
//            alllist.add(colList.toArray());
//        }
        String[] header = new String[]{"序号", "盘点计划", "零件号", "颜色", "厂家代码", "整箱箱数", "整箱包装数", "散件总数",
                "散件箱数","总数", "盘点人胸卡号","盘点人姓名", "区域号", "库位号", "盘点类型"};
        ee1.addSheetByArray("所有记录", alllist, header);
        ee1.export(response);
    }

    /**
    * 保存
    */
    @RequestMapping("/save")
    @RequiresPermissions("arrival:save")
    public R save(@RequestBody ArrivalEntity arrival) {
        arrivalService.save(arrival);
        return R.ok();
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    @RequiresPermissions("arrival:update")
    public R update(@RequestBody ArrivalEntity arrival) {
        arrivalService.update(arrival);
        return R.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    @RequiresPermissions("arrival:delete")
    public R delete(@RequestBody Integer[] ids) {
        arrivalService.deleteBatch(ids);
        return R.ok();
    }

    /**
    * 查看所有列表
    */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {
    List<ArrivalEntity> list = arrivalService.queryList(params);
        return R.ok().put("list", list);
    }


}
