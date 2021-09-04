module.exports = {
  sidebar: {
    "/config/feature/": [{
      children: [
        '/config/feature/README.md',
        '/config/feature/donate.md',
        '/config/feature/feature-config.md',
        '/config/feature/image.md',
        '/config/feature/recommend.md',
        '/config/feature/seo.md',
        '/config/feature/social.md',
      ],
      text: "主题特征配置",
    }, ],
    "/config/": [{
      children: [
        '/config/feature/readme.md',
        '/config/other/readme.md',
        '/config/page/readme.md',
      ],
      text: "其他配置",
    }, ],
    "/config/other/": [{
      children: [
        '/config/other/readme.md',
        '/config/other/footer.md',
        '/config/other/message.md',
      ],
      text: "其他配置",
    }, ],
    "/config/page/": [{
      children: [
        '/config/page/README.md',
        '/config/page/about.md',
        '/config/page/ad.md',
        '/config/page/friendlink.md',
        '/config/page/mood.md',
        '/config/page/tag.md',
        '/config/page/page.md',
      ],
      text: "页面配置",
    }, ],
    "/home/": [{
      children: [
        '/home/deploy.md',
        '/home/directory-structure.md',
        '/home/readme.md',
      ],
      text: "部署/结构/主题",
    }, ]
  }
};