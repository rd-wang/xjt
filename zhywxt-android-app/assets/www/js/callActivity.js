/**
 * callback      �ɹ��ص�
 * failCallBack  ʧ�ܻص�
 * CallActivityPlugin  ���ƣ�config.xml�����ã�
 * call          ���ֲ���
 * params        ���ò���(json��ʽ)
 */
window.callActivityPlugin = function(params,callback) {  
	//var params={activityName:"com.catsic.action.DemoActivity",data:{name:'zhangsan'}};
    cordova.exec(callback, failCallBack, "CallActivityPlugin", "call", [ params ]);  
};  
  
var failCallBack = function(message) {  
    alert("failed>>" + message);
}  
