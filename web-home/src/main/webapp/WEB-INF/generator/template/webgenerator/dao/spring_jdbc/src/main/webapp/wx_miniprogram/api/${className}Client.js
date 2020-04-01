<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   
<#assign dollor = '$'>   

var jsPath = '..';
var appWs = require(jsPath + '/utils/app_ws.js');
var util = require(jsPath + '/utils/util.js');

var ${className}Client = {

  //增加数据处理或转换，再传输给后端
  convertSaveData : function(row) {
    return row;
  },

  //为数据增加计算属性，用于前端展示。 应用场景: json string属性  =>  json 对象,  tags string => tags Array
  convertShowData: function (that) {
	  if(!that) return that;
	  
	  var extendComputedProps = {
		  get demoProperty() { //示例,增加 一个计算列 
			  return that.toString()+", hello world";
		  }
	  }
	  
	  return Object.assign(that,extendComputedProps);
  },


  
  create:function(data,success,fail) {
    data = this.convertSaveData(data);
    appWs.wsRequest("${className}WebService/create", data, success, fail);
  },

  update:function(data,success,fail) {
    data = this.convertSaveData(data);
    appWs.wsRequest("${className}WebService/update", data, success, fail);
  },

  removeById:function(data,success,fail){
    appWs.wsRequest("${className}WebService/removeById", data, success,fail);
  },

  getById: function (data, success, fail) {
    var that = this;
    appWs.wsRequest("${className}WebService/getById", data, function(res) {
      //可以增加数据处理，再返回给展示层
      res.data.result = that.convertShowData(res.data.result);
      success(res);
    }, fail);
  },

  search:function(data,success,fail) {
    var that = this;
    appWs.wsRequest("${className}WebService/search", data, function (res) {
    	//可以增加数据处理，再返回给展示层
    	res.data.result = res.data.result.map(that.convertShowData);
    	success(res);
    }, fail);
  }

}



module.exports = {
  ${className}Client: ${className}Client,
}

