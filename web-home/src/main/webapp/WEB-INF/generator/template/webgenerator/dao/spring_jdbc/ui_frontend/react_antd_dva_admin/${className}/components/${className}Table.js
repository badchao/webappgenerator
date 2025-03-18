<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>

import { connect } from 'dva';
import { history,Link } from 'umi';
import { Table, Pagination, Popconfirm, Button, Space,Form,Input } from 'antd';
import ProTable from '@ant-design/pro-table';
import { Component } from 'react';
import React from 'react';
import ${className}Modal from './${className}Modal';

import { EditOutlined,DeleteOutlined } from '@ant-design/icons';

const FormItem = Form.Item;

class ${className}Table extends Component {
    
    queryFormRef = React.createRef();
    
    constructor(props) {
        super(props);
    }

    componentDidMount() {
      this.doPageChange(1,this.props.locationQuery);
    }

    componentDidUpdate() {
    }

    componentWillUnmount() {
    }

    doRemove = (record) => {
        this.dispatch({
            type: '${classNameLower}/remove',
            payload: record,
        });
    }

    doPageChange = (current,query) => {
        this.dispatch({
            type: '${classNameLower}/query',
            payload: { current, ...query },
        });
    }

    doEdit = (record) => {
        return this.dispatch({
            type: '${classNameLower}/update',
            payload: record,
        });
    }


    doCreate = (record) => {
        return this.dispatch({
            type: '${classNameLower}/create',
            payload: { ...record, },
        });
    }

    doView = (record) => {
      console.info("doView record",record);
    }

    doQueryFormSubmit = (e) => {
      this.queryFormRef.current.submit();
    }

    doQueryFormFinish = (values) => {
      console.info("doQueryFormFinish()",values);
      this.doPageChange(1,values);
    }
    
    dispatch = (obj) => {
      return this.props.dispatch(obj);
    }

    render() {
      const { dataSource, loading, query, total ,projectId } = this.props;
      const { current,pageSize } = query;

      const columnOperationRender = (text, record) => {
        return (
        <span >
          <Space>
            <${className}Modal isEdit={true} record={record} onOk={this.doEdit}>
                <Button size="small" type="primary">修改</Button>
            </${className}Modal>
            <Popconfirm title="确认删除?" onConfirm={this.doRemove.bind(this,record)}>
                <Button size="small" type="primary" danger={true}>删除</Button>
            </Popconfirm>
           </Space>
        </span>)
      };
      
      // valueType: date,time,dateTime,dateRange,  percent(百分比),money,progress(进度条)    具体查看: https://procomponents.ant.design/table/value-type#valueenum
      const columns = [
          <#list table.columns as column>
          { title: '${column.columnAlias!}', dataIndex: '${column.columnNameLower}', valueType: 'text', hideInTable: ${column.pk?string}, },
          </#list>
          {
              title: '操作',
              render: columnOperationRender,
          },
      ];

      return (
          <div >
              <div>
                  <div >
                      <Form ref={this.queryFormRef} layout="inline" initialValues={query} onFinish={this.doQueryFormFinish}  >
                        <FormItem>
                          <${className}Modal  onOk={this.doCreate}>
                            <Button type="primary">新增</Button>
                          </${className}Modal>
                        </FormItem>
                        <FormItem name="keyword"  >
                            <Input placeholder="搜索关键字" type="search" onPressEnter={this.doQueryFormSubmit} />
                        </FormItem>
                      </Form>
                  </div>
                  
                  <ProTable
                      loading={loading}
                      columns={columns}
                      dataSource={dataSource}
                      rowKey={record => record.${table.pkColumn.columnNameLower}}
                      pagination={false}
                      search={false}
                  />
                  <Pagination
                      className="ant-table-pagination"
                      total={total}
                      current={current}
                      pageSize={pageSize}
                      onChange={this.doPageChange}
                  />
              </div>
          </div>
      );
    }
}

export default ${className}Table;
