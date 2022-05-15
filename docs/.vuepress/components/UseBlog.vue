<template>
  <!--docs/.vuepress/components/Aurora.vue-->
  <aurora-global :show-bg="true" :show-style-menu="true" :show-navbar="true">
    <template #top>
      <div class="use-blog-top">
        <h2>这是顶部</h2>
      </div>
    </template>
    <template #center>
      <div class="use-blog mood-article" id="use-blog">
        <h2>这是中部</h2>
      </div>
    </template>
    <template #bottom>
      <div class="use-blog-comment">
        <comment :path-name="$route.path"/>
      </div>
      <aurora-footer/>
    </template>
  </aurora-global>
</template>

<script>

export default {
  name: "UseBlog",
  data() {
    return {
      themeConfig: {},
    }
  },
  created() {
    this.sites = sites
    for (let i = 0; i < this.sites.length; i++) {
      let color = this.randomColors[this.getRandomInt(0,this.randomColors.length -1)]
      let hexToRgb = this.hexToRgb(color);
      this.colors.push(hexToRgb)
    }
  },
  methods: {

  },
  computed: {
    
  }
}
</script>

<style scoped lang="css">
.use-blog {
  margin: 0 auto;
  height: max-content;
}
#use-blog {
  margin-bottom: 3rem;
}
.use-blog-message,.use-blog-comment {
  margin: 0 auto;
}
.use-blog-code {
  background: rgba(255,255,255,0);
}
.use-blog-message {
  margin-top: 3rem;
  margin-bottom: 3rem;
}
@media screen and (min-width: 719px) {
  .use-blog,.use-blog-comment,.use-blog-message {
    width: 70%;
  }
  #link-item {
    width: calc(85% / 3);
  }
}

@media screen and (max-width: 719px) {
  .use-blog,.use-blog-comment,.use-blog-message {
    width: 96%;
  }

  #link-item {
    width: 100%;
  }
  .use-blog-comment {
    margin-bottom: 3rem;
  }
}
.use-link-item {
  margin-top: 2rem;
}
</style>