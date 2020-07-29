<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

import { Component } from 'react';
import { Modal, Form, Input } from 'antd';

const FormItem = Form.Item;

class ${className}Modal extends Component {
    formRef = React.createRef();
    defaultRecord = {};
    
    constructor(props) {
        super(props);
        this.state = {
            visible: false,
        };
    }
    
    componentDidMount() {
    }
    
    componentDidUpdate() {
    }
    
    componentWillUnmount() {
    }

    showModelHandler = (e) => {
        if (e) e.stopPropagation();
        this.setState({
            visible: true,
        });
    };

    hideModelHandler = () => {
        this.setState({
            visible: false,
        });
    };

    okHandler = () => {
        const { onOk } = this.props;
        this.formRef.current.validateFields().then((values) => {
          onOk(values);
          this.hideModelHandler();
        });
    };

    render() {
        const { children } = this.props;
        const isEdit = this.props.isEdit; //是否编辑,用于控制新增/编辑的页面展现
        const record = isEdit ? this.props.record : this.defaultRecord;
        const onOk = this.props.onOk;
        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 },
        };

        return (
            <span>
                <span onClick={this.showModelHandler}>
                    {children}
                </span>
                <Modal
                    destroyOnClose
                    title="用户"
                    visible={this.state.visible}
                    onOk={this.okHandler}
                    onCancel={this.hideModelHandler}
                >
                    <Form ref={this.formRef} {...formItemLayout} layout="horizontal" initialValues={record} >
                        <#list table.columns as column>
                        <FormItem label="${column.columnAlias!}" name="${column.columnNameLower}"
                            rules={[{ required: ${(!column.nullable)?string} }]} >
                            <Input />
                        </FormItem>
                        </#list>
                        
                    </Form>
                </Modal>
            </span>
        );
    }
}

export default ${className}Modal;