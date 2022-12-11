# keysGen

## 接口
1. 获取特定bot的keys和敏感词

    http://localhost:8080/keys?num=1

    入参：num 为bot编号

    返回报文
    
    {
    
        "keys": "dsfafsfasfasf",
        
        "words": "啊啊,遍布,尺寸,单独"
    }
    
    

2. 修改特定bot的keys（keys过长建议使用POST提交）

    http://localhost:8080/genKeys?num=1&keys=xxxxx&token=xxxxx
    
    入参：num为bot编号，keys为新的值（多个key逗号分隔），token为访问密钥（需要修改IndexController TOKEN参数）
    
    返回报文
    
    成功：“修改成功”
    
3. 追加敏感词（敏感词过长建议使用POST提交）

    http://localhost:8080/appendWords?words=xxxxx&token=xxxxxx

    入参：num为bot编号，words为新追加的敏感词（多个敏感词逗号分隔），token为访问密钥（需要修改IndexController TOKEN参数）
    
    返回报文
    
    成功：“修改成功”
    



## 部署方式
可使用docker容器部署，确保安装docker

### 部署准备

1. 请先将代码打包（mvn clean package）

2. 将以下文件上传到服务器（演示路径为/root/keysGen 目录，可先创建）

    Dockerfile  -- docker文件
    
    keysGen-1.0-SNAPSHOT.jar -- 项目jar文件
    
    keys.txt -- keys初始json文件（默认支持最大20个bot）
    
    words.txt -- 敏感词文件
    



### docker部署命令
1. 打包镜像

    docker build -t keysgen:1.0 .
    
2. 查看是否有占用

    docker ps -a
    
3. 启动容器（包含ENV_KEYS_URL/ENV_WORDS_URL变量和宿主机文件映射）

    docker run --name keysgen -p 8080:8080 -e ENV_KEYS_URL="/data/keys.txt"  -e ENV_WORDS_URL="/data/words.txt" -v /root/keysGen:/data -d keysgen:1.0

4. 重新打包

    停用服务  docker stop xxxx
    
    删除旧镜像  docker rm xxxx
    
    再打包启动  执行1-3
    
5. 查看日志

    dockr logs xxxx
