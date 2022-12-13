	/**
     * ${methodDescription.UPDATE_BY_PRIMARY_KEY.comment} <br>
    <#list methodDescription.UPDATE_BY_PRIMARY_KEY.params as param>
     * @param ${javaClassDefinition.POJO.className?uncap_first} ${param.comment}  <br>
    </#list>
     * @return 更新的数据条数
     */
    int ${methodDescription.UPDATE_BY_PRIMARY_KEY.methodName}(${javaClassDefinition.POJO.className} ${javaClassDefinition.POJO.className?uncap_first});

