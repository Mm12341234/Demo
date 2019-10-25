package ${BasePackageName}${ControllerPackageName};

import ${BasePackageName}${EntityPackageName}.${ClassName}Entity;
import ${BasePackageName}${ServicePackageName}.${ClassName}Service;
import ${BasePackageName}utils.Query;
import ${BasePackageName}utils.R;
import ${BasePackageName}utils.PageMap;
import ${BasePackageName}utils.PageUtils;
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
 * Author ${Author}
 * Date  ${Date}
 */
@RestController
@RequestMapping("${pathName}")
public class ${ClassName}Controller {
    @Autowired
    private ${ClassName}Service ${EntityName}Service;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("${pathName}:list")
    public R list(@RequestBody PageMap pageMap) {
        //查询列表数据
        Query query = new Query(pageMap);

        List<${ClassName}Entity> ${EntityName}List = ${EntityName}Service.queryList(query);
        int total = ${EntityName}Service.queryTotal(query);

        PageUtils pageUtil = new PageUtils(${EntityName}List, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
    * 查看信息
    */
    @RequestMapping("/info/{${PrimaryKey.attrname}}")
    @RequiresPermissions("${pathName}:info")
    public R info(@PathVariable("${PrimaryKey.attrname}") ${PrimaryKey.attrType} ${PrimaryKey.attrname}) {
       ${ClassName}Entity ${EntityName} = ${EntityName}Service.queryObject(${PrimaryKey.attrname});
        return R.ok().put("${EntityName}", ${EntityName});
    }

    /**
    * 保存
    */
    @RequestMapping("/save")
    @RequiresPermissions("${pathName}:save")
    public R save(@RequestBody ${ClassName}Entity ${EntityName}) {
        ${EntityName}Service.save(${EntityName});
        return R.ok();
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    @RequiresPermissions("${pathName}:update")
    public R update(@RequestBody ${ClassName}Entity ${EntityName}) {
        ${EntityName}Service.update(${EntityName});
        return R.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    @RequiresPermissions("${pathName}:delete")
    public R delete(@RequestBody ${PrimaryKey.attrType}[] ${PrimaryKey.attrname}s) {
        ${EntityName}Service.deleteBatch(${PrimaryKey.attrname}s);
        return R.ok();
    }

    /**
    * 查看所有列表
    */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {
    List<${ClassName}Entity> list = ${EntityName}Service.queryList(params);
        return R.ok().put("list", list);
    }


}
