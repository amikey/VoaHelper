
$(".xu-back").on('tap',function(e){

//	alert("长度--->"+window.history.length);

	if (window.history.length > 1) {
//		alert("大于一");
				window.history.back();	
		}
});
//导致love依赖amazeui的js
function love(type,title,userName){

	var flag = 1;
	if($('#collect span').hasClass('am-icon-star-o')){
							//ajax 收藏
			$('#collect  span').removeClass('am-icon-star-o') ;
			$('#collect  span').addClass('am-icon-star am-text-primary');
			flag = 1;
	}else{
						//ajax取消收藏
			$('#collect  span').removeClass('am-icon-star am-text-primary');
			$('#collect  span').addClass('am-icon-star-o') ;
			flag=0;
	}
	//远程请求
			$.ajax( {  
						
						  type : "GET",  
                          url : "http://192.168.1.113:8080/VOABack/Collect/love",  
                           data :{
								"type":type,
								"title":title,
								"user":userName,
								"flag":flag
                            },
                            cache: false,
                            timeout: 1000,
//                          dataType: "json",  
                            success : function(data) {

                           		alert(data);
                           	},
                             error: function(ex) {
                             	//对页面进行处理
                             	//只需要显示  网络无法连接，
                             	//心形的显示方式
                             	if(flag===1){
             $('#collect  span').removeClass('am-icon-star am-text-primary');
			$('#collect  span').addClass('am-icon-star-o') ;
                             	}else{
                             		$('#collect  span').removeClass('am-icon-star-o') ;
			$('#collect  span').addClass('am-icon-star am-text-primary');
                             	}
                                alert("操作失败,网络链接异常%>_<%"+ex);
                            }  
                        });		
};
