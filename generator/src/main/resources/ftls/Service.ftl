package ${BasePackageName}${ServicePackageName};

import ${BasePackageName}${DaoPackageName}.${ClassName}Dao;
import ${BasePackageName}${EntityPackageName}.${ClassName}Entity;
${InterfaceImport}
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author ${Author}
 * Date  ${Date}
 */
@Service
public class ${ClassName}Service${Impl} {
    @Autowired
    private ${ClassName}Dao ${EntityName}Dao;

    @Override
    public List<${ClassName}Entity> queryList(Map<String, Object> map) {
         return ${EntityName}Dao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
         return ${EntityName}Dao.queryTotal(map);
    }

    @Override
    public ${ClassName}Entity queryObject(${PrimaryKey.attrType} ${PrimaryKey.attrname}) {
         return ${EntityName}Dao.queryObject(${PrimaryKey.attrname});
    }

    @Override
    public int save(${ClassName}Entity ${EntityName}) {
         return ${EntityName}Dao.save(${EntityName});
    }

    @Override
    public int update(${ClassName}Entity ${EntityName}) {
         return ${EntityName}Dao.update(${EntityName});
    }

    @Override
    public int delete(${PrimaryKey.attrType} ${PrimaryKey.attrname}) {
         return ${EntityName}Dao.delete(${PrimaryKey.attrname});
    }

    @Override
    public int deleteBatch(${PrimaryKey.attrType}[] ${PrimaryKey.attrname}s) {
         return ${EntityName}Dao.deleteBatch(${PrimaryKey.attrname}s);
    }
}
