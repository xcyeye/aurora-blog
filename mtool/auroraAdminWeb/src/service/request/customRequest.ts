import {AxiosRequestConfig} from "axios";
import CustomAxiosInstance from "@/service/request/instance";
import {getRequestResponse} from "@/service/request/request";

interface CustomRequestParam {
	url: string;
	method?: 'get' | 'post' | 'put' | 'delete';
	data?: any;
	axiosConfig?: AxiosRequestConfig;
}
export async function createCustomRequest<T>(param: CustomRequestParam, customInstance: CustomAxiosInstance): Promise<any> {
	const { url } = param;
	const method = param.method || 'get';
	const { instance } = customInstance;
	const res = (await getRequestResponse({
		instance,
		method,
		url,
		data: param.data,
		config: param.axiosConfig
	})) as Service.RequestResult<T>;
	return res;
}
