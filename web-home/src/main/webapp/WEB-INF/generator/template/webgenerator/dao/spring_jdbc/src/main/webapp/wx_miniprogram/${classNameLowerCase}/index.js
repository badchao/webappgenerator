<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   
<#assign dollor = '$'>   

var jsPath = '../..';
var util = require(jsPath+'/utils/util.js');
var appEnum = require(jsPath +'/utils/app_enum.js');
var appWs = require(jsPath +'/utils/app_ws.js');
var service = require(jsPath +'/utils/app_service.js');
var helper = require(jsPath +'/utils/app_helper.js');
var router = require(jsPath +'/utils/app_router.js');
var model = require(jsPath +'/utils/app_model.js');
var config = require(jsPath +'/utils/app_config.js');
var security = require(jsPath +'/utils/app_security.js');

var webservice = require('./${className}WebService.js');


const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,

    <#list table.pkColumns as column>
    ${table.pkColumn.columnNameLower}: null,
    </#list>
    
    query:null, //搜索关键字
    dataList:[], 
    page: 1,
    pageSize: 10,
    hasMoreData: true,
  },

  /**
   * 增加
   */
  doAdd: function (e) {
    var that = this;
    wx.navigateTo({
      url: 'form/index'
    });
  },

  doDelete: function (e) {
    console.info("${className} doDelete", e);
    var that = this;
    var dataset = e.currentTarget.dataset;
    
    <#list table.pkColumns as column>
    var ${column.columnNameLower} = dataset.${column.columnName?lower_case};
    </#list>

    wx.showModal({
      title: '确认删除?',
      success: function (sm) {
        if (sm.confirm) {
          webservice.${className}WebService.removeById({ <#list table.pkColumns as column>${column.columnNameLower}:${column.columnNameLower}<#if column_has_next>,</#if></#list> }, function (res) {
            that.execSearch();
          });
        }
      }
    });
  },

  doEdit: function(e) {
    console.info("${className} doEdit", e);
    var that = this;
    var dataset = e.currentTarget.dataset;
    
    <#list table.pkColumns as column>
    var ${column.columnNameLower} = dataset.${column.columnName?lower_case};
    </#list>
    
    wx.navigateTo({
      url: 'form/index?edit=true'+<#list table.pkColumns as column>'&${column.columnNameLower}='+${column.columnNameLower}<#if column_has_next>+</#if></#list> 
    });
  },

  doSearch:function(e) {
    console.info("${className} doSearch", e);
    var that = this;
    var query = e.detail.value;
    that.setData({
      query: query
    });

    this.execSearch();
  },

  execSearch: function(isNextPage) {
    var that = this;
    var query = that.data.query;
    var page = isNextPage ? that.data.page : 1;
    var pageSize =that.data.pageSize;
    
    webservice.${className}WebService.search({ query: query, page:page,pageSize:pageSize }, function (dataList) {
      
      var finalDataList = [];
      if (isNextPage) {
        finalDataList = that.data.dataList;
        finalDataList = finalDataList.concat(dataList);
      }else {
        finalDataList = dataList;
      }
      

      that.setData({
        dataList: finalDataList,
        hasMoreData: dataList.length >= pageSize,
        page: page + 1,
      });
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.info("${className} index onload optoins:",options);

    var that = this;
    that.setData(options);
    that.execSearch();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.execSearch();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (this.data.hasMoreData) {
      this.execSearch(true);
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})