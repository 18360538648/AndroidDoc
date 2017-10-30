# MVC和MVP架构
## 1. MVC架构

MVC架构是一个很老的架构，M为逻辑处理层、V为View层，对应各种布局文件、C为Controller层，对应Activity。在这种架构中，Controller层即Activity，负担过重，各层次之间的耦合情况也会变得很严重

## 2. MVP架构

MVP架构,把布局文件和Activity做为View层，增加`Presenter`层,`Presenter`层负责与`model`层进行交互，完成以后再与`View`层进行回调交互，刷新UI，这样核心落于`Presenter`层，`View`层与`model`层无联系，降低耦合度。

[MVPdemo](https://github.com/18360538648/Mvpdemo/tree/master)