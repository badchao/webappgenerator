<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   
<#assign dollor = '$'>   

// pages/sandbox/listbox/add/index.js
var jsPath = '../../../';
var util = require(jsPath + '/utils/util.js');
var appEnum = require(jsPath + '/utils/app_enum.js');
var appWs = require(jsPath + '/utils/app_ws.js');
var service = require(jsPath + '/utils/app_service.js');
var helper = require(jsPath + '/utils/app_helper.js');
var router = require(jsPath + '/utils/app_router.js');
var model = require(jsPath + '/utils/app_model.js');
var config = require(jsPath + '/utils/app_config.js');
var security = require(jsPath + '/utils/app_security.js');

var webservice = require('../${className}WebService.js');

const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    edit:false,

    
    <#list table.columns as column>
	${column.columnNameLower} : null,
	</#list>
  },

  goIndex() {
    wx.navigateTo({
      url: '../index',
    });
  },

  doSave:function(e){
    console.info("${className} doSave,event:", e);
    var that = this;
    var form = e.detail.value;

    //do form check


    if(that.data.edit) {

      webservice.${className}WebService.update( form, function (res) {
        that.goIndex();
      });

    }else {

      webservice.${className}WebService.create( form, function (res) {
        that.goIndex();
      });

    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.info("${className} form onload,options", options);
    var that = this;
    this.setData(options);

    if(options.edit) {
      //for edit
      
      webservice.${className}WebService.getById(options, function (entity) {
        that.setData(entity);
      });
    }else {
      //for create
      
    }
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
    console.info("${className} onPullDownRefresh");
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    console.info("${className} onReachBottom");
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})