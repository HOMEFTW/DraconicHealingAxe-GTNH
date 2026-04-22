# TODO 列表

## 当前计划
- 无。

## 未来想法
- [ ] 可选配置补给频率、每次补饥饿量、饱和量和营养值平衡步长。

## 已完成
- [x] 创建一个新的 GTNH 模组，使 Draconic Evolution 的 Draconic 套装任意一件即可触发 Healing Axe 手持补饥饿/饱和效果。
- [x] 运行 `.\gradlew.bat test` 和 `.\gradlew.bat compileJava` 验证项目可测试、可编译。
- [x] 运行 `.\gradlew.bat build` 生成可用 jar。
- [x] 加入 Nutrition 兼容：装备任意 Draconic 套装时，每次 Healing Axe 补给节奏将所有营养值向 50% 移动 1 点。
- [x] 将构建 JDK 探测迁移为读取 `JAVA_HOME` 环境变量，并验证 Zulu25 启动 Gradle wrapper。

## 拒绝 / 暂缓
- 不实现 Healing Axe 攻击生物治疗或转化僵尸村民效果：用户明确只需要手持补饥饿/饱和效果。
