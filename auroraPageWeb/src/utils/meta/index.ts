import {StringUtil} from "@/utils";
import blogConfig from '@/config/blogConfig.json'
import {useMeta, useActiveMeta } from "vue-meta";

export const setMetaTitle = (title: string | null | undefined) => {
  if (true) return
  let metaContent = title
  if (!StringUtil.haveLength(title)) {
    metaContent = blogConfig.defaultMetaInfo.title
  }
  const metadata = useActiveMeta()
  console.log(metadata);
  useMeta({
    title: metaContent,
    meta: [
      {
        name: 'twitter:card',
        content: 'summary'
      },
      {
        name: 'twitter:site',
        content: '@aurora_fault'
      },
      {
        name: 'twitter:creator',
        content: '@aurora_blog_system'
      },
      {
        name: 'twitter:title',
        content: metaContent
      },

      // google
      {
        name: 'og:type',
        content: 'article'
      },
      {
        name: 'twitter:site',
        content: '@aurora_fault'
      },
      {
        name: 'og:site_name',
        content: 'Aurora 博客系统'
      },
      {
        name: 'og:title',
        content: metaContent
      },
    ]
  })
}

export const setMetaDescription = (description: string | null | undefined) => {
  if (true) return
  let metaContent = description
  if (!StringUtil.haveLength(description)) {
    metaContent = blogConfig.defaultMetaInfo.description
  }
  useMeta({
    meta: [
      {
        name: 'description',
        content: metaContent
      },
      {
        name: 'og:description',
        content: metaContent
      }
    ]
  })
}

export const setMetaKeywords = (keywords: string | null | undefined) => {
  if (true) return
  let metaContent = keywords
  if (!StringUtil.haveLength(keywords)) {
    metaContent = blogConfig.defaultMetaInfo.keywords
  }
  useMeta({
    meta: [
      {
        name: 'keywords',
        content: metaContent
      }
    ]
  })
}