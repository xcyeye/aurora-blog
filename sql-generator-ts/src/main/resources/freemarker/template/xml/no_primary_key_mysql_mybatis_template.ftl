<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mybatisXmlDefinition.nameSpace}">

<#include "common/result_map_xml.ftl"/>

<#include "common/common_sql_xml.ftl"/>
<#if methodDescription.INSERT.enabled>

<#include "common/insert_xml.ftl"/>
</#if>
<#if methodDescription.DELETE_BY_WHERE.enabled>

<#include "common/delete_by_where_xml.ftl"/>
</#if>
<#if methodDescription.QUERY_BY_WHERE.enabled>

<#include "common/query_by_where_xml.ftl"/>
</#if>
<#if methodDescription.COUNT_BY_WHERE.enabled>

<#include "common/count_by_where_xml.ftl"/>
</#if>
</mapper>