<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

import * as ${className}Service from '../services/${className}';

//为数据增加计算属性，用于前端展示。 应用场景: json string属性  =>  json 对象,  tags string => tags Array
function convertShowData(that) {
  if(!that) return that;
  
  var extendComputedProps = {
    get demoProperty() { //示例,增加 一个计算列 
      return that.toString()+", hello world";
    }
  }
  
  return Object.assign(that,extendComputedProps);
}
  
const PAGE_SIZE = 20;
export default {
    namespace: '${classNameLower}',
    state: {
        // table 相关
        dataSrouce: [],
        total: null,
        current: null,
        pageSize: PAGE_SIZE,
        
    },
    reducers: {
        merge(state, action) {
            const dataSource = action.payload.dataSource;
            if(dataSource) {
              action.payload.dataSource = dataSource.map(convertShowData)
            }
            return { ...state, ...action.payload };
        },
    },
    effects: {
        *findPage({ payload }, { call, put, select }) {
            const pageSize = yield select(state => state.${classNameLower}.pageSize);
            const params = { ...payload, page : payload.current, pageSize : pageSize };
            const response = yield call(${className}Service.findPage,params);
            yield put({
                type: 'merge',
                payload: {
                    dataSource: response.itemList,
                    total: response.paginator.totalItems,
                    current: response.paginator.page
                },
            });
        },
        *remove({ payload }, { call, put, select }) {
            yield call(${className}Service.remove, payload);
            const current = yield select(state => state.${classNameLower}.current);
            yield put({ type: 'findPage', payload: { current } });
        },
        *update({ payload }, { call, put, select }) {
            yield call(${className}Service.update, payload);
            const current = yield select(state => state.${classNameLower}.current);
            yield put({ type: 'findPage', payload: { current } });
        },
        *create({ payload }, { call, put, select }) {
            yield call(${className}Service.create, payload);
            const current = yield select(state => state.${classNameLower}.current);
            yield put({ type: 'findPage', payload: { current } });
        },
    },
    
    //监控事件
    subscriptions: {
        setup({ dispatch, history }) {
        },
    },
};