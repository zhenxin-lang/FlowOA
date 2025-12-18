# FlowOA - 基于工作流引擎的办公自动化系统

<p align="center">
  <img src="https://img.shields.io/badge/JDK-1.8-green.svg" alt="Java 8">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.7.17-blue.svg" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Vue-3.x-42b883.svg" alt="Vue 3">
  <img src="https://img.shields.io/badge/Activiti-5.23-orange.svg" alt="Activiti">
  <img src="https://img.shields.io/badge/Build-Maven-red.svg" alt="Maven">
</p>

## 📖 项目介绍 | Introduction

**FlowOA** 是一款致力于解决传统 OA 系统流程僵化痛点的现代化办公自动化系统。

与传统 OA 不同，FlowOA 引入了“动态配置”的设计思想。通过集成可视化流程配置组件和动态表单渲染技术，实现了业务流程的“所见即所得”。管理员无需编写代码，通过简单的拖拽即可完成复杂审批流的配置与调整，极大地降低了系统的维护成本。

本项目在底层架构上对 **Activiti** 引擎进行了深度定制，实现了 **“虚拟节点 (VNode)”** 架构模型，解耦了业务逻辑与底层引擎，支持复杂的中国式审批场景（如加签、转办、条件网关等）。

---

## ✨ 核心特性 | Key Features

*   **🎨 可视化流程配置**
    *   集成图形化配置界面，支持串行、并行、条件分支等多种节点拖拽。
    *   定义标准的前后端 JSON 通信协议，自动将前端配置解析为可执行的 BPMN 模型。

*   **📝 JSON Schema 动态表单**
    *   基于 JSON Schema 描述表单结构，系统运行时自动渲染业务表单。
    *   支持文本、数字、日期、附件等多种组件，满足多样化办公需求。

*   **🔀 灵活的审批流转**
    *   深度定制 Activiti 内核，支持**加签、转办、委派、退回任意节点**等复杂操作。
    *   支持基于人员、角色、部门负责人等多种维度的审批人设置。

*   **🛡️ 完备的质量保障体系**
    *   构建了 **“单元测试 + 集成测试”** 双重验证体系。
    *   引入 `Mockito-Inline` 技术攻克静态方法 Mock 难点，核心业务逻辑覆盖率达 100%。

---

## 🛠️ 技术栈 | Tech Stack

### 后端 (Backend)
*   **基础框架**: Spring Boot 2.7.17
*   **工作流引擎**: Activiti 5.23
*   **ORM 框架**: MyBatis Plus 3.5.1
*   **规则引擎**: Drools 6.5.0
*   **工具库**: Hutool, FastJSON2, Guava, Lombok
*   **测试框架**: JUnit 5, Mockito-Inline, Spring Boot Test

### 前端 (Frontend)
*   **核心框架**: Vue 3.x (Composition API)
*   **构建工具**: Vite
*   **UI 组件库**: Element Plus
*   **状态管理**: Pinia
*   **HTTP 客户端**: Axios

### 数据存储
*   **数据库**: MySQL 8.0

---

## 📂 项目结构 | Project Structure

FlowOA 采用多模块 Maven 架构设计：

```text
FlowOA
├── flowoa-base      # [底层] 引擎基础层，包含对 Activiti 源码的定制修改
├── flowoa-engine    # [核心] 业务引擎层，实现 VNode 解析、流程流转算法、表单逻辑
├── flowoa-web       # [接入] Web 接口层，提供 RESTful API，处理权限与系统集成
├── flowoa-vue       # [前端] 基于 Vue3 的客户端，包含可视化设计器与业务页面
└── script           # 数据库初始化 SQL 脚本
```

---

## 🚀 快速开始 | Getting Started

### 1. 环境准备
*   JDK 1.8+
*   Maven 3.6+
*   Node.js 16+
*   MySQL 8.0+

### 2. 数据库初始化
在 MySQL 中创建数据库 `flowoa_db`，并依次执行 `script` 目录下的 SQL 文件：
1.  `act_init_db.sql` (Activiti 引擎表)
2.  `bpm_init_db.sql` (业务流程表)
3.  `bpm_init_db_data.sql` (初始化数据)

### 3. 后端启动
```bash
# 在项目根目录下执行编译
mvn clean install

# 进入 Web 模块启动
cd flowoa-web
mvn spring-boot:run
```

### 4. 前端启动
```bash
cd flowoa-vue

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

访问地址：`http://localhost:80` (默认)

---

## 🧪 测试与质量 | Testing

本项目注重工程质量，构建了完整的自动化测试链：

*   **单元测试 (Unit Test)**: 使用 Mockito 隔离外部依赖，专注于 VNode 解析算法与流转逻辑的验证。
*   **集成测试 (Integration Test)**: 启动真实 Spring Context 与 MySQL 数据库，验证 API 端到端的正确性。

**执行测试命令：**
```bash
mvn test
```

---

**FlowOA Team**

- 罗文辉
- 李晓阳
- 肖苏阳
- 刘锐
- 姚书涵
