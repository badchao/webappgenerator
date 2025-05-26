<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
<#assign classNameLower = className?uncap_first>
<#assign classNameDtoClass = className+"Dto"> 

import 'common_import.dart';

void main() {
  runApp(buildTestApp(homePage: const ${className}TablePage()));
}

