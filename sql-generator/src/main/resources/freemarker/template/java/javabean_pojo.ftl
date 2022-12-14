package ${javaClassDefinition.POJO.fullPackage};

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
<#list tableInfo.importPackages as import>
    <#if import??>
import ${import};
    </#if>
</#list>

/**
 * @description ${tableInfo.tableName}数据表的POJO <br/>
 * @date ${classCommentInfo.createTime} <br/>
 * @author ${classCommentInfo.author} <br/>
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ${javaClassDefinition.POJO.className} {

<#list tableInfo.columnLists as colm>
  <#if colm??>
    /**
     * ${colm.columnComment}
     */
    @Schema(title = "${colm.columnComment}")
    private ${colm.javaDataType} ${colm.javaColumnName};
  </#if>

</#list>
}