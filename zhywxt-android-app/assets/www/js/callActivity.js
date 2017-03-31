/**
 * callback      成功回调
 * failCallBack  失败回调
 * CallActivityPlugin  名称（config.xml中配置）
 * call          区分操作
 * params        调用参数(json格式)
 */
window.callActivityPlugin = function(params,callback) {  
	//var params={activityName:"com.catsic.action.DemoActivity",data:{name:'zhangsan'}};
    cordova.exec(callback, failCallBack, "CallActivityPlugin", "call", [ params ]);  
};  
  
var failCallBack = function(message) {  
    alert("failed>>" + message);
}  
