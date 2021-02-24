<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

import ${className}Table from './components/${className}Table';
import { Component } from 'react';
import React from 'react';
import { connect } from 'dva';

function mapStateToProps(state) {
    //console.info("mapStateToProps() state",state);
    const model = state.${classNameLower};
    return {
        ...model,
        loading: state.loading.models.${classNameLower},
    };
}

const C${className}Table = connect(mapStateToProps)(${className}Table);

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
    
   /**
         * 页面相关事件处理函数--监听用户下拉动作,当前没有实现
     */
    onPullDownRefresh() {
      //this.execSearch(1);
    }
  
    /**
         * 页面上拉触底事件的处理函数,当前没有实现
     */
    onReachBottom() {
      //if (this.data.hasMoreData) {
      //  this.execSearch(this.data.page + 1);
      //}
    }
  
    render() {
      //URL 参数
      const query = this.props.location.query;
      
      return (
      <div>
        <C${className}Table locationQuery={...query} />
      </div>
      );
    }
  
}

export default ${className}Index;
