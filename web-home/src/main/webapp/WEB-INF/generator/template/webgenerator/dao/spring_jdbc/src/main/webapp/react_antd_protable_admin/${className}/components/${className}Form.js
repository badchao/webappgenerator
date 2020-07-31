<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>

import { Component } from 'react';
import { Modal, Form, Input } from 'antd';

const FormItem = Form.Item;

class ${className}Form extends Component {
    formRef = React.createRef();
    defaultRecord = {}; //表单默认值

    constructor(props) {
        super(props);
    }

    componentDidMount() {
    }

    componentDidUpdate() {
    }

    componentWillUnmount() {
    }

    submit = () => {
      this.formRef.current.submit();
    }

    doFinish = (values) => {
      const {onFinish} = this.props;

      //可以继续增加自己的验证逻辑

      onFinish(values);
    }

    render() {
        //isEdit 是否编辑,用于控制新增/编辑的页面展现
        var {isEdit, onFinish,record} = this.props;
        record = isEdit ? record : Object.assign(this.defaultRecord,record);

        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 },
        };

        return (
          <Form ref={this.formRef} {...formItemLayout} layout="horizontal" onFinish={this.doFinish} initialValues={record} >
              <#list table.columns as column>
              <FormItem label="${column.columnAlias!}" name="${column.columnNameLower}" rules={[{ required: ${(!column.nullable)?string} }]} hidden={false} >
                  <Input />
              </FormItem>
              </#list>
          </Form>

        );
    }
}

export default ${className}Form;
