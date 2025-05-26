<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
<#assign classNameLower = className?uncap_first> 
<#assign classNameDtoClass = className+"Dto"> 

import 'common_import.dart';



class ${className}Form extends StatefulWidget {
  final ${classNameDtoClass}? initData;
  final Function(${classNameDtoClass}) onSubmit;

  const ${className}Form({
    super.key,
    this.initData,
    required this.onSubmit,
  });

  @override
  _${className}FormState createState() => _${className}FormState();
}

class _${className}FormState extends State<${className}Form> {
  final _formKey = GlobalKey<FormState>();
  
  <#list table.columns as column>
  late final TextEditingController ${column.columnNameLower}Controller = TextEditingController(text: widget.initData?.${column.columnNameLower}.toString() ?? '');
  </#list>
  
  ${classNameDtoClass} _buildDataFromForm() {
    return ${classNameDtoClass}(
	  <#list table.columns as column>
		<#if column.isStringColumn>
		${column.columnNameLower}: ${column.columnNameLower}Controller.text,
		<#else>
		${column.columnNameLower}: <@dartType column/>.parse(${column.columnNameLower}Controller.text),
		</#if>      
      </#list>
    );
  }

  Widget _buildFormActionButtons() {
    return Row(mainAxisAlignment: MainAxisAlignment.end, children: [
      // 关闭按钮
      TextButton(
        onPressed: () => Navigator.pop(context),
        style: TextButton.styleFrom(
          foregroundColor: Colors.grey[700],
        ),
        child: const Text('关闭'),
      ),
      const SizedBox(width: 12),

      // 保存按钮
      ElevatedButton(
        onPressed: () {
          if (_formKey.currentState!.validate()) {
            widget.onSubmit(_buildDataFromForm());
            // Navigator.pop(context); // 保存后自动关闭
          }
        },
        child: const Text('保存'),
      ),
    ]);
  }

  List<Widget> _buildFormFields() {
    return [
	<#list table.columns as column>
      TextFormField(
        controller: ${column.columnNameLower}Controller,
        decoration: const InputDecoration(labelText: '${column.columnAlias!}'),
        <#if !column.nullable>
        validator: (value) => value?.isEmpty ?? true ? '请输入${column.columnAlias!}' : null,
        </#if>
      ),		
	</#list>

      
    ];
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: SingleChildScrollView(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            
            ..._buildFormFields(),

            const SizedBox(height: 20),
            _buildFormActionButtons(),
            
          ],
        ),
      ),
    );
  }
}


