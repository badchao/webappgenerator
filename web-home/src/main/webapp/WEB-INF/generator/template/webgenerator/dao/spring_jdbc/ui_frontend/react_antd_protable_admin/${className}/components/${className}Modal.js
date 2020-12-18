<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>

import { Component } from 'react';
import { Modal, Form, Input } from 'antd';
import ${className}Form from './${className}Form';
import React from 'react';

const FormItem = Form.Item;

class ${className}Modal extends Component {
    formRef = React.createRef();

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

    doShowModal = (e) => {
        if (e) e.stopPropagation();
        this.setState({
            visible: true,
        });
    };

    doHideModal = () => {
        this.setState({
            visible: false,
        });
    };

    doOk = () => {
        this.formRef.current.submit();
    };

    onFormFinish = (values) => {
      const onOk = this.props.onOk;
      var promise = onOk(values);
      if(promise) {
        promise.then(()=>{
          this.doHideModal();
        });
      }else {
        this.doHideModal();
      }
    };

    render() {
        const {isEdit, record, children, onOk, readonly} = this.props;
        
        const formItemLayout = {
            labelCol: { span: 4 },
            wrapperCol: { span: 20 },
        };

        return (
            <span>
                <span onClick={this.doShowModal}>
                    {children}
                </span>
                <Modal
                    width="85%"
                    destroyOnClose={true}
                    maskClosable={false}
                    title="${table.tableAlias!}"
                    visible={this.state.visible}
                    okText={readonly ? '关闭' : '保存'}
                    onOk={readonly ? this.doHideModal : this.doOk}
                    onCancel={this.doHideModal}
                >
                    <${className}Form onFinish={this.onFormFinish} ref={this.formRef} record={record} isEdit={isEdit} />
                </Modal>
            </span>
        );
    }
}

export default ${className}Modal;
