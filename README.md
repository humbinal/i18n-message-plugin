# i18n-message-plugin

![](https://img.shields.io/badge/status-up-brightgreen.svg)
![](https://img.shields.io/badge/build-passing-brightgreen.svg)
![](https://img.shields.io/badge/java-1.8%2B-brightgreen.svg)
![](https://img.shields.io/badge/gradle-6.x-brightgreen.svg)
![](https://img.shields.io/badge/maven-3.x-brightgreen.svg)
![](https://img.shields.io/badge/license-Apache2-blue.svg)
![](https://img.shields.io/badge/coverage-100%25-brightgreen.svg)

gradle and maven plugin for generate i18n message file from java source file written by javadoc.

> 注意: 请使用最新发布版本`1.0.2`

## 示例

`examples`目录中存放着基于`javafx`、`springboot`框架的应用完整示例，[前往查看](examples/README.md)。

## 动机

我们在进行java开发(j2ee、springboot、validation、javafx客户端应用等)时，常常需要支持多语言，i18n多语言消息文件一般配置在`resources/message.properties`
中，但是直接面临的一个问题就是配置文件和源代码分离，经常是代码中编写了消息的`label`
,配置文件中却忘记了增加消息的`value`; 同时，如果更改或删除消息的`label`，都可能导致与配置文件中的`label`不对应，最终运行时因找不到对应消息出错。

本插件旨在提供一种将消息的`label`和`value`均编写在`.java`源文件中方法，防止出现代码和配置不一致的问题，同时还可以提升开发效率(减少在常量定义文件/枚举文件和配置文件之间的来回切换)。

## 设计

为了实现i18n消息`label`和`value`均编写在源代码中，我们基于`java文档注释`实现。

注释使用javadoc的`tag`机制，其中：

- `language`标签代表当前使用的语言，可省略。

- `message`标签用于表示该消息对应的language语言翻译后的message，不可省略。

- `solution`标签用于提示用户出现该message消息的解决方案，可省略。

> 注意： 该插件仅支持生成单个语言的message文件，针对其他多国语言message文件的生成是在该工具生成后，发送给翻译人员，由翻译人员完成编写，最后再统一整合到项目中。

java开发中，一般使用枚举进行消息`label`管理，但是也可以使用常量进行消息`label`管理。本插件支持`枚举类`和`接口类`两种方式。

- 枚举模式

```java
public enum UserError {

    /**
     * @language zh_CN
     * @message 用户不存在
     * @solution 请检查用户名是否正确或进行用户注册
     */
    USER_NOT_EXIT("0x0001001", "user not exist"),

    /**
     * @language zh_CN
     * @message 用户密码错误
     * @solution 请检查用户密码或重置密码
     */
    USER_PWD_INCORRECT("0x0001002", "user password incorrect");

    UserError(String label, String errorMsg) {
    }
}
```

枚举模式编码如上，通过java文档注释的方式，每个枚举字段由两个枚举属性组成。

其中`label`属性用于生成i18n配置文件的`label`，不可省略;

`errorMsg`用于后台日志记录等功能，可省略，省略后的模式如下所示：

```java
public enum UserError {

    /**
     * @language zh_CN
     * @message 用户不存在
     * @solution 请检查用户名是否正确或进行用户注册
     */
    USER_NOT_EXIT("user.not.exist"),

    /**
     * @language zh_CN
     * @message 用户密码错误
     * @solution 请检查用户密码或重置密码
     */
    USER_PWD_INCORRECT("user.password.incorrect");

    UserError(String label) {
    }
}
```

- 接口模式

```java
public interface OrderError {

    /**
     * @language zh_CN
     * @message 订单不存在
     * @solution 请更换订单号
     */
    String ORDER_NOT_EXIST = "0x0002001";

    /**
     * @language zh_CN
     * @message 订单已失效
     * @solution 超出规定支付时间订单将失效
     */
    String ORDER_EXPIRED = "0x0002002";
}
```

其中每个属性对应一个消息`label`的值。

在接口模式中，还支持将`label`拆分为`前缀+后缀`的方式编写(仅支持单变量字符串相加)，不常用，编码如下所示：

```java
public interface UserError {

    /**
     * 模块label前缀
     */
    String PREFIX = "0x01f";

    /**
     * @message 用户不存在
     */
    String USER_NOT_EXIST = PREFIX + "002";
}
```

## 在Gradle中使用

**build.gradle**配置如下：

```groovy
plugins {
    id 'java'
    id 'io.github.humbinal.i18n.message.gradle-plugin' version '1.0.2'
}

group 'org.example'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

i18nMessageGenerator {
    sourceDirectory = 'src/main/java'
    outputDirectory = 'build/classes/java/main'
    outputFileNamePrefix = "message"
    sourceTypes = ['enum', 'interface']
    language = 'zh_CN'
    defaultLanguage = 'zh_CN'
}
```

配置完毕执行: `./gradlew generateI18nMessage` , 即可在`build/classes/java/main`目录下生成message文件。

执行: `./gradlew jar` ，即可将message文件打进jar包中。

## 在Maven中使用

在maven模块`pom.xml`文件中引入插件，并进行如下配置：

```xml

<build>
    <plugins>
        <plugin>
            <groupId>io.github.humbinal</groupId>
            <artifactId>i18n-message-maven-plugin</artifactId>
            <version>1.0.2</version>
            <configuration>
                <sourceDirectory>src/main/java</sourceDirectory>
                <sourceFileEndsWith>Error.java</sourceFileEndsWith>
                <outputDirectory>target/classes</outputDirectory>
                <outputFileNamePrefix>message</outputFileNamePrefix>
                <sourceTypes>
                    <sourceType>enum</sourceType>
                    <sourceType>interface</sourceType>
                </sourceTypes>
                <language>zh_CN</language>
                <defaultLanguage>zh_CN</defaultLanguage>
            </configuration>
            <executions>
                <execution>
                    <phase>compile</phase>
                    <goals>
                        <goal>generate</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

配置完毕执行: `maven compile`, 即可在`target/classes`下生成message文件，同时该message文件也会打进jar包中。

## 完整可配置项列表

### 生成器配置

gradle配置在`i18nMessageGenerator`中，maven配置在`configuration`中，配置列表如下：

- sourceDirectory: 需要扫描的`.java`源代码目录,必填

- outputDirectory: 生成的message文件的输出目录,必填

- outputFileNamePrefix: 输出的i18n文件名前缀, 必填

- sourceFileEndsWith: 指定仅扫描某种后缀结尾的文件,默认不限制

- sourceTypes: 需要扫描的源码类型, 支持`enum`、`interface`或二者同时

- language：生成配置文件的语言，当配置的language与源代码中注释的language不一致时将生成失败

- defaultLanguage： 生成message文件的默认语言,即：

> 默认语言message文件为： message.properties；
> 非默认语言的message文件为：message_XXX.properties, XXX为对应语言；
> 即当language和defaultLanguage配置一致时，输出两个不同名的文件，但内容一致；
> language和defaultLanguage配置不一致时，仅输出一个文件。

- solutionLabelSuffix: 当我们使用solution注释时，生成的solution对应label的后缀

### 执行阶段配置

gradle和maven插件默认执行时机均在compile阶段。

#### 调整在gradle中的执行时机

目前绑定在gradle的任务`classes`和`jar`之间执行，但是可以单独使用插件手动执行，因此不需要调整依赖顺序。

手动执行也非常方便，借助IDE或者命令行执行：

```
./gradlew generateI18nMessage
```

#### 调整在maven中的执行时机

指定在maven生命周期的哪个阶段生成，默认为`compile`。

maven插件中配置`execution`的`phase`即可，例如可以调整为`validate`,在校验阶段就生成，可以不进行编译动作，加快执行速度。

maven插件的goal为`generate`。

配置方式如下：

```xml

<executions>
    <execution>
        <phase>validate</phase>
        <goals>
            <goal>generate</goal>
        </goals>
    </execution>
</executions>
```

## 开发及缺陷

本项目为标准gradle项目，克隆并打开即可进行二次开发。

如使用中有任何问题请及时提交issue，同时欢迎大家参与代码优化及特性贡献。
