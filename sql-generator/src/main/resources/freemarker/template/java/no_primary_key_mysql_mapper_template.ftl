package ${javaClassDefinition.MAPPER.fullPackage};

import java.util.List;

import ${javaClassDefinition.POJO.fullPackage}.${javaClassDefinition.POJO.className};

/**
 * description : ${tableInfo.tableName} table dao layer interface <br/>
 * time:    ${classCommentInfo.createTime} <br/>
 * @author  ${classCommentInfo.author} <br/>
 * @since   ${classCommentInfo.sinceVersion} <br/>
 * @version ${classCommentInfo.version} <br/>
 */
public interface ${javaClassDefinition.MAPPER.className} {
<#if methodDescription.INSERT.enabled>

	<#include "common/insert.ftl"/>
</#if>
<#if methodDescription.DELETE_BY_WHERE.enabled>

	<#include "common/deleteByWhere.ftl"/>
</#if>
<#if methodDescription.QUERY_BY_WHERE.enabled>

	<#include "common/queryByWhere.ftl"/>
</#if>
<#if methodDescription.COUNT_BY_WHERE.enabled>

	<#include "common/countByWhere.ftl"/>
</#if>
}