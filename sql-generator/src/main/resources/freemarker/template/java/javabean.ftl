package ${javaClassDefinition.PO.fullPackage};

<#list tableInfo.importPackages as import>
	<#if import??>
import ${import};
	</#if>
</#list>
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @table ${tableInfo.tableName} <br/>
 * @description ${tableInfo.tableComment} <br/>
 * @date ${classCommentInfo.createTime} <br/>
 * @author ${classCommentInfo.author} <br/>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "${tableInfo.tableName}数据表的实体类")
public class ${javaClassDefinition.PO.className} implements Serializable {

<#list tableInfo.columnLists as colm>
	<#if colm??>
	/**
	 * ${colm.columnComment}
	 */
	@Schema(title = "${colm.columnComment}")
	private ${colm.javaDataType} ${colm.javaColumnName};
	</#if>

</#list>
<#--<#list tableInfo.columnLists as colm>-->
<#--	<#if colm??>-->
<#--	public ${colm.javaDataType} get${colm.javaColumnName?cap_first}() {-->
<#--		return ${colm.javaColumnName};-->
<#--	}-->

<#--	public void set${colm.javaColumnName?cap_first}(${colm.javaDataType} ${colm.javaColumnName}) {-->
<#--		this.${colm.javaColumnName} = ${colm.javaColumnName};-->
<#--	}-->
<#--	-->
<#--	</#if>-->
<#--</#list>-->
}