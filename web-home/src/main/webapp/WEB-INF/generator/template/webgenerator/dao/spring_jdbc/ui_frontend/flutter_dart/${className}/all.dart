<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
<#assign classNameLower = className?uncap_first> 

export '${className}_app.dart';
export '${className}_dto.dart';
export '${className}_form.dart';
export '${className}_service.dart';
export '${className}_table.dart';

