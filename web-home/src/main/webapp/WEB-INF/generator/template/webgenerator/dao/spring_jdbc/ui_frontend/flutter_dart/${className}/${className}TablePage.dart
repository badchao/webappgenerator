<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
<#assign classNameLower = className?uncap_first> 
<#assign classNameDtoClass = className+"Dto"> 


import 'common_import.dart';
import 'package:intl/intl.dart';


@RoutePage()
class ${className}TablePage extends StatefulWidget {
  const ${className}TablePage({super.key});

  @override
  _${className}TablePageState createState() => _${className}TablePageState();
}


class _${className}TablePageState extends State<${className}TablePage> {
  List<${classNameDtoClass}> _dataList = [];
  int _currentPage = 1;
  int _pageSize = 10;
  bool _isLoading = false;
  int _totalRecords = 0;
  
  
  final TextEditingController _searchController = TextEditingController();
  final ScrollController _horizontalController = ScrollController();
  Timer? _searchDebounce;

  int? _sortColumnIndex; 
  bool _sortAscending = true; // 排序方向（true=升序）
  String? _sortColumn;
  
  List<TableColumn<${classNameDtoClass}>> tableColumns = [];
  
  final DateFormat DATE_FORMAT = DateFormat('yyyy-MM-dd');

  @override
  void dispose() {
    _horizontalController.dispose();
    super.dispose();
  }
  
  @override
  void initState() {
    super.initState();
    _initTableColumns();
    _loadData();
  }

  void _initTableColumns() {
    tableColumns = [
	<#list table.columns as column>
      TableColumn(
        label: '${column.columnAlias!}',
        <#if column.isDateTimeColumn>
        dataCellBuilder: (item) => Text(DATE_FORMAT.format(item.${column.columnNameLower})),
        <#else>
        dataCellBuilder: (item) => Text(item.${column.columnNameLower}.toString()),
        </#if>   
        sortable: true,     
        columnName: '${column.sqlName}',
      ),
    </#list>
    ];
  }
  
  Future<void> _loadData() async {
    if (_isLoading) return;
    setState(() => _isLoading = true);
    
    try {
	  String? sortColumns = _sortColumn == null ? null : _sortColumn! + (_sortAscending ? " asc" : " desc");
      
	  ${className}PageRequest query = ${className}PageRequest();
      final result = await ${className}Service.query(_currentPage, _pageSize,sortColumns, keyword: _searchController.text);
      setState(() {
        _dataList = result.data;
        _totalRecords = result.total;
      });
    } finally {
      setState(() => _isLoading = false);
    }
  }

  void _handleSearch(String value) {
    _searchDebounce?.cancel();
    _searchDebounce = Timer(const Duration(milliseconds: 500), () {
      setState(() => _currentPage = 1);
      _loadData();
    });
  }

  void _handleSortTable(int columnIndex, bool ascending,String? sortColumn) {
      setState(() {
        _sortColumnIndex = columnIndex;
        _sortAscending = ascending;
        _sortColumn = sortColumn;
        _currentPage = 1;
        print('_handleSortTable() by sortColumn:$sortColumn in ascending:$ascending order');
        
        _loadData();
      });
  }
  
  void _handleFormSubmit(${classNameDtoClass} newItem, bool isCreate) async {
    if(isCreate) {
      await ${className}Service.create(newItem);
    }else {
      await ${className}Service.update(newItem);
    }
    Navigator.pop(context); //close form dialog
    _loadData();
  }

  List<Widget> _buildTableHandleActions(${classNameDtoClass} item) {
    return [

      //edit action
      IconButton(
        icon: const Icon(Icons.edit, size: 20),
        onPressed: () => showDialog(
          context: context,
          builder: (context) => AlertDialog(
            title: const Text('编辑数据'),
            content: ${className}Form(
              initData: item,
              onSubmit: (newItem) => _handleFormSubmit(newItem, false),
            ),
          ),
        ),
      ),

      // delete action
      IconButton(
        icon: const Icon(Icons.delete, color: Colors.red, size: 20),
        onPressed: () {
          showDeleteConfirmDialog(context, () async {
            await ${className}Service.remove(item);
            _loadData();
          });
        },
      ),

      // deploy action
      Padding(
        padding: const EdgeInsets.symmetric(horizontal: 4.0),
        child: TextButton(
          style: TextButton.styleFrom(
            backgroundColor: Colors.blue[50],
            padding: const EdgeInsets.symmetric(horizontal: 12),
          ),
          onPressed: () async {
            await ${className}Service.deploy(item);
            ScaffoldMessenger.of(context)
                .showSnackBar(const SnackBar(content: Text('部署操作已触发')));
          },
          child: const Text('部署'),
        ),
      ),
    ];
  }

  
  List<DataColumn> _buildTableHeaderColumns() {
    return [
      ...tableColumns.map((column) => 
        DataColumn(
          label: Text(column.label),
          onSort: column.sortable
              ? (columnIndex, ascending) => 
                  _handleSortTable(columnIndex, ascending, column.columnName)
              : null,
        )
      ).toList(),
      
      const DataColumn(label: Text('操作')),
    ];
  }

  List<DataRow> _buildTableBodyRows() {
    return _dataList.map((item) => DataRow(
      cells: [
        ...tableColumns.map((column) => 
          DataCell(column.dataCellBuilder(item))
        ).toList(),
        
        DataCell(Row(children: _buildTableHandleActions(item))),
      ],
    )).toList();
  }  

  Widget _buildDataTable() {
    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      child: DataTable(
        sortColumnIndex: _sortColumnIndex,
        sortAscending: _sortAscending,		
        columns: _buildTableHeaderColumns(),
        rows: _buildTableBodyRows(),
      ),
    );
  }
  
  Widget _buildDataTable() {
    return Scrollbar(
      controller: _horizontalController,
      thumbVisibility: true,
      trackVisibility: true,
      child: SingleChildScrollView(
        controller: _horizontalController,
        scrollDirection: Axis.horizontal,
        child: ConstrainedBox(
	            constraints: BoxConstraints(
	              minWidth: MediaQuery.of(context).size.width, // 关键！取屏幕宽
	            ),
	            child: DataTable(
	              sortColumnIndex: _sortColumnIndex,
	              sortAscending: _sortAscending,
	              columns: _buildTableHeaderColumns(),
	              rows: _buildTableBodyRows(),
	            ) // 原有表格
            ),
      ),
    );
  }  

  Widget _buildAppBarSearchTitle() {
    return Row(
      children: [
        const Text('数据管理'),
        const SizedBox(width: 20),
        Expanded(
          child: TextField(
            controller: _searchController,
            decoration: const InputDecoration(
              prefixIcon: Icon(Icons.search),
              hintText: '搜索...',
              border: OutlineInputBorder(),
            ),
            onChanged: _handleSearch,
          ),
        ),
      ],
    );
  }

  AppBar _buildAppBar() {
    return AppBar(
        title: _buildAppBarSearchTitle(),
        actions: [
          IconButton(
            icon: const Icon(Icons.add),
            onPressed: () => showDialog(
              context: context,
              builder: (context) => AlertDialog(
                title: const Text('新建数据'),
                content: ${className}Form(
                  onSubmit: (newItem) => _handleFormSubmit(newItem, true),
                ),
              ),
            ),
          ),
        ],
      );
  }

  Widget _buildBody() {
    return Column(
        children: [
          Expanded(
            child: _isLoading 
                ? const Center(child: CircularProgressIndicator())
                : _dataList.isEmpty
                    ? const Center(child: Text('暂无数据'))
                    : _buildDataTable(),
          ),

          if (_totalRecords > 0)
            PaginationWidget(
              currentPage: _currentPage,
              totalRecords: _totalRecords,
              pageSize: _pageSize,
              onPageChanged: (newPage) {
                _currentPage = newPage;
                _loadData();
              },
            ),
        ],
      );
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _buildAppBar(),
      body: _buildBody(),
    );
  }

}












