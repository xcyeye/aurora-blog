import {defineStore} from "pinia";
import {Condition, PageData} from "@/bean/core/bean";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import RequestResult = Service.RequestResult;

export interface ArticleStoreBean {
  condition?: Condition,
  articleArr?: Array<ArticleVo>,
  currentPageSize?: number,
  currentPage?: number,
  queryArticleDataMethod?: (condition: Condition) => Promise<RequestResult<PageData<ArticleVo>>>
}

const articleInfo: ArticleStoreBean = {}
export const useArticleStore = defineStore('article_store', {
  state: () => {
    return {
      articleInfo
    }
  },
  getters: {
    getArticleInfo: state => (): ArticleStoreBean => {
      return state.articleInfo
    }
  },
  actions: {
    setArticleInfo(articleInfo: ArticleStoreBean) {
      this.articleInfo = articleInfo
    }
  }
})