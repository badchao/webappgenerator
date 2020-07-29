<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

import ${className}List from './components/${className}List';
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
        <${className}List />
      </div>
      );
    }
  
}

export default ${className}Index;
