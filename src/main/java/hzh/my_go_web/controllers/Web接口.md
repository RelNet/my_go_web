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
| 发起对局                 | 1 http post, body:{sponsor:int, inviter:int, black:int, white:int, giver:bool, giverColor:int, giverNum:int, divideFirst:bool} | 2 ws json{status:string(success, id:int}<br />{status:string(fail), msg:string} |
| 服务器发起对局邀请       | 2 http post: game/refuseaddgame, body:{sponsor: Int}<br />http post: game/agreeaddgame, body:{sponsor: Int, inviter: Int, black: Int, white: Int} | 1ws {sponsor:int, inviter:int, behavior:invite, black:int, white:int, giver:bool, giverColor:int, giverNum:int, divideFirst:boo}} |
| 申请结算                 | 1http post:game/score, body{to: Int}                         | 2 ws json成功{status:SUCCESS，winner:int}<br />失败{status:FAIL, msg:string} |
| 服务器发起结算           | 2 http post:game/refusescore, body:{to:Int}<br />http post:game/agreescore, body{gameId:int, to:int} | 1{behavior:socre}                                            |
| 认输                     | 1{id:int, behavior:SURRENDER}                                | 2成功{status:SUCCESS}<br />失败{status:FAIL, msg:string}     |
| 服务器提交对方认输       |                                                              | 1{behavior:SURRENDER}                                        |
| 获取用户所有对局简要信息 | 1 http get:game/getgames, body{userId:int}                   | 2 gamelist                                                   |

## Game Info Protocol

| 行为           | 客户端                                                       | 服务器                                                       |
| -------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 添加步         | 1 http post:gameinfo/addstep, body:{gameId:int, player:int, stepNum:int, location:string, to:int} | 2成功{status:SUCCESS}<br />失败{status:FAIL, msg:string}     |
| 服务器发送新步 |                                                              | 1{stepNum:int, location:string, behavior:add_step}           |
| 悔棋           | 1{id:int,stepNum:int, behavior:DELETE_STEP}                  | 2成功{status:SUCCESS}<br />失败{status:FAIL}                 |
| 服务器发送悔棋 | 2{behavior:REFUSE}<br />{behavior:AGREE}                     | 1{behavior:DELETE_STEP}                                      |
| 获取所有对局步 | 1{id:int, behavior:GET_ALL_STEP}                             | 2成功{status:SUCCESS, steps:list}<br />失败{status:FAIL, msg:string} |

## Heartbeat Protocol

| 行为         | 客户端                                              | 服务器                                                 |
| ------------ | --------------------------------------------------- | ------------------------------------------------------ |
| 发送存活信息 | 1 http post:online/keep, body:{id:int, name:string} |                                                        |
| 获取在线用户 | 1 http get:online/getusers                          | 2json {status:string, users:list[id:int, name:string]} |