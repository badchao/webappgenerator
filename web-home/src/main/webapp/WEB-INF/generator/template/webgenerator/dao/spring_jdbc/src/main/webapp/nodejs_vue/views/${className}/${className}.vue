<#include "/macro.include"/>
<#include "/custom.include"/>  
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>

<!--
	${table.className} CRUD
	author: ${author}
	created: <#if now??>${now?string('yyyy-MM-dd')}</#if>
 -->
<template>
	<div>
		<div class="filter-container">
			<el-button type="primary" icon="el-icon-plus" @click="handleAdd">增加</el-button>
		</div>
        
        <!-- table -->
        <el-table :data="tableData" style="width: 100%" stripe>
        	<#list table.columns as column>
          <el-table-column prop="${column.columnNameLower}" label="${column.columnAlias}"></el-table-column>
          	</#list>
          
          <el-table-column prop="co" label="操作" align="right" width="180">
              <template slot-scope="scope">
                  <el-button size="mini" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                  <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
              </template>
          </el-table-column>
        </el-table>

        <el-pagination background @size-change="handleChangePageSize" @current-change="handleChangePage" :current-page="page" :page-sizes="[30, 50, 80, 100]" 
              :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total" class="pagination-container">
        </el-pagination>

        <!-- 编辑,增加 Dialog -->
        <el-dialog title="{{edit ? '编辑' : '增加'}}" :visible.sync="editFormVisible">
          <el-form ref="form" :model="form" :rules="checkRules">
          	<#list table.columns as column>
            <el-form-item label="${column.columnAlias}" :label-width="formLabelWidth" prop="${column.columnNameLower}">
              <el-input v-model="form.${column.columnNameLower}" auto-complete="off" class="form-width"></el-input>
            </el-form-item>
            
            </#list>
          </el-form>
          
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="save('form')">确 定</el-button>
            <el-button @click="handleCancel">取 消</el-button>
          </div>
        </el-dialog>
        <!-- 编辑,增加 Dialog end -->


   </div>
</template>

<script>
    import { ${className}Client,CheckRules } from '@/api/${className}';
    
    export default {
      data() {
        return {
          checkRules: CheckRules,
          //projectId: this.$route.params.projectId,
          
          listQuery: {},
          tableData: [],
          
          defaultForm: {
            <#list table.columns as column>
            ${column.columnNameLower}: null,
            </#list>
          },
          form: {},
          editFormVisible: false,
          
          page: 1,
          total: 0,
          pageSize: 30,
          formLabelWidth: '30%',
          edit: false,
        }
      },
      
      created() {
        this.getTableData(1, this.pageSize);
      },
      
      watch: {
        '$route.params.someId': function(val, oldVal) {
        }
      },
      
      methods: {
        getTableData(page, pageSize) {
          this.listQuery.page = page
          this.listQuery.pageSize = pageSize
          this.findPage(this.listQuery)
        },
        
        findPage(listQuery) {
          ${className}Client.findPage(listQuery).then(response => {
            this.tableData = response.itemList;
            var paginator = response.paginator;
            this.total = paginator.totalItems;
            this.page = response.page;
          })
        },
        
        save(formName) {
          this.$refs[formName].validate((valid) => {
            if (!valid) {
                console.log('form check invalid!!');
            	return false;
            }
            
			if(this.edit) {
               ${className}Client.update(this.form).then(response => {
               	  this.editFormVisible = false
                  this.getTableData(1, this.pageSize)
               })
            }else {
               	${className}Client.create(this.form).then(response => {
               	  this.editFormVisible = false
                  this.getTableData(1, this.pageSize)
                  
                })
            }
	        return true;
          })
        },
        
        removeById(data) {
          ${className}Client.removeById(data).then(response => {
            if (response) {
              this.getTableData(1, this.pageSize)
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            }
          })
        },
        
        handleAdd() {
          this.form = Object.assign({},this.defaultForm); //clone object
          this.edit = false,
          this.editFormVisible = true
        },
        
        handleEdit(index, row) {
          this.form = Object.assign({},this.tableData[index]); //clone object
          this.edit = true,
          this.editFormVisible = true;
        },
        
        handleDelete(index, row) {
          this.$confirm('确认删除?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            var row = this.tableData[index];
            this.removeById(row);
          })
        },
        
        handleCancel() {
          this.editFormVisible = false
        },
        
        handleChangePageSize(pageSize) {
          this.getTableData(this.page, pageSize)
        },
        
        handleChangePage(page) {
          this.getTableData(page, this.pageSize)
        }
      }
    }
</script>


<style rel="stylesheet/scss" lang="scss" scoped>
</style>
