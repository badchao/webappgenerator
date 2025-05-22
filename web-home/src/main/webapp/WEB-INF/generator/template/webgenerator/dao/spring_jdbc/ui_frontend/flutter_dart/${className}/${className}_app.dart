<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
<#assign classNameLower = className?uncap_first> 

import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'dart:async';
import 'common_import.dart';
import "all.dart";

void main() {
  runApp(buildTestApp(homePage: const ${className}CrudTablePage()));
}

