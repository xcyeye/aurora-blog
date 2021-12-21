import axios from "axios";

export default {
    req: function (option) {
        return new Promise((resolve, reject) => {
            let timeout = 5000;
            let method = "GET"
            let baseURL = ""
            try {
                timeout = option.timeout
                method = option.method
                baseURL = option.baseURL
            }catch (e) {

            }

            const instance = axios.create({
                baseURL,
                timeout,
                method: 'GET',
                url: option.url,
                params: option.params,
                withCredentials: true
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
                resolve({
                    src: option.songSrc,
                    data: res.data
                })
            }).catch(err => {
                //err为异常结果
                reject(err)
            })
        })
    },
}
