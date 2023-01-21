/** 枚举的key类型 */
declare namespace EnumType {
  /** 布局组件名称 */
  type LayoutComponentName = keyof typeof import('@/enum').EnumLayoutComponentName;

  /** 布局模式 */
  type ThemeLayoutMode = keyof typeof import('@/enum').EnumThemeLayoutMode;

  /** 多页签风格 */
  type ThemeTabMode = keyof typeof import('@/enum').EnumThemeTabMode;

  /** 水平模式的菜单位置 */
  type ThemeHorizontalMenuPosition = keyof typeof import('@/enum').EnumThemeHorizontalMenuPosition;

  /** 过渡动画 */
  type ThemeAnimateMode = keyof typeof import('@/enum').EnumThemeAnimateMode;

  /** 登录模块 */
  type LoginModuleKey = keyof typeof import('@/enum').EnumLoginModule;
}

/** 请求的相关类型 */
declare namespace Service {
  /**
   * 请求的错误类型：
   * - axios: axios错误：网络错误, 请求超时, 默认的兜底错误
   * - http: 请求成功，响应的http状态码非200的错误
   * - backend: 请求成功，响应的http状态码为200，由后端定义的业务错误
   */
  type RequestErrorType = 'axios' | 'http' | 'backend';

  /** 请求错误 */
  interface RequestError {
    /** 请求服务的错误类型 */
    type: RequestErrorType;
    /** 错误码 */
    code: string | number;
    /** 错误信息 */
    msg: string;
  }

  /** 后端接口返回的数据结构配置 */
  interface BackendResultConfig {
    /** 表示后端请求状态码的属性字段 */
    codeKey: string;
    /** 表示后端请求数据的属性字段 */
    dataKey: string;
    /** 表示后端消息的属性字段 */
    msgKey: string;
    /** 后端业务上定义的成功请求的状态 */
    successCode: number | string;
    /** 接口请求的标志 */
    successFlag: string;
  }

  /** 自定义的请求成功结果 */
  interface SuccessResult<T = any> {
    /** 请求错误 */
    error: null;
    /** 请求数据 */
    data: T;
  }

  /** 自定义的请求失败结果 */
  interface FailedResult {
    /** 请求错误 */
    error: RequestError;
    /** 请求数据 */
    data: null;
  }

  /** 自定义的请求结果 */
  type RequestResult<T = any> = SuccessResult<T> | FailedResult;

  /** 多个请求数据结果 */
  type MultiRequestResult<T extends any[]> = T extends [infer First, ...infer Rest]
    ? [First] extends [any]
      ? Rest extends any[]
        ? [Service.RequestResult<First>, ...MultiRequestResult<Rest>]
        : [Service.RequestResult<First>]
      : Rest extends any[]
      ? MultiRequestResult<Rest>
      : []
    : [];

  /** 请求结果的适配器函数 */
  type ServiceAdapter<T = any, A extends any[] = any> = (...args: A) => T;

  /** mock示例接口类型：后端接口返回的数据的类型 */
  interface MockServiceResult<T = any> {
    /** 状态码 */
    code: string | number;
    /** 接口数据 */
    data: T;
    /** 接口消息 */
    message: string;

    success: boolean;
  }

  /** mock的响应option */
  interface MockOption {
    url: Record<string, any>;
    body: Record<string, any>;
    query: Record<string, any>;
    headers: Record<string, any>;
  }
}

/** 主题相关类型 */
declare namespace Theme {
  /** 主题配置 */
  interface Setting {
    /** 暗黑模式 */
    darkMode?: boolean;
    /** 是否自动跟随系统主题 */
    followSystemTheme?: boolean;

    /** 全局的圆角 */
    borderRadius?: number,

    /** 全局的字体颜色 */
    fontColor?: string,

    /** 全局字体 */
    fontFamily?: string,

    fontSize?: string
  }
}

declare namespace App {
  /** 全局头部属性 */
  interface GlobalHeaderProps {
    /** 显示logo */
    showLogo: boolean;
    /** 显示头部菜单 */
    showHeaderMenu: boolean;
    /** 显示菜单折叠按钮 */
    showMenuCollapse: boolean;
  }

  /** 菜单项配置 */
  type GlobalMenuOption = import('naive-ui').MenuOption & {
    key: string;
    label: string;
    routeName: string;
    routePath: string;
    icon?: () => import('vue').VNodeChild;
    children?: GlobalMenuOption[];
  };

  /** 面包屑 */
  type GlobalBreadcrumb = import('naive-ui').DropdownOption & {
    key: string;
    label: string;
    disabled: boolean;
    routeName: string;
    hasChildren: boolean;
    children?: GlobalBreadcrumb[];
  };

  /** 多页签Tab的路由 */
  interface GlobalTabRoute
    extends Pick<import('vue-router').RouteLocationNormalizedLoaded, 'name' | 'fullPath' | 'meta'> {
    /** 滚动的位置 */
    scrollPosition: {
      left: number;
      top: number;
    };
  }

  interface MessageTab {
    /** tab的key */
    key: number;
    /** tab名称 */
    name: string;
    /** badge类型 */
    badgeProps?: import('naive-ui').BadgeProps;
    /** 消息数据 */
    list: MessageList[];
  }

  interface MessageList {
    /** 数据唯一值 */
    id: number;
    /** 头像 */
    avatar?: string;
    /** 消息icon */
    icon?: string;
    svgIcon?: string;
    /** 消息标题 */
    title: string;
    /** 消息发送时间 */
    date?: string;
    /** 消息是否已读 */
    isRead?: boolean;
    /** 消息描述 */
    description?: string;
    /** 标签名称 */
    tagTitle?: string;
    /** 标签props */
    tagProps?: import('naive-ui').TagProps;
  }
}

declare namespace I18nType {
  interface Schema {
    system: {
      title: string;
    };
    routes: {
      dashboard: {
        dashboard: string;
        analysis: string;
        workbench: string;
      };
      about: {
        about: string;
      };
    };
  }
}
