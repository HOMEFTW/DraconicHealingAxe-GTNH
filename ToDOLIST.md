# TODO 列表

## 当前计划
- 无。

## 未来想法
- [ ] 可选配置补给频率、每次补饥饿量、饱和量和营养值平衡步长。

## 已完成
- [x] 将版本升级到 `0.2.0-beta`，并准备发布 GitHub Release。
- [x] 将项目移植到 GTNH `2.9.0-beta-1`，并完成 `compileJava`、`build` 和 jar 打包验证。
- [x] 清理本地生成目录 `.gradle/`、`build/`、`run/`、`bin/` 和临时文件 `##dummy`，并将 `bin/` 加入 `.gitignore`。
- [x] 修复 `log.md`、`ToDOLIST.md`、`context.md` 的中文乱码。
- [x] 将 Gradle wrapper 与 Maven 仓库解析切换到腾讯云镜像。
- [x] 创建一个新的 GTNH 模组，使 Draconic Evolution 的 Draconic 套装任意一件即可触发 Healing Axe 手持补饥饿/饱和效果。
- [x] 运行 `.\gradlew.bat test` 和 `.\gradlew.bat compileJava` 验证项目可测试、可编译。
- [x] 运行 `.\gradlew.bat build` 生成可用 jar。
- [x] 加入 Nutrition 兼容：装备任意 Draconic 套装时，每次 Healing Axe 补给节奏将所有营养值向 50% 移动 1 点。
- [x] 将构建 JDK 探测迁移为读取 `JAVA_HOME` 环境变量，并验证 Zulu25 启动 Gradle wrapper。

## 拒绝 / 暂缓
- 不实现 Healing Axe 攻击生物治疗或转化僵尸村民效果：用户明确只需要手持补饥饿/饱和效果。
