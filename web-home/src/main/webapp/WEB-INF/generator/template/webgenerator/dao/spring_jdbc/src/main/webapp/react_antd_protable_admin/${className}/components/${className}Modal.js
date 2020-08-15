<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>

import { Component } from 'react';
import { Modal, Form, Input } from 'antd';
import ${className}Form from './${className}Form';

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
        const {isEdit, record, children, onOk} = this.props;
        const isCreate = !isEdit;
        
        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 },
        };

        return (
            <span>
                <span onClick={this.doShowModal}>
                    {children}
                </span>
                <Modal
                    destroyOnClose={true}
                    maskClosable={false}
                    title="${table.tableAlias!}"
                    visible={this.state.visible}
                    onOk={this.doOk}
                    onCancel={this.doHideModal}
                >
                    <${className}Form onFinish={this.onFormFinish} ref={this.formRef} record={record} isEdit={isEdit} />
                </Modal>
            </span>
        );
    }
}

export default ${className}Modal;
