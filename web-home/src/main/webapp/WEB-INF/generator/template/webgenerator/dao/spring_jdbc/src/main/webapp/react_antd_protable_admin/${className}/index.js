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
      return (
      <div>
        <${className}Table />
      </div>
      );
    }
  
}

export default ${className}Index;
