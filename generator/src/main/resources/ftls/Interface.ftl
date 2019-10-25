package ${BasePackageName}${InterfacePackageName};

import ${BasePackageName}${EntityPackageName}.${ClassName}Entity;

import java.util.List;
import java.util.Map;

/**
 * Author ${Author}
 * Date  ${Date}
 */
public interface ${ClassName}Service {

    /**
     * 分页查询
     *
     * @param map 参数
     * @return list
     */
    List<${ClassName}Entity> queryList(Map<String, Object> map);

    /**
    * 分页统计总数
    *
    * @param map 参数
    * @return 总数
    */
    int queryTotal(Map<String, Object> map);

    /**
    * 根据主键查询实体
    *
    * @param id 主键
    * @return 实体
    */
    ${ClassName}Entity queryObject(${PrimaryKey.attrType} ${PrimaryKey.attrname});

    /**
    * 保存实体
    *
    * @param ${ClassName} 实体
    * @return 保存条数
    */
    int save(${ClassName}Entity ${EntityName});

    /**
    * 根据主键更新实体
    *
    * @param ${ClassName} 实体
    * @return 更新条数
    */
    int update(${ClassName}Entity ${EntityName});

    /**
    * 根据主键删除
    *
    * @param ${PrimaryKey.attrname}
    * @return 删除条数
    */
    int delete(${PrimaryKey.attrType} ${PrimaryKey.attrname});

    /**
    * 根据主键批量删除
    *
    * @param ${PrimaryKey.attrname}s
    * @return 删除条数
    */
    int deleteBatch(${PrimaryKey.attrType}[] ${PrimaryKey.attrname}s);

}
