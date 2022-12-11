# keysGen

## 接口
1. 获取特定bot的keys和敏感词(num为bot编号)
http://localhost:8080/keys?num=1
入参：num 为bot编号
返回报文
{
    "keys": "dsfafsfasfasf",
    "words": "啊啊,遍布,尺寸,单独"
}

2. 修改特定bot的keys
http://localhost:8080/genKeys?num=1&keys=xxxxx&token=xxxxx
