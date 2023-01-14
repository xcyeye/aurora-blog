import type { AxiosRequestConfig } from 'axios';
import { EnumContentType } from '@/enum/index';

export const baseAxiosRequestConfig: AxiosRequestConfig = {
  headers: {
    'Content-Type': EnumContentType.json
  }
};

export const authorizedGrantTypes: Array<string> = [
	'authorization_code','client_credentials','implicit','refresh_token','password'
]
