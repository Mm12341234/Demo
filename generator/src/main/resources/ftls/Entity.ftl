package ${BasePackageName}${EntityPackageName};

import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Author ${Author}
 * Date  ${Date}
 */
public class ${ClassName}Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    <#list columns! as column>
       private ${column.attrType} ${column.attrname};
    </#list>

    <#list columns! as column>
       public void set${column.attrName}(${column.attrType} ${column.attrname}) {
          this.${column.attrname} = ${column.attrname};
       }
       public ${column.attrType} get${column.attrName}() {
          return ${column.attrname};
       }
    </#list>
}