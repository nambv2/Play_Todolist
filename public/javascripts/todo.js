/**
 * 
 */
//reload page
$(document).ready( function () {
	$("input:checkbox").removeAttr("checked");
	$(window).load(function(){
	
		$.ajax({
			type:"GET",
			url:"",
			dataType:"json",
			success:function(lstTodo){
				$.each(lstTodo,function(key,object){
					var output = "<li><div class='view' id="+object.id+">"+
				      "<input class='toggle' type='checkbox'>"+
				      "<label>"+object.name+"</label>"+
				      "<a class='destroy'></a>"+
				    "</div></li>";
					$("input:checkbox").removeAttr('checked');
	            	$("ul").append(output);
	            	$("#new-todo").val("");
				});
			}
		});
	});
	//enter to do list
	$("body").on("keypress","#new-todo", function (e) {
		if(e.which == 13){
			
			console.log("enter");
			var input = $("#new-todo").val();
			console.log(input);
			$.ajax({
				type: "POST",
				data: {"param" : input},
				url: "newTask",
				success: function(key) {	
					var output = "<li><div class='view' id="+key+">"+
				      "<input class='toggle' type='checkbox'>"+
				      "<label>"+input+"</label>"+
				      "<a class='destroy'></a>"+
				    "</div></li>";
					var countItem = "<b id=count>"+key+" items</b>";
					console.log("Key is : "+key)
					$("ul").append(output);
					$("#count").remove();
					$("#todo-count").append(countItem);
					$("#new-todo").val("");
					
				}
			});
		}
	});	
	
	$("input[type=checkbox]").on("click",function(){
		if ($("#toggle-all").is(":checked"))
        {
          $("input:checkbox").not(this).prop("checked", this.checked);
          
        } else {
        	$('input:checkbox').removeAttr('checked');
        }

	});

	//mark all
	$("body").on("click","input[type=checkbox]",function(){
		var n = $( "input:checked" ).length;
		if($("#toggle-all").is(":checked")){
			n=n-1;
		}
		var itemDel = "<b id=itemDel>Clear "+n+" completed</b>";
		$("#itemDel").remove();
		$("#clear-completed").append(itemDel);
	});
	
	//event delete all
	
	$("body").on("click","#itemDel",function(){
		var selected = [];
		if($("#toggle-all").is(":checked")){
			selected = ["-1"];
			console.log($(this).attr("id"));
			$.ajax({
				type:"GET",
				data:{"deleteItem":selected},
				url:"LoadDataServlet",
				success:function(){
					console.log("Delete all");
					$("input:checkbox").removeAttr("checked");
					$("li").remove();
					$("#count").remove();
					$("#itemDel").remove();
					$("#todo-list").remove();
					$("footer").remove();
					$("#main").remove();

				}
			});
		} else {
			var selected = [];
			$('input:checked').each(function() {
				console.log("this is id:"+$(this).parent().attr('id'));
			    selected.push($(this).parent().attr('id'));
			});
			$.ajax({
				type:"GET",
				data:{"deleteItem":selected},
				url:"LoadDataServlet",
				success:function(key){
					var countItem = "<b id=count>"+key+" items</b>";
					$("#count").remove();
					$("#todo-count").append(countItem);
					$("#new-todo").val("");
					$('input:checked').each(function() {
						var idTag = $(this).parent().attr("id");
					    $($("#"+idTag).parent()).remove();
					});
				}
			});
		}
	});
	
	
	//delete items
	$("body").on("click",".destroy",function(){
		var itemParent = $(this).parent();
		var valueItem = "";
		var item = $(itemParent).attr("id");
		console.log("id is: "+item);
		$.ajax({
			type:"POST",
			data:{"itemChoose":item,"param":valueItem},
			url:"LoadDataServlet",
			success:function(key){
				var item1 = $("#"+item).parent();
				console.log(item1);
				$("#"+item).remove();
				var countItem = "<b id=count>"+key+" items</b>";
				$("#count").remove();
				$("#todo-count").append(countItem);
				$("#new-todo").val("");
				$(item1).remove();
			}
		});
	});
	
	//Edit items
	$(function(){
		$("body").on("dblclick",".view",function(e){
			//e.stopPropagation();
			var currentEle = $(this);
			console.log("Element is:"+$(this).html());
			var idEdit = $(this).attr("id");
			console.log("The parent Tag have id is"+idEdit+" is: "+$("#"+idEdit).parent());
			$.ajax({
				type:"POST",
				data:{"param":"","itemChoose":"","idEdit":idEdit},
				url:"LoadDataServlet",
				success:function(value){
					editable(currentEle,value,idEdit);
				}
			});
			
		});
	});
	function editable(currentEle,value,idEdit){
		$(currentEle).html("<input class='edit' type = 'text' value = '"+value+"'></input>");
		$(".edit").focus();
		$(".edit").keyup(function(event){
			if(event.keyCode == 13){
				var newValue = $(".edit").val().trim();
				$.ajax({
					type:"POST",
					data:{"param":"","itemChoose":"","idEdit":idEdit,"newValue":newValue},
					url:"LoadDataServlet",
					success:function(){
						$(currentEle).html("<input class='toggle' type='checkbox'>"+
								"<label>"+newValue+"</label>"+
							      "<a class='destroy'></a>");
					}
				});
			}
		});
		$("body").on("click",".view",function(){
			$(currentEle).html("<input class='edit' type = 'text' value = '"+value+"'></input>");
			$(".edit").focus();
			var newValue = $(".edit").val().trim();
			$.ajax({
				type:"POST",
				data:{"param":"","itemChoose":"","idEdit":idEdit,"newValue":newValue},
				url:"LoadDataServlet",
				success:function(){
					$(currentEle).html("<input class='toggle' type='checkbox'>"+
							"<label>"+newValue+"</label>"+
						      "<a class='destroy'></a>");
				}
			});
		});
		
	}
});