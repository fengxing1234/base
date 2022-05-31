### 组件化支撑的设计理论
组件化所支撑设计理念是原子设计。

原子设计是一种方法论，由原子、分子、组织、模板和页面共同协作以创造出更有效的用户界面系统的一种设计方法。

原子：设计系统的最小单位。原子包括调色板，分割线，字体，单个元素（即标题，段落，按钮等）这些元素有个特点就是最小元素不可再切割
https://image.uisdc.com/wp-content/uploads/2020/04/uisdc-zj-20200408-2.jpg

分子：通过多个原子组合而成，作为一个单元组成 UI 元素的一个组件
https://image.uisdc.com/wp-content/uploads/2020/04/uisdc-zj-20200408-8.jpg

组织：通过多个分子组合而成，我们可以构建更复杂但可重复的组织。组织是组成模板的主要组成部分
https://image.uisdc.com/wp-content/uploads/2020/04/uisdc-zj-20200408-7.jpg

模板：通过多个组织组合而成。是页面的基础架构。将以上元素进行排版，将原子，分子，组织进行排版成最终的模板
https://image.uisdc.com/wp-content/uploads/2020/04/uisdc-zj-20200408-4.jpg

页面：将实际内容（图片、文章等）套件在特定模板，每个页面由将实际内容（图片、文章等）模板修改而成
https://image.uisdc.com/wp-content/uploads/2020/04/uisdc-zj-20200408-3.jpg

### 搭建组件化
整理目录、制定规范模板、填充目录内容、生成组件库、sketch插件库和开发服务平台
- 整理目录：将线上的产品的组件进行梳理并做分组，形成一个组件目录
- 制定规范模板：以一个典型的组件为例，制定组件内容规范模板。里面包含组件的定义、组件的几种类型、组件的标注、组件的交互规范和组件的极限情况等
- 填充目录内容：按照制定的规范模板，将每个组件的内容进行填充完成，形成一套完整的设计规范
- 生成组件库：将设计规范里面的组件样式单独抽离出来，生成完整的组件库
- sketch 插件库：如果有前端支持，可以将 sketch 组件库开发成对应的插件，这样设计师可以更方便地使用和实时更新
- 开放服务平台：有前端开发资源支持的话，可以将组件进行代码封装。移动端或 PC 端都可以做组件开发平台

### 参考
https://tech.meituan.com/2020/11/26/consistency-in-ui-design.html
https://tech.meituan.com/2020/05/21/waimai-sketch-plugin.html
https://tech.meituan.com/2020/11/05/native-web-pratice-in-meituan.html