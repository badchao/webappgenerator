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
  runApp(const ${className}App());
}

class ${className}App extends StatelessWidget {
  const ${className}App({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '数据表格管理',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        useMaterial3: true,
      ),
      home: const ${className}CrudTablePage(),
    );
  }
}