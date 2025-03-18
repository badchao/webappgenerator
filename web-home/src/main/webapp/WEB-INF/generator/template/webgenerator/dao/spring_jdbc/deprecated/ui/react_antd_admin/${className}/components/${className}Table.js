<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

import { connect } from 'dva';
import { Table, Pagination, Popconfirm, Button, Space } from 'antd';
import ProTable from '@ant-design/pro-table';
import { Component } from 'react';
import ${className}Modal from './${className}Modal';

class ${className}Table extends Component {
    
    constructor(props) {
        super(props);
    }
    
    componentDidMount() {
      this.doPageChange(1);
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

    doPageChange = (current) => {
        this.dispatch({
            type: '${classNameLower}/query',
            payload: { current },
        });
    }

    doEdit = (record) => {
        this.dispatch({
            type: '${classNameLower}/update',
            payload: record,
        });
    }


    doCreate = (record) => {
        this.dispatch({
            type: '${classNameLower}/create',
            payload: record,
        });
    }
    
    doView = (record) => {
      console.info("doView record",record);
    }
    
    dispatch = (obj) => {
      this.props.dispatch(obj);
    }
  
    render() {
      const { dataSource, loading, total, current,pageSize } = this.props;
      
      
      //valueType: date,dateTime,time,money
      const columns = [
        <#list table.columns as column>
          { title: '${column.columnAlias!}', dataIndex: '${column.columnNameLower}', valueType: 'text'},
        </#list>
          {
              title: '操作',
              render: (text, record) => (
                  <span >
                    <Space>
                      <${className}Modal isEdit={true} record={record} onOk={this.doEdit}>
                          <a>修改</a>
                      </${className}Modal>
                      <Popconfirm title="确认删除?" onConfirm={this.doRemove.bind(this,record)}>
                          <a href="">删除</a>
                      </Popconfirm>
                     </Space>
                  </span>
              ),
          },
      ];
    
      return (
          <div >
              <div>
                  <div >
                      <${className}Modal  onOk={this.doCreate}>
                          <Button type="primary">新增</Button>
                      </${className}Modal>
                  </div>
                  <ProTable
                      loading={loading}
                      columns={columns}
                      dataSource={dataSource}
                      rowKey={record => record.${table.pkColumn.columnNameLower}}
                      pagination={false}
                      search={true} 
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

function mapStateToProps(state) {
    console.info("mapStateToProps() state",state);
    const model = state.${classNameLower};
    return {
        ...model,
        loading: state.loading.models.${classNameLower},
    };
}

export default connect(mapStateToProps)(${className}Table);