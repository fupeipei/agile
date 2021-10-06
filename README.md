[![coverage report](https://gitlab.sdtdev.net/dcs/parts-stocking-warehouse/badges/develop/coverage.svg?job=unit_test)](https://gitlab.sdtdev.net/dcs/parts-stocking-warehouse/commits/develop?job=unit_test)
## 1、aigle代码结构如下：
```
.
devops_agile
├── JenkinsPipeLine.groovy
├── README.md
├── agile-clent
│   ├── agile-clent.iml
│   ├── pom.xml
│   ├── src
│   └── target
├── agile-server
│   ├── agile-server.iml
│   ├── db-check
│   ├── db-parser
│   ├── pom.xml
│   └── target
├── agile-model
│   ├── agile-model.iml
│   ├── pom.xml
│   ├── src
│   └── target
├── docker
│   ├── Dockerfile_agile
│   └── docker-compose-jenkins.yaml
├── docker-compose.yaml
├── logs
│   ├── agile-server.log
├── pom.xml
├── settings.xml
├── target
│   └── generated-sources
└── devops_cide.iml

```
## 2、JenkinsPipeLine.groovy 实现Jenkins Job通过Pipeline流水线读取文件进行构建和部署。
## 3、Gitlab Pipeline cicd，配置.gitlab-ci.yml文件，通过gitlab-runner拉取容器调用.gitlab-ci.yml文件中的stages阶段进行构建。
## 4、在./docker/agent/README文件中，讲解了JENKINS Agent节点的Docker容器 集群构建方式。11
##6