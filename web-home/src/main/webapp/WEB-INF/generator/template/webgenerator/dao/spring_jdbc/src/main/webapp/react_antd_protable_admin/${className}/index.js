<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

import ${className}Table from './components/${className}Table';
import { Component } from 'react';

class ${className}Index extends Component {
  
    constructor(props) {
        super(props);
    }
    
    componentDidMount() {
    }
    
    componentDidUpdate() {
    }
    
    componentWillUnmount() {
    }
    
    render() {
      //URL 参数
      const query = this.props.location.query;
      
      return (
      <div>
        <${className}Table {...query} />
      </div>
      );
    }
  
}

export default ${className}Index;
