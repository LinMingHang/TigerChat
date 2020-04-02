# 腾讯云配置

公网IP: 193.112.213.xxx

私网IP: 172.16.0.8

# 搭建 fastDFS、Nginx

## 1. 环境准备

|         名称         |             说明              |
| :------------------: | :---------------------------: |
|        centos        |              7.x              |
|    libfatscommon     | FastDFS分离出的一些公用函数包 |
|       FastDFS        |          FastDFS本体          |
| fastdfs-nginx-module |   FastDFS和nginx的关联模块    |
|        nginx         |          nginx1.15.4          |

## 2. 编译环境

```shell
yum install git gcc gcc-c++ make automake autoconf libtool pcre pcre-devel zlib zlib-devel openssl-devel wget vim -y
```

## 3. 磁盘目录

|                  说明                  |      位置      |
| :------------------------------------: | :------------: |
|               所有安装包               | /usr/local/src |
|              数据存储位置              |   /home/dfs/   |
| #这里我为了方便把日志什么的都放到了dfs |                |

```shell
# 创建数据存储目录
mkdir /home/dfs 
# 切换到安装目录准备下载安装包
cd /usr/local/src
```

## 4. 安装 libfatscommon

```shell
git clone https://github.com/happyfish100/libfastcommon.git --depth 1
cd libfastcommon/
#编译安装
./make.sh && ./make.sh install
```

## 5. 安装 FastDFS

```shell
#返回上一级目录
cd ../
git clone https://github.com/happyfish100/fastdfs.git --depth 1
cd fastdfs/
#编译安装
./make.sh && ./make.sh install
#配置文件准备
cp /etc/fdfs/tracker.conf.sample /etc/fdfs/tracker.conf
cp /etc/fdfs/storage.conf.sample /etc/fdfs/storage.conf
cp /etc/fdfs/client.conf.sample /etc/fdfs/client.conf #客户端文件，测试用
cp /usr/local/src/fastdfs/conf/http.conf /etc/fdfs/ #供nginx访问使用
cp /usr/local/src/fastdfs/conf/mime.types /etc/fdfs/ #供nginx访问使用
```

## 6. 安装 fastdfs-nginx-module

```shell
#返回上一级目录
cd ../
git clone https://github.com/happyfish100/fastdfs-nginx-module.git --depth 1
cp /usr/local/src/fastdfs-nginx-module/src/mod_fastdfs.conf /etc/fdfs
```

## 7. 安装 nginx

```shell
#下载nginx压缩包
wget http://nginx.org/download/nginx-1.15.4.tar.gz 
#解压
tar -zxvf nginx-1.15.4.tar.gz 
cd nginx-1.15.4/
#添加fastdfs-nginx-module模块
./configure --add-module=/usr/local/src/fastdfs-nginx-module/src/ 
#编译安装
make && make install 
```

## 单机部署

### tracker 配置

```shell
vim /etc/fdfs/tracker.conf
#需要修改的内容如下
port=22122  # tracker服务器端口（默认22122,一般不修改）
base_path=/home/dfs  # 存储日志和数据的根目录

/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf
/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf restart
```

### storage 配置

```shell
vim storage.conf
base_path = /home/dfs
group_name = tigerChat
store_path0 = /home/dfs
tracker_server = 193.112.213.xxx:22122

/usr/bin/fdfs_storaged /etc/fdfs/storage.conf
/usr/bin/fdfs_storaged /etc/fdfs/storage.conf restart
```

### client 测试

```shell
vim /etc/fdfs/client.conf
#需要修改的内容如下
base_path=/home/dfs
tracker_server = 193.112.213.xxx:22122  #tracker服务器IP和端口
#保存后测试,返回ID表示成功 如：group1/M00/00/00/xx.tar.gz
fdfs_upload_file /etc/fdfs/client.conf /usr/local/src/nginx-1.15.4.tar.gz
```

### 配置 nginx 访问

```shell
vim /etc/fdfs/mod_fastdfs.conf

#需要修改的内容如下
tracker_server=193.112.213.xxx:22122  #tracker服务器IP和端口
url_have_group_name=true
store_path0=/home/dfs
group_name=tigerChat

#配置nginx.config
vim /usr/local/nginx/conf/nginx.conf
#添加如下配置
    server {
        listen       8888;
        server_name  193.112.213.xxx,172.16.0.8;

        location /tigerChat/M00 {
             ngx_fastdfs_module;
        }
    }
    
cd /usr/local/nginx/sbin
./nginx

#测试下载,用外部浏览器访问刚才已传过的nginx安装包,引用返回的ID
http://193.112.213.xxx:8888/tigerChat/M00/00/00/wKgAQ1pysxmAaqhAAA76tz-dVgg.tar.gz
#弹出下载单机部署全部跑通
```

