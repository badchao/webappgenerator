
<#include "/macro.include"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>  

 


columns
===
```sql

    <@generateSqlSelectColumns table.columns/>  

```




commonWhere
===
```sql
	-- 条件未走索引容易有性能问题，按需打开所需要的条件
			
	/*
	<#list table.columns as column>
	<#if column.isDateTimeColumn>
	@if(isNotEmpty(query.${column.columnNameLower}Begin)){
		AND main.${column.sqlName}=#query.${column.columnNameLower}Begin#
	@}
	@if(isNotEmpty(query.${column.columnNameLower}End)){
		AND main.${column.sqlName}=#query.${column.columnNameLower}End#
	@}
	
	<#else>
	@if(isNotEmpty(query.${column.columnNameLower})){
		AND main.${column.sqlName}=#query.${column.columnNameLower}#
	@}
	
	</#if>
	</#list>
	*/

```



queryPage
===
```sql
    select
        @pageTag(){
            #use("columns")#
        @}
    FROM ${table.sqlName} main
    WHERE 1=1
    
        #use("commonWhere")#
        
    @pageIgnoreTag(){
        ORDER BY main.create_date desc
    @}
```