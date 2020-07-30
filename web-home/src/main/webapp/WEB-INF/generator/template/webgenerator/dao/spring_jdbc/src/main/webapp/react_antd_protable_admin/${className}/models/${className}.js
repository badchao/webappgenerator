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
const NAMESPACE = '${classNameLower}';
export default {
    namespace: NAMESPACE,
    
    state: {
        // table 相关
        dataSrouce: [],
        total: null,

        query: {
          current: null,
          pageSize: PAGE_SIZE,
        },

    },
    
    reducers: {
        merge(state, action) {
            const payload = action.payload;
            const dataSource = payload.dataSource;
            if(dataSource) {
              payload.dataSource = dataSource.map(convertShowData)
            }
            return { ...state, ...payload };
        },
        mergeQuery(state,{payload}) {
          return {
            ...state, query : payload
          }
        },
    },
    
    effects: {
        *findPage({ payload }, { call, put, select }) {
            const query = yield select(state => state[NAMESPACE].query);
            const params = { ...payload, page : payload.current, pageSize : query.pageSize };

            const response = yield call(${className}Service.findPage,params);
            yield put({
                type: 'merge',
                payload: {
                    dataSource: response.itemList,
                    total: response.paginator.totalItems,
                },
            });
            yield put({ type:'mergeQuery', payload: {
              ...params,
              current: response.paginator.page,
            }});
        },
        *remove({ payload }, { call, put, select }) {
            yield call(${className}Service.remove, payload);
            yield put({ type: 'refresh' });
        },
        *update({ payload }, { call, put, select }) {
            yield call(${className}Service.update, payload);
            yield put({ type: 'refresh' });
        },
        *create({ payload }, { call, put, select }) {
            yield call(${className}Service.create, payload);
            yield put({ type: 'refresh' });
        },
        *refresh({ payload }, { call, put, select }) {
          const query = yield select(state => state[NAMESPACE].query);
          yield put({ type: 'findPage', payload: query });
        },
    },

    //监控事件
    subscriptions: {
        setup({ dispatch, history }) {
        },
    },
};
