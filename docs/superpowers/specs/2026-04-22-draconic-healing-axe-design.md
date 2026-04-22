# Draconic Healing Axe Design

## 目标
- 创建独立 GTNH 1.7.10 模组 `DraconicHealingAxe-GTNH`。
- 当玩家穿戴 Draconic Evolution 的 Draconic 套装任意一件时，复制 Extra Utilities Healing Axe 拿在手上时的补饥饿/饱和效果。
- 如果 Nutrition 已加载，同一触发条件下将所有营养值逐步拉向 50%。
- 不复制 Healing Axe 攻击生物治疗、伤害亡灵或转化僵尸村民的效果。

## 实现方式
- 在服务器端 `TickEvent.PlayerTickEvent` 的 `END` 阶段执行。
- 使用 `GameRegistry.findItem("DraconicEvolution", itemName)` 解析四件 Draconic 护甲，避免直接编译依赖 Draconic Evolution 类。
- 如果玩家护甲栏任意槽匹配 Draconic 护甲，每 10 tick 补 1 hunger 和 1 saturation，最大值均不超过 20。
- 通过 AppleCore `AppleCoreAPI.mutator.setHunger` 和 `setSaturation` 修改数值。
- Nutrition 作为可选兼容：使用反射调用 `ca.wescook.nutrition.data.PlayerDataHandler` 和 `NutrientManager`，每 10 tick 将低于 50% 的营养值加 1、高于 50% 的营养值减 1。

## 依赖
- `required-after:DraconicEvolution`
- `required-after:AppleCore`
- `after:nutrition`

## 测试
- `HungerRefillServiceTest` 覆盖补给节奏、上限和饱和不超过 hunger。
- `NutritionBalanceServiceTest` 覆盖营养值向 50% 移动、接近 50% 时吸附、已平衡时不改动。
- `DraconicHealingAxeMetadataTest` 覆盖基础 mod 元数据稳定性。
