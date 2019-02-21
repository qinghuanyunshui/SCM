/**
 * 
 */
  $(function(){
	  // var choose="";//增加或修改
	   var isOK=true;
	   
	   //当选择截止日期之后 验证日期是否在当前日期之后
	   $(".alertDiv-add input[name=stopdate]").blur(function(){
		   var cdate =new Date( $(this).val());
		   var nowdate = new Date();
		   if(nowdate>cdate){
			   $(".alertDiv-add #mess").html("截至日期不能在当前日期之前");
			   isOK=false;
		   }else{
			   $(".alertDiv-add #mess").html("");
			   isOK=true;
		   }
		   
	   });
	   
	   
	   //只能搜  当输入商品时  触发keyup
	   $(".alertDiv-add input[name=goods]").keyup(function(){
		   $(".alertDiv-add #searchbox").show();
		   $(".alertDiv-add #searchbox ul").children("li").remove();
		   $.ajax({
			   url:'NeedServlet?flag=findByGoodsName',
			   type:"post",
			   async:true,
			   dataType:'json',
			   data:{"name":$(this).val()},
			   success:function(data){
				  
				   var appstr="";
				  var str =JSON.stringify(data);
			       var go=eval("("+str+")");
			       $.each(go,function(i,n){
			    	   appstr+="<li>"+n.name+"</li>";
				    });
			       $(".alertDiv-add #searchbox ul").append(appstr);
			       //当单击提示的信息时 填入文本框
				   $(".alertDiv-add #searchbox ul li").click(function(){
					   alert(1)
					   $(".alertDiv-add input[name=goods]").val($(this).html());
					   $(".alertDiv-add #searchbox").hide();
				   });
			    }
		   });
	   });
	   
	  
	  //当填写完商品时 验证是否为存在的商品 ：已经审核通过的商品才可以
	   $(".alertDiv-add input[name=goods]").blur(function(){
		   $(".alertDiv-add #searchbox").hide();
		   $.ajax({
			    url:"NeedServlet?flag=findByFullGoodsName",
			    type:"post",
			    async:true,
			    data:{"name":$(this).val()},
			    success:function(data){
			    	$(".alertDiv-add #mess").html(data);
			    	if(data!="")
			    	  isOK=false;
			    	else
			    		isOK=true;
			    }
			    });
	   });
	   //当添加表单提交时
	   $(".alertDiv-add").submit(function(){
		   //choose="add";
		   return isOK;
	   });
	   
	    $(".btn-edit").click(function(){
	    	choose='edit';
	    	if($(this).parent().prev("td").html()=='未审核'){//判断已经审核过的不能点
	    		window.location.href="NeedServlet?flag=yupdate&id="+$(this).attr('id');
	    	}
	   });
	   

	    
	    //删除多个选取的复选框
		$(".btn-delete").click(function(){
			var checks = $("input[name=check]");
			var str="";
			 $.each(checks,function(index,value){
				 if(value.checked && index!=0){
					str+=$(this).attr('id').substr(1)+',';
				 }
			});
			 location.href='NeedServlet?flag=deledeSome&ids='+str+'&isCheck='+$("#isCheck").val();
		});
		
		//单击审查按钮
		$(".btn-check").click(function(){
			if($(this).parent().prev("td").html()=='未审核'){//判断已经审核过的不能点
				var res =confirm("是否审核通过？");
				var id=$(this).attr("id");
	    		window.location.href='NeedServlet?flag=check&res='+res+'&id='+id+'&isCheck=true';
	    	}
		});
		
	 
   });