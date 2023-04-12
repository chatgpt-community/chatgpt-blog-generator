# 使用ChatGPT生成100个话题

### 请求chatGPT创造100个话题

输入
```text
请帮我创作一篇软文，内容需要达到如下要求：
* 社区背景和定位： ChatGPT共创变现社区
* 优势和特点： 丰富的教程、学习简单、容易上手、投入成本低收益高
* 目标受众： 希望能力变现，挣钱的人
* 内容要求： 参考流行软文和网络关键词、鼓励大家加入星球
* SEO高：能被大量的搜索到
```
输出
* [话题输出，拷贝到text.txt](topics.txt)

### 生成一百个话题

输入
```text
你是一名行业分析员，请帮我罗列当下最受欢迎的100个相关话题，话题请入如下格式
`作为一名<XXX>,  如何用ChatGPT帮助实现能力变现`

举例：
作为一名短视频行业工作者，如何用ChatGPT帮助实现能力变现

```


### 将以上100个话题转化为 `VUEPRESS` 博客结构
输入
```text
please help generate a java application class which support below function.
 
input:  file contains list string
output:   folder which name is index of input string list,   a markdown file "README.MD" and title `#{string}` in input list

example

input:
file name: "text.txt" 
file content: 
AAA
BBB

output

file: 1/README.MD
content:
#AAA

file: 2/README.MD
content:
#BBB
```

产出

见代码 `org.cgc.mywriter.StringListToFolderConverter`

### 将话题列表转为`VuePress`Config文件格式

输入
```text
please help generate a java application class which support below function.
 
input:  file contains list string
output:   file contains another list string, content include the list string index

example
input:
file name: "text.txt" 
file content: 
AAA
BBB

output
file: gpt/result
content:
'/saidao/1/',
'/saidao/2/', 

```

输出

见代码 `org.cgc.mywriter.StringIndexer`


### 问题

当有100个readme目录和文档时，vuepress的加载速度巨慢！、

* 解决思路： 把所有内容写入一个markdown文件当中