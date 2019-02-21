/**
 * 
 */
$(function(){
	  var isOK=true;
	 //--等会写--当选完图片后  发票div填入图片
	  //单击采购状态单选按钮 选择采购成功或失败
	  $(".alertDiv-add input[name=st]").click(function(){
		  if($(this).val()=='yes'){
			  $(".alertDiv-add").css("height","570px");
			  $(".alertDiv-add  #upbill_box").show();
		  }else{
			  $(".alertDiv-add").css("height","470px");
			  $(".alertDiv-add  #upbill_box").hide();
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
			 location.href='PurchaseServlet?flag=deledeSome&ids='+str+'&oper='+$("#oper").val();
		});
	//单击审查按钮
		$(".btn-check").click(function(){
			if($(this).parent().prev("td").html()=='未审核'){//判断已经审核过的不能点
				var res =confirm("是否审核通过？");
				var id=$(this).attr("id");
	    		window.location.href='PurchaseServlet?flag=check&res='+res+'&id='+id+'&oper='+$("#oper").val();
	    	}
			if($(this).parent().prev("td").html()=='请款中-已提交报价单'){
				var res =confirm("是否确定此请款？");
				var id=$(this).attr("id");
	    		window.location.href='PurchaseServlet?flag=giveMoney&res='+res+'&id='+id+'&oper='+$("#oper").val();
	    	}
		});
	  
		//编辑
	/*	 $(".btn-edit").click(function(){
			// alert(1)
		    	choose='edit';
		    	if($(this).parent().prev("td").html()=='未审核'){//判断已经审核过的不能点
		    		window.location.href="PurchaseServlet?flag=yupdate&id="+$(this).attr('id');
		    	}
		});*/
	  
	  //当鼠标移入 显示详细信息
		$("table tr").mouseover(function(){
			var y=$(this).offset().top;
	        var x=$(this).offset().left;
	        $(".tablebox .see").css("top",y-120+"px").css("left",x+100+"px");
	        $(".tablebox .see").fadeIn(500);
			 $(".see ul").children("li").remove();
			 var id=$(this).children().eq(1).html();
				if($(this).index()==0){
					$(".tablebox .see").css("display","none");
					$(".see").hide();
				}else{
					 $.ajax({
			        	    url:"PurchaseServlet?flag=see",
			  			    type:"post",
			  			    async:true,
			  			    dataType:'json',
			  			    data:{"id":isNaN(Number(id))?0:id},
			  			    success:function(data){
			  			    	 $(".see ul").children().empty();
			  			    	if(data=='nodata'){
			  			    		 $(".see").hide();
			  			    	}else{
			  			    		var str =JSON.stringify(data);
				  			    	var pur=eval("("+str+")");
				  			    	
				  			    	$(".see ul").append("<li><div>商品名称：</div>"+pur.good.name+"</li>" +
				  			    			"<li><div>供应商：</div>"+pur.good.sup.name+"</li>" +
				  			    			"<li><div>单价：</div>"+pur.good.price+"</li>" +
				  			    			"<li><div>数量：</div>"+pur.num+"</li>" +
				  			    			"<li><div>请款金额：</div>"+pur.price+"</li>" +
				  			    			    "<li><div>日期：</div>"+pur.purdate+"</li>" +
				  			    			    "<li><div>用途：</div>"+pur.demo+"</li>" +
				  			    			    "<li><div>状态：</div>"+pur.status+"</li>" +
				  			    			    "<li style='border-bottom:none;'><div>发票：</div>"+pur.bills+"</li>");

			  			    	}
			  			    }
	       
	        });}
		}).mouseout(function(){
			$(".see ul").children("li").remove();
			 $(".tablebox .see").hide();
		});
	  //智能搜索商品
	$(".alertDiv-add input[name=goods]").keyup(function(){
		 $(".alertDiv-add #searchbox").show();
		   $(".alertDiv-add #searchbox ul").children("li").remove();
		   $.ajax({
			   url:'PurchaseServlet?flag=findByGoodsName',
			   type:"post",
			   async:true,
			   dataType:'json',
			   data:{"name":$(this).val()},
			   success:function(data){
						  var appstr="";
						  var str =JSON.stringify(data);
					       var pur=eval("("+str+")");
					       $.each(pur,function(i,n){
					    	   appstr+="<li>"+n.name+"</li>";
						    });
					       $(".alertDiv-add #searchbox ul").append(appstr);
					       //当单击提示的信息时 填入文本框
						   $(".alertDiv-add #searchbox ul li").click(function(){
							   $(".alertDiv-add input[name=goods]").val($(this).html());
							   $(".alertDiv-add #searchbox").hide();
						   });
				
			    }
		   });
	});
	
	 //当填写完商品时 验证是否为存在的符合条件的商品 
	   $(".alertDiv-add input[name=goods]").blur(function(){
		   $(".alertDiv-add #searchbox").hide();
		   $.ajax({
			    url:"PurchaseServlet?flag=findByFullGoodsName",
			    type:"post",
			    dataType:"json",
			    data:{"name":$(this).val()},
			    success:function(data){
			    	if(data=='nodata'){
			    		$(".alertDiv-add #mess").html("暂无此商品或此商品还未审核");
			    		isOK=false;
			    	}else{
			    		$(".alertDiv-add #mess").html("");
			    		$(".alertDiv-add #goods_sup").html(data.sup.name);
			    		$(".alertDiv-add #goods_pice").html(data.price);
			    		isOK=true;
			    	}
			    }
			    });
	   });
	   //当添加表单提交时
	   $(".alertDiv-add").submit(function(){
		   //choose="add";
		   return isOK;
	   });
	   

});
