<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   
<#assign dollor = '$'>   

var jsPath = '../..';
var appWs = require(jsPath + '/utils/app_ws.js');
var util = require(jsPath + '/utils/util.js');

var ${className}Client = {

  //增加数据处理或转换，再传输给后端
  convertSaveData : function(data) {
    return data;
  },

  convertShowData: function (row) {
    return row;
  },


  
  create:function(data,success,fail) {
    var data = this.convertSaveData(data);
    appWs.wsRequest("${className}Client/create", data, success, fail);
  },

  update:function(data,success,fail) {
    var data = this.convertSaveData(data);
    appWs.wsRequest("${className}Client/update", data, success, fail);
  },

  removeById:function(data,success,fail){
    appWs.wsRequest("${className}Client/removeById", data, success,fail);
  },

  getById: function (data, success, fail) {
    var that = this;
    appWs.wsRequest("${className}Client/getById", data, function(res) {
      //可以增加数据处理，再返回给展示层
      var result = that.convertShowData(res.data.result);
      success(result);
    }, fail);
  },

  search:function(data,success,fail) {
    var that = this;
    appWs.wsRequest("${className}Client/search", data, function (res) {
    	//可以增加数据处理，再返回给展示层
    	var result = util.listMap(res.data.result,that.convertShowData);
    	success(result);
    }, fail);
  }

}



module.exports = {
  ${className}Client: ${className}Client,
}

