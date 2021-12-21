//import axios from "axios"
import axios from "axios";

export function req(option) {
    return new Promise((resolve, reject) => {
        let timeout = 0;
        let method = "GET"
        let baseURL = ""
        try {
            timeout = option.timeout
            method = option.method
            baseURL = option.baseURL
        }catch (e) {

        }

        if (timeout === undefined) {
            timeout = 5000
        }

        if (method === undefined) {
            method = "get"
        }

        if (baseURL === '') {
            baseURL = 'https://international.v1.hitokoto.cn/?c=k&max_length=45'
        }

        const instance = axios.create({
            baseURL,
            timeout,
            method
        })

        //拦截器
        instance.interceptors.request.use(config => {
            return config
        },err => {
            return err
        })

        //响应拦截器
        instance.interceptors.response.use(response => {
            return response
        },err => {

            return err
        })

        instance(option)
            .then(res => {
                //res为响应结果 执行这里，跳转回到调用该方法处
                resolve(res.data)
            }).catch(err => {
            //err为异常结果
            reject(err)
        })
    })
}

export function cors(option) {
    return new Promise((resolve, reject) => {
        const instance = axios.create(option)
        //拦截器
        instance.interceptors.request.use(config => {
            return config
        }, err => {
            return err
        })

        //响应拦截器
        instance.interceptors.response.use(response => {
            return response
        }, err => {

            return err
        })

        instance(option)
            .then(res => {
                //res为响应结果 执行这里，跳转回到调用该方法处
                resolve(res.data)
            }).catch(err => {
            //err为异常结果
            reject(err)
        })
    })
}
