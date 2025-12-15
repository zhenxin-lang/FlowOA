<template>
  <div class="app-container home">
    <shortcuts />
    <el-divider />
    <el-row :gutter="10">
      <el-col :xs="24" :sm="24" :md="24" :lg="24">
        <el-card class="intro-card">
          <template v-slot:header>
            <div class="clearfix">
              <span>FlowOA办公自动化系统</span>
            </div>
          </template>
          <div class="body">
            <p>
              {{ readmeText }}
            </p>
            <p>
              <b>当前版本:</b> <span>v{{ version }}</span>
            </p>
             <p>
              <el-button type="primary" icon="Github" plain @click="goTarget('https://github.com/zhenxin-lang/FlowOA')">访问源码仓库</el-button>
            </p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="Index">
import shortcuts from "@/components/Dashboard/Shortcuts"
import useSettingsStore from '@/store/modules/settings'

const settingsStore = useSettingsStore()
const version = settingsStore.version;

const readmeText = "FlowOA：基于工作流引擎的办公自动化系统"

function goTarget(url) {
  window.open(url, '__blank')
}
</script>

<style scoped lang="scss">
.el-row {
  padding-top: 10px;
  padding-right: 10px;
  padding-left: 10px;
}

.el-col {
  padding-bottom: 10px;
}

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

  .item {
    margin-top: 10px;
    margin-right: 10px;
  }

  font-family: "open sans",
  "Helvetica Neue",
  Helvetica,
  Arial,
  sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

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
    line-height: 1.8;

    b {
      font-weight: 700;
    }
  }
  
  .intro-card {
    .body {
        font-size: 14px;
    }
  }
}
</style>