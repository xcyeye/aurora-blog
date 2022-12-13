package ${javaClassDefinition.MAPPER.fullPackage};

import org.apache.ibatis.annotations.Mapper;
import ${javaClassDefinition.PO.fullPackage}.${javaClassDefinition.PO.className};
import xyz.xcye.service.base.BaseDao;

/**
 * @table ${tableInfo.tableName} <br/>
 * @description ${tableInfo.tableName} 数据表DAO层 <br/>
 * @date ${classCommentInfo.createTime} <br/>
 * @author ${classCommentInfo.author} <br/>
 */

@Mapper
public interface ${javaClassDefinition.MAPPER.className} extends BaseDao<${javaClassDefinition.PO.className}> {

}