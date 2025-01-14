# 网关

接收所有外界请求,并中转为其他应用或服务

## http

http请求通过[top.uhyils.usher.protocol.controller.AllController.action](src/main/java/top/uhyils/usher/protocol/controller/AllController.java)
方法来进行统一调用
http上传与下载文件通过[top.uhyils.usher.protocol.controller.FileController](src/main/java/top/uhyils/usher/protocol/controller/FileController.java)
方法来进行统一上传与下载

## rpc

目前仅支持usher-rpc
具体见泛化调用[RpcApiUtil.java](..%2F..%2F..%2Fusher-common%2Fusher-common-rpc%2Fusher-common-rpc-spring-start%2Fsrc%2Fmain%2Fjava%2Ftop%2Fuhyils%2Fusher%2Frpc%2Fspring%2Futil%2FRpcApiUtil.java)

## callNode框架整理

### http入口: [GatewayHttpController.java](src%2Fmain%2Fjava%2Ftop%2Fuhyils%2Fusher%2Fprotocol%2Fcontroller%2FGatewayHttpController.java)

### rpc入口: [GatewayRpcProviderImpl.java](src%2Fmain%2Fjava%2Ftop%2Fuhyils%2Fusher%2Fprotocol%2Frpc%2Fimpl%2FGatewayRpcProviderImpl.java)

### mysql入口: [MysqlNettyServer.java](src%2Fmain%2Fjava%2Ftop%2Fuhyils%2Fusher%2Fprotocol%2Fmysql%2Fnetty%2FMysqlNettyServer.java)

