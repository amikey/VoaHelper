<!DOCTYPE html>
<html>

<meta charset="utf-8">

<head></head>
 <!-- 必须加上 项目名称， -->
  <!-- <script type="text/javascript" src="/VOABack/resources/jquery.js"></script>   -->
  <script type="text/javascript" src="/VOABack/resources/zepto.min.js"></script>  
            <script type="text/javascript">  
            
                $(function() {  
                    $("#click").click(function() {  
                    	alert("Hello World");
                    	$.ajax( {  
                            type : "GET",  
                            url : "http://192.168.1.102:8080/VOABack/listening/views", 
							data:{
									"starter":100,
									"space":5
							},
                            cache: false,
                            timeout: 1000000,
                            dataType: "json",  
                            success : function(data) {
                            	alert(data[0].title);
                            	$.each(data, function(i, item) {
                            		
                                     $("#info").append(
                                            "<div>" + item.title + "</div>" + 
                                            "<div>" + item.content    + "</div>" +
                                            "<div>" + item.mp3 + "</div><hr/>"); 
                                });
                            },  
                            error: function() {
                                alert("TimeOut");
                            }  
                        });  
                    });  
                });  
   	</script>  
<body>


 <input id="click" type="button" value="click to show person" />
<!-- <input id="info" type="textarea" /> -->
<div id="info"></div>

 
</body>
</html>
