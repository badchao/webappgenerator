<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 

import * as ${className}Service from '../services/${className}';

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