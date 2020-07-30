/*
 * Copyright [rapid-framework]
 * Web Site: https://github.com/badqiu/rapid-framework
 * Since 2005 - 2020
 * author: badqiu email:badqiu(a)gmail.com
 */

import { Component } from 'react';
import { Modal, Form, Input } from 'antd';

const FormItem = Form.Item;

class ${className}Form extends Component {
    formRef = React.createRef();
    defaultRecord = {enabled:true};

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
        const {isEdit, onFinish} = this.props; //是否编辑,用于控制新增/编辑的页面展现
        const record = isEdit ? this.props.record : this.defaultRecord;

        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 },
        };

        return (
          <Form ref={this.formRef} {...formItemLayout} layout="horizontal" onFinish={this.doFinish} initialValues={record} >
              <#list table.columns as column>
              <FormItem label="${column.columnAlias!}" name="${column.columnNameLower}" rules={[{ required: ${(!column.nullable)?string} }]} >
                  <Input />
              </FormItem>
              </#list>
          </Form>

        );
    }
}

export default ${className}Form;