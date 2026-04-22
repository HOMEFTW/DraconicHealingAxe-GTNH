# Draconic Healing Axe Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a GTNH 1.7.10 addon that gives any equipped Draconic Evolution Draconic armor piece the held Healing Axe hunger and saturation refill effect.

**Architecture:** Keep the behavior in a small pure Java service and a Forge tick event adapter. Detect Draconic armor by registry names instead of importing Draconic Evolution classes.

**Tech Stack:** Java, Forge 1.7.10, GTNH Gradle convention, AppleCore API, JUnit 5.

---

### Task 1: Project Skeleton

**Files:**
- Create: `settings.gradle`
- Create: `build.gradle`
- Create: `gradle.properties`
- Create: `dependencies.gradle`
- Create: `src/main/resources/mcmod.info`
- Create: `src/main/java/com/github/skystardust/draconichealingaxe/DraconicHealingAxe.java`
- Create: `src/main/java/com/github/skystardust/draconichealingaxe/CommonProxy.java`

- [x] Create the GTNH convention Gradle project.
- [x] Add `required-after:DraconicEvolution;required-after:AppleCore`.
- [x] Register a common proxy and event handler during init.

### Task 2: Core Hunger Logic

**Files:**
- Create: `src/main/java/com/github/skystardust/draconichealingaxe/hunger/PlayerHungerState.java`
- Create: `src/main/java/com/github/skystardust/draconichealingaxe/hunger/HungerRefillService.java`
- Test: `src/test/java/com/github/skystardust/draconichealingaxe/hunger/HungerRefillServiceTest.java`

- [x] Write failing tests for cadence, refill amount, and caps.
- [x] Implement `HungerRefillService`.

### Task 3: Forge Integration

**Files:**
- Create: `src/main/java/com/github/skystardust/draconichealingaxe/hunger/DraconicArmorRegistry.java`
- Create: `src/main/java/com/github/skystardust/draconichealingaxe/hunger/AppleCorePlayerHungerState.java`
- Create: `src/main/java/com/github/skystardust/draconichealingaxe/hunger/DraconicHealingEventHandler.java`

- [x] Resolve Draconic armor items by registry name.
- [x] Use AppleCore mutator to set hunger and saturation.
- [x] Run on server-side player tick end only.

### Task 4: Verification

**Files:**
- Update: `log.md`
- Update: `ToDOLIST.md`
- Update: `context.md`

- [x] Run `./gradlew.bat test`.
- [x] Run `./gradlew.bat compileJava`.
- [x] Run `./gradlew.bat build`.
- [x] Update project records with final verification result.

### Task 5: Nutrition 50% Balance

**Files:**
- Create: `src/main/java/com/github/skystardust/draconichealingaxe/nutrition/NutritionBalanceService.java`
- Create: `src/main/java/com/github/skystardust/draconichealingaxe/nutrition/NutritionAdapter.java`
- Create: `src/main/java/com/github/skystardust/draconichealingaxe/nutrition/ReflectiveNutritionAdapter.java`
- Modify: `src/main/java/com/github/skystardust/draconichealingaxe/hunger/DraconicHealingEventHandler.java`
- Test: `src/test/java/com/github/skystardust/draconichealingaxe/nutrition/NutritionBalanceServiceTest.java`

- [x] Write failing tests for moving nutrients toward 50%.
- [x] Implement `NutritionBalanceService`.
- [x] Add optional reflective Nutrition adapter.
- [x] Run targeted test.
- [x] Run full build.
