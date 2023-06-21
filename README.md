# <div align="center">![Agile Decision Engine](https://github.com/ponderyao/onlineImage/raw/main/readme-title/agile-decision-engine.jpg)</div>

**<div align="center">A flexible and modular code engine designed for complex and dynamic decision-making scenarios
<br>一款灵活的组件化代码引擎，专为复杂且动态的决策场景而设计</div>**

[<div align="center">![standard-readme compliant](https://img.shields.io/badge/JDK-1.8+-brightgreen.svg?style=flat-square)](https://github.com/ponderyao/agile-decision-engine)
[![standard-readme compliant](https://img.shields.io/badge/SpringBoot-2.3.12.RELEASE-brightgreen.svg?style=flat-square)](https://github.com/ponderyao/agile-decision-engine)
[![standard-readme compliant](https://img.shields.io/badge/Maven-3.8.6-brightgreen.svg?style=flat-square)](https://github.com/ponderyao/agile-decision-engine)
[![standard-readme compliant](https://img.shields.io/badge/Groovy-3.0.17-pink.svg?style=flat-square)](https://github.com/ponderyao/agile-decision-engine)
[![standard-readme compliant](https://img.shields.io/badge/MySQL-5.6+-orange.svg?style=flat-square)](https://github.com/ponderyao/agile-decision-engine)
[![standard-readme compliant](https://img.shields.io/badge/License-Apache2.0-blue.svg?style=flat-square)</div>](https://github.com/ponderyao/agile-decision-engine)

## 简介
Agile-Decision-Engine 是一款用于**复杂决策场景**的代码引擎组件，基于灵活的**动态编程**与**决策持久化**能力，为涉及策略模式的线上业务提供**敏捷迭代**支持。

## 特性
- 场景：大型项目中，需要频繁变更策略的复杂决策业务场景
- 目的：零代码迭代成本的敏捷策略发布，解决频繁变更策略的高成本代码迭代问题
- 本质：基于持久化决策表的动态编程实现
- 理论：策略模式，决策表，动态编程（Groovy），有向图，拓扑排序
- 约定：各决策单元之间的关系
  - 通过决策域（DecisionDomain）实现决策领域划分，因此决策引擎的使用需要提供唯一的决策域编码
  - 一个决策域中允许存在多条决策规则（DecisionRule），但在决策过程中只允许至多一条规则符合匹配
  - 一个决策域中包含一组条件桩（ConditionStub），一个条件桩对应一条条件脚本，设定条件类型，允许指定多个前置条件
  - 一个决策域中包含一组动作桩（ActionStub），一个动作桩对应一条动作脚本
  - 一条决策规则绑定一组条件项（ConditionEntry），与至少一个条件桩一一对应，设定条件值，仅当所给条件满足全部条件项时视为决策匹配
  - 一条决策规则绑定一组动作项（ActionEntry），与至少一个动作桩可以一对多对应，允许指定多个后置动作，存在执行顺序的动作间支持参数传递
- 核心：构建自动化双链结构
    - 决策链：基于决策表的决策规则、条件桩与条件项搭建，用于决策过程中的条件匹配，得到唯一的决策规则
    - 作业链：基于决策表的决策规则、动作桩与动作项搭建，用于决策匹配后的动作执行，实现动态的决策结果

## 说明
- 重点：**组件提供基础的决策表持久化管理接口，引用方需自行实现数据的增删改业务，可自由选择耦合到业务项目或单独构建决策管理面板**
- 由于决策表持久化的决策查询成本、动态编程的实时编译成本，不建议在过于简单的策略模式场景中使用该组件
- 对于复杂的动态决策场景，建议尽量为条件桩设定前置条件，使引擎能自动优化决策链路，减少不必要的决策成本
- 当策略动作间存在先后顺序时，后置动作的脚本方法需提供固定类型为 `Map<String, Object>` 的形参
- Groovy脚本必须是存在Java类结构的脚本，详见[**使用**]章节

## 使用
### Maven引入
```xml
<dependence>
    <groupId>io.github.ponderyao</groupId>
    <artifactId>agile-decision-engine</artifactId>
    <version>1.0.0</version>
</dependence>
```
### 数据准备
1. 执行 agile_decision.sql 数据表结构文件的脚本，创建数据表
2. 根据业务录入决策表数据，需保证数据之间的关系是符合上述约定的
### Java代码
`SpringBootApplication` 启动程序添加扫描包的注解
```java
@SpringBootApplication
@ComponentScan(basePackages = {"io.github.ponderyao.decision.*"})
public class AgileDecisionEngineTestApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AgileDecisionEngineTestApplication.class, args);
    }
    
}
```
定义决策域编码常量，需与数据表中的决策域表对应
```java
public class DecisionDomainConstant {
    
    public static final String PRICE_CALCULATION = "price-calculation";
    
}
```
在业务侧注入 `DecisionEngine` 决策引擎，传入决策条件并执行得到输出结果
```java
@Slf4j
@Service
@RequiredArgsConstructor
public class TestDecisionServiceImpl implements TestDecisionService {

    private final DecisionEngine decisionEngine;

    /**
     * 场景：根据商品信息、会员等级、促销活动三个维度计算商品最终价格
     */
    @Override
    public Map<String, Object> runDecision(TestDTO dto) {
        // 设定决策所需的前置业务对象
        Map<String, Object> previousObjects = new HashMap<>();
        previousObjects.put("goodsInfo", dto.getGoodsInfo());
        previousObjects.put("memberLevelInfo", dto.getMemberLevelInfo());
        previousObjects.put("promotionInfo", dto.getPromotionInfo());
        // 根据决策领域执行决策，得到决策结果
        DecisionCondition condition = new DecisionCondition(DecisionDomainConstant.PRICE_CALCULATION, previousObjects);
        DecisionResult result = decisionEngine.execute(condition);
        if (result.isMatched()) {
            log.info("本次决策采取的决策规则：" + result.getDecisionRuleName());
        } else {
            log.info("当前条件无匹配的决策规则");
        }
        return result.getResponseData();
    }
}
```
### 完整案例
参考官方案例：[agile-decision-engine-test](https://github.com/ponderyao/agile-decision-engine-test)