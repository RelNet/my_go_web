# Web接口

1表示交互提起方，2表示回应方

ws指的是使用ws通信，其余是普通http请求响应

## User Protocol

| 行为             | 客户端                                                       | 服务端                                                       |
| ---------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 注册             | 1 http post：user/signup，body：{name:string, password:string} | 2 json:成功{status:string(success), id:int}<br />失败{status:string(fail), msg:string} |
| 登陆             | 1http post: user/login, body: {id:int, password:string}      | 2 json成功{status:string(success)}<br />失败{status:string(fail), msg:string} |
| 更新用户名和密码 | 1 http post: user/update, body{id:int, name:string, password:string} | 2 json成功{status:string(success)}<br />失败{status:string(fail), msg:string} |
| 获取用户名       | 1 http post: user/getname, body:{id:int}                     | 2 json成功{status:string(success), name:string}<br />失败{status:string(fail), msg:string} |

## Rank Protocol

| 行为         | 客户端                              | 服务端                                                       |
| ------------ | ----------------------------------- | ------------------------------------------------------------ |
| 获取个人排行 | 1 http post:rank/get, body:{id:int} | 2 json成功{status:string, point:int}<br />失败{status:string, msg:string} |
| 获取前50名   | 1http get: rank/top50               | 2 json成功{status:string, top50:list[point:int, name:string]} |

## Game Protocol

| 行为                     | 客户端                                                       | 服务端                                                       |
| ------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 发起对局                 | 1{sponsor:int, inviter:int, behavior:ADD_GAME, (如果不分先，black、white一定要有), rule:{giver:bool, giverColor:int, giverNum:int, divideFirst:bool}} | 2成功{status:SUCCESS, id:int}<br />失败{status:FAIL, msg:string} |
| 服务器发起对局邀请       | 2{id:int, behavior:REFUSE}<br />{id:int, behavior:AGREE}     | 1{sponsor:int, inviter:int, behavior:INVITE, (如果不分先，black、white一定要有), rule:{giver:bool, giverColor:int, giverNum:int, divideFirst:bool}} |
| 申请结算                 | 1{id:int, behavior:SCORING}                                  | 2成功{status:SUCCESS}<br />失败{status:FAIL, msg:string}     |
| 服务器发起结算           | 2{behavior:REFUSE}<br />{behavior:AGREE}                     | 1{behavior:SCORING}                                          |
| 认输                     | 1{id:int, behavior:SURRENDER}                                | 2成功{status:SUCCESS}<br />失败{status:FAIL, msg:string}     |
| 服务器提交对方认输       |                                                              | 1{behavior:SURRENDER}                                        |
| 获取用户所有对局简要信息 | 1{sponsor:int, behavior:USER_ALL_GAME}                       | 2成功{status:SUCCESS, games:list}<br />失败{status:FAIL, msg:string} |

## Game Info Protocol

| 行为           | 客户端                                                       | 服务器                                                       |
| -------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 添加步         | 1{id:int, player:bool, stepNum:int, location:string, behavior:ADD_STEP} | 2成功{status:SUCCESS}<br />失败{status:FAIL, msg:string}     |
| 服务器发送新步 |                                                              | 1{player:bool, stepNum:int, location:string, behavior:ADD_STEP} |
| 悔棋           | 1{id:int,stepNum:int, behavior:DELETE_STEP}                  | 2成功{status:SUCCESS}<br />失败{status:FAIL, msg:string}     |
| 服务器发送悔棋 | 2{behavior:REFUSE}<br />{behavior:AGREE}                     | 1{behavior:DELETE_STEP}                                      |
| 获取所有对局步 | 1{id:int, behavior:GET_ALL_STEP}                             | 2成功{status:SUCCESS, steps:list}<br />失败{status:FAIL, msg:string} |

## Heartbeat Protocol

| 行为         | 客户端                                              | 服务器                                                 |
| ------------ | --------------------------------------------------- | ------------------------------------------------------ |
| 发送存活信息 | 1 http post:online/keep, body:{id:int, name:string} |                                                        |
| 获取在线用户 | 1 http get:online/getusers                          | 2json {status:string, users:list[id:int, name:string]} |