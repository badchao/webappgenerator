<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
<#assign classNameLower = className?uncap_first> 
<#assign classNameDtoClass = className+"Dto"> 

import 'common_import.dart';


class ${className}Service {
  static final List<${classNameDtoClass}> _mockData = List.generate(105, (index) => ${classNameDtoClass}.newMockData(index));


  static Future<QueryResult<${classNameDtoClass}>> query(int page, int pageSize, {String keyword = ''}) async {
    
    var filteredList = _mockData.where((item) {
      if (keyword.isEmpty) return true;
      
      return true;
      //return item.someField1.contains(keyword) || (item.someField1?.contains(keyword) ?? false);
    }).toList();

    final startIndex = (page - 1) * pageSize;
    final endIndex = startIndex + pageSize;
    final pageSubList = filteredList.sublist(
      startIndex.clamp(0, filteredList.length), 
      endIndex.clamp(0, filteredList.length)
    );

    return QueryResult<${classNameDtoClass}>(data: pageSubList, total: filteredList.length);
  }

  static Future<void> create(${classNameDtoClass} item) async {
    _mockData.insert(0, item);
  }

  static Future<void> update(${classNameDtoClass} item) async {
    final index = _mockData.indexWhere((e) => e.id == item.id);
    if (index != -1) _mockData[index] = item;
  }

  static Future<void> remove(int id) async {
    _mockData.removeWhere((e) => e.id == id);
  }

  static Future<void> deploy(int id) async => await Future.delayed(const Duration(milliseconds: 500));
  static Future<void> run(int id) async => await Future.delayed(const Duration(milliseconds: 500));
}


