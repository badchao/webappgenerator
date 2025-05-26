<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
<#assign classNameLower = className?uncap_first> 

export '${className}App.dart';
export '${className}Dto.dart';
export '${className}Form.dart';
export '${className}Service.dart';
export '${className}TablePage.dart';

