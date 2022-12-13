package ${javaClassDefinition.POJO.fullPackage};

import lombok.Data;
import lombok.EqualsAndHashCode;
import ${javaClassDefinition.PO.fullPackage}.${javaClassDefinition.PO.className};

/**
 * @description ${tableInfo.tableName}数据表的POJO <br/>
 * @date ${classCommentInfo.createTime} <br/>
 * @author ${classCommentInfo.author} <br/>
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ${javaClassDefinition.POJO.className} extends ${javaClassDefinition.PO.className} {

}