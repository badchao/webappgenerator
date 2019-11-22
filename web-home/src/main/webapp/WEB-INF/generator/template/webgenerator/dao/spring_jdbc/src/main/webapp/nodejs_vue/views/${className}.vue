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

        <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[30, 50, 80, 100]" 
              :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total" class="pagination-container">
        </el-pagination>

        <!--弹出层-->
        <el-dialog title="增加" :visible.sync="dialogFormVisible">
          <el-form ref="form" :model="form" :rules="rules">
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
        <!--弹出层 end-->


   </div>
</template>

<script>
    import { ${className}Client,CheckRules } from '@/api/${classNameLowerCase}';
    
    export default {
      data() {
        return {
          rules: CheckRules,
          projectId: this.$route.params.projectId,
          
          loading: true,
          listQuery: {},
          tableData: [],
          dialogFormVisible: false,
          
          form: {
            <#list table.columns as column>
            ${column.columnNameLower}: null,
            </#list>
          },
          
          currentPage: 1,
          total: 0,
          pageSize: 30,
          formLabelWidth: '30%'
        }
      },
      
      created() {
        this.getTableData(1, 30)
        setTimeout(() => {
          this.loading = false
        }, 200)
      },
      
      watch: {
        '$route.params.projectId': function(val, oldVal) {
          if (val) {
            this.projectId = val
            this.getTableData(1, 30)
          }
        }
      },
      
      methods: {
        getTableData(current, size) {
          this.listLoading = true
          this.listQuery.current = current
          this.listQuery.size = size
          this.findPage(this.listQuery)
        },
        
        findPage(listQuery) {
          ${className}Client.findPage(listQuery).then(response => {
            this.tableData = response.records
            this.total = response.total
            this.pageSize = response.size
            this.currentPage = response.current
          })
        },
        
        save(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
               ${className}Client.save(this.form).then(response => {
                  this.getTableData(1, 30)
                  this.dialogFormVisible = false
              })
            } else {
              console.log('error submit!!')
              return false
            }
          })
        },
        
        removeById(data) {
          ${className}Client.removeById(data).then(response => {
            if (response) {
              this.getTableData(1, 30)
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            }
          })
        },
        
        handleAdd() {
          this.form = {
            projectId: this.projectId,
            siteDatasourceId: '',
            host: '',
            port: '',
            db: '',
            dbType: '',
            userName: '',
            password: '',
            remarks: ''
          };
          
          this.dialogFormVisible = true
        },
        
        handleEdit(index, row) {
          this.form = this.tableData[index];
          this.dialogFormVisible = true;
        },
        
        handleDelete(index, row) {
          this.$confirm('确认删除?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            var row = this.tableData[index];
            this.removeById(row);
          }))
        },
        
        handleCancel() {
          this.dialogFormVisible = false
        },
        
        handleSizeChange(pageSize) {
          this.getTableData(this.currentPage, pageSize)
        },
        
        handleCurrentChange(currentPage) {
          this.getTableData(currentPage, this.pageSize)
        }
      }
    }
</script>


<style rel="stylesheet/scss" lang="scss" scoped>
</style>
