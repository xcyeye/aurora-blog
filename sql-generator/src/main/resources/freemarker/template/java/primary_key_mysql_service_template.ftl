package ${javaClassDefinition.SERVICE.fullPackage};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${javaClassDefinition.PO.fullPackage}.${javaClassDefinition.PO.className};
import ${javaClassDefinition.MAPPER.fullPackage}.${javaClassDefinition.MAPPER.className};
import xyz.xcye.service.base.BaseService;

/**
 * @table ${tableInfo.tableName} <br/>
 * @description ${tableInfo.tableName} 数据表Service层 <br/>
 * @date ${classCommentInfo.createTime} <br/>
 * @author ${classCommentInfo.author} <br/>
 */

@Service
public class ${javaClassDefinition.SERVICE.className} extends BaseService<${javaClassDefinition.PO.className}> {
	@SuppressWarnings("unused")
	private ${javaClassDefinition.MAPPER.className} ${javaClassDefinition.MAPPER.classNameCamelCase}Dao;
	
	@Autowired
    public void setInfoDao(${javaClassDefinition.MAPPER.className} ${javaClassDefinition.MAPPER.classNameCamelCase}Dao) {
        super.setBaseDao(${javaClassDefinition.MAPPER.classNameCamelCase}Dao);
        this.${javaClassDefinition.MAPPER.classNameCamelCase}Dao = ${javaClassDefinition.MAPPER.classNameCamelCase}Dao;
    }
}