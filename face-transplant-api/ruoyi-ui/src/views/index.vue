<template>
  <div class="app-container home">
    <a href="https://beian.miit.gov.cn" target="_blank" class="footer-link">鲁ICP备2024094456号-1</a>
  </div>
  
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.2",
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}

.app-container.home {
  min-height: 100vh; /* 使容器至少占满视口高度 */
  display: flex;
  flex-direction: column; /* 设置为垂直排列 */
}

.footer-link {
  position: absolute; /* 使用绝对定位 */
  bottom: 0;          /* 放置在底部 */
  margin-bottom: 1rem; /* 添加一些底部外边距 */
  text-align: center; /* 文本居中对齐 */
  width: 100%;        /* 占据整个宽度 */
  background-color: #f8f9fa; /* 背景颜色 */
  padding: 0.5rem 0; /* 内边距 */
}

/* 如果需要的话，可以添加媒体查询来适应不同屏幕尺寸 */
@media (max-width: 768px) {
  .footer-link {
    font-size: 0.8rem; /* 减小字体大小以适应较小屏幕 */
  }
}
</style>

