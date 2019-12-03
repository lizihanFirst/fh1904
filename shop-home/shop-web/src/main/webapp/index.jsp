<%--
  Created by IntelliJ IDEA.
  User: lenovo1
  Date: 2019/11/20
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
<link rel="stylesheet" type="text/css" href="/js/layui/assets/css/layui.css">
<link rel="stylesheet" type="text/css" href="/js/layui/assets/css/view.css">
<link rel="stylesheet" type="text/css" href="/js/layui/layer/mobile/need/layer.css">
<link rel="stylesheet" type="text/css" href="/js/layui/assets/css/login.css">

<style>
    .end
    {
        border-style: solid;
        border-color: red
    }
    .ss{
        color: red;
    }
</style>
</head>
<body>



<!--左侧菜单-->
<div>
<div class="sec-mainL left">
    <div class="mainL-hd-box">
        <h2 class="mainL-hd"><a href="#">购物返彩贝商家</a></h2>
    </div>

    <!--菜单列表-->
    <ul class="sec-mainNav" id="menuId">

    </ul>
</div>

<div  style="width: 1200px;float: left;padding-left: 20px">
    <div style="float: right;margin-bottom: 25px">
    <span class="layui-breadcrumb" lay-separator="-">
  <a href="" >首页</a>
  <a href="#" onclick="openLogin()">登录</a>
  <a href="">亚太地区</a>
  <a><cite>正文</cite></a>
</span></div>
        <div id="centre" style="height: 140px;width: 500px">

        </div>
    <div id="showProduct">

    </div>
</div>

</div>
<div id="live2d-widget"><canvas id="live2dcanvas" width="300" height="600" style="position: fixed; opacity: 0.7; left: 0px; bottom: 0px; z-index: 99999; pointer-events: none;"></canvas></div>
<script type="text/javascript" src="/js/jquery-3.3.1.js"></script>
<script src="/js/layui/assets/layui.all.js"></script>
<script type="text/javascript" src="/js/layui/assets/layui.js"></script>
<script type="text/javascript" src="/js/layui/layer/mobile/layer.js"></script>
<div id="loginId" style="display: none">
   手机号:<input type="text" id="phone"><br>
    验证码:<input type="text" id="yaZhengId" width="30"><button onclick="getYanZhengMa()">获取验证码</button>
</div>
<script type="text/javascript">
    $(function() {
    initClassifyAll()
    });
    //查询所有类型
    var data;
    function initClassifyAll() {
        $.ajax({
            url:"http://localhost:8091/classify",
            type:"get",
            dataType:"json",
            success:function (result) {
                console.info(result)
                 data=result.data;
                var str="";
                for(var i=0;i<data.length;i++){
                    str+="<li>";
                    str+="<h3>"+data[i].classifyName+"</h3>";
                    if(data[i].classifyVolist.length>0){
                        str+=" <div class='menu-tab'>";
                        for(var j=0;j<data[i].classifyVolist.length;j++){
                            if(j<3){
                                str+=" <a href='#' value='"+data[i].classifyVolist[j].id+"' onclick='getProductByTypeId("+data[i].classifyVolist[j].id+",this,1)'>"+data[i].classifyVolist[j].classifyName+"</a>";
                            }
                        }
                        str+="<div class=\"fix\"></div>\n" +
                            "</div> <span class=\"menu-more\">更多</span>";
                        str+="<div class=\"menu-panel\" >\n" +
                            "<div class=\"menu-panel-hd\">";
                        str+="<h3>热门分类</h3><div class=\"sub-group\">";
                        for(var k=0;k<data[i].classifyVolist.length;k++){
                            str+=" <a href='#' vlaue='"+data[i].classifyVolist[k].id+"' onclick='getProductByTypeId("+data[i].classifyVolist[k].id+",this,1)'><span class='spens'>"+data[i].classifyVolist[k].classifyName+"</spen></a>";
                        }
                        str+="</div></div><div class=\"menu-panel-bd\"><ul>";
                        if(data[i].brandList.length>0){
                            for(var a=0;a<data[i].brandList.length;a++) {
                                str += "<li><a href='#' class='img1' value='"+data[i].brandList[a].id+"' onclick='selectBrand(this)' style=''><img src='https://lizihan.oss-cn-beijing.aliyuncs.com/" +data[i].brandList[a].brandPicture + "'/></a></li>";
                            }
                        }
                        /*for(var a=0;a<data[i].brandList.length;a++){
                            str+="<li><a href=''><img src='"+data[i].brandList+"'/></a></li>";
                        }*/
                        str+="</ul><div> <a href=\"\" class=\"menu-panel-btn\">\n" +
                            " <span>查看全部商家</span>\n" +
                            "<em></em>\n" +
                            " </a></div>";
                    }
                    str+="</li>";
                }
                $("#menuId").html(str);
                var $top = $('.sec-mainNav').offset().top + $('.sec-mainNav').height()
                //左侧导航动画
                $('.sec-mainNav li').on('mouseenter', function() {
                    var $height = $(this).offset().top + $(this).find('.menu-panel').outerHeight();
                    $(this).find('.menu-panel').show();
                    if($height - $top >= 0) {
                        $(this).find('.menu-panel').css({
                            top: -($height - $top) + 'px'
                        })
                    }
                });
                $('.sec-mainNav li').on('mouseleave', function() {
                    $(this).find('.menu-panel').hide();
                });
            }
        })
    }
    function openLogin(){
        layui.use(['upload','form'], function() {
            var form = layui.form;
            layer.open({
                type: 1,
                offset: 'auto',
                title: "登录",
                area: ['50%', '200px'],
                overflow: 'auto',
                scrollbar: true,
                content: $("#loginId").html(),
                btn: ['登录', '取消'],
                btnAlign: 'c',//对齐方式　r l c
                fixed:false,
                top:10,
                yes:function(index,layero){
                    var phone=$("#phone").val();
                    var  yaZhengId=$("#yaZhengId").val();
               $.ajax({
                        url:"http://localhost:8094/user/login",
                        type:"post",
                        data:{
                            phone:phone,
                            yaZhengId:yaZhengId
                        },
                        dataType:"json",
                        success:function (result) {
                            if(result.code==200){
                                layer.msg("登录成功")
                                layer.close(index);//为关闭
                            }
                        }
                    })

                },
                btn2:function(index,layero){
                    layer.close(index);//为关闭
                },
            });
            /* 渲染表单 */
            form.render();
        })
    }
    function getYanZhengMa() {
        var phone=$("#phone").val();
        $.ajax({
            url:"http://localhost:8094/user/getValidate",
            type:"post",
            data:{
                phone:phone
            },
            success:function (result) {
                if(result.code==200){
                    layer.msg("发送成功")
                }else {
                    layer.msg(result.msg)
                }
            }
        })
    }
    var str="";
    var typeId="";
    function getProductByTypeId(id,obj,type) {
        if(str!=""){
            var collection = $(".spens");
            $.each(collection, function () {
                $(this).css("color","")
            })
            $(obj).children().css("color","red")
        }
          // var v1 = $(obj).attr("value");
          // console.log($(obj).html())
           if(type==1){
               var jQuery = $(obj).parent().next().next();
               var a1 = jQuery.html();
               $("#centre").html(a1)
           }else {
               var a2 = $(obj).parent().parent().parent().html();
               $("#centre").html(a2)
           }
           var spens = $("#centre").children().children(".sub-group").find("a");
           spens.each(function(){
               str = $(this).children().html();
               if($(obj).html()==str){
                   $(this).children().css("color","red")
               }
           });
          typeId=id;
        findProductList()
    }
    function findProductList() {
        $("#showProduct").html("")
        layui.use(['table',"upload","form"], function () {
            var table = layui.table;
            var form = layui.form;
            //第一个实例
            table.render({
                elem: '#productTable'
                , url: 'http://localhost:8092/product/getProductByTypeId' //数据接口
                , page: true //开启分页
                ,method:"post"
                ,where:{typeId:typeId,brandId:brandId}
                , cols: [[ //表头
                    {
                        field: 'id', title: '选择', width: 30, sort: true, fixed: 'left',
                        templet: function (d) {
                            return "<input type='checkbox'>";
                        }
                    }
                    , {field: 'productName', title: '商品名称', width: 80}
                    , {field: 'subtitle', title: '宣传标题', width: 80, sort: true}
                    , {field: 'detail', title: '商品描述', width: 80}
                    , {
                        field: 'subImgs', title: '主图片', width: 177,
                        templet: function (d) {
                            return "<img src='https://lizihan.oss-cn-beijing.aliyuncs.com/" + d.subImgs + "'>";
                        }
                    }
                    , {field: 'brandName', title: '品牌', width: 80, sort: true}
                    , {field: 'price', title: '价格', width: 80, sort: true}
                    , {field: 'stock', title: '库存', width: 80}
                    , {
                        field: 'id', title: '操作', width:255, sort: true,
                        templet: function (d) {
                            return "<button class=\"layui-btn layui-btn-normal\">收藏</button ><button class=\"layui-btn layui-btn-normal\">加入购物车</button>";
                        }
                    }
                ]]
                ,request: {
                    pageName: 'page' // 页码的参数名称，默认：page
                    , limitName: 'size' //每页数据量的参数名，默认：limit
                },
                done: function(res, curr, count){
                    //如果是异步请求数据方式，res即为你接口返回的信息。
                    //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                    $("#showProduct").html($("#productDiv").html())

                }
            });
            /* $.ajax({
                       url:"http://localhost:8092/product/getProductByTypeId",
                       type:"post",
                       data:{typeId:id},
                       dataType:"json",
                       success:function (result) {
                           console.info(result)
                           var data=result;
                           $("#showProduct").html("")
                           $("#table").html("")
                           var str="";
                           if(data.length>0){
                               for(var i=0;i<data.length;i++){
                                   str+="<tr>"
                                   str+="<td><input type='checkbox'></td>";
                                   str+="<td>"+data[i].productName+"</td>";
                                   str+="<td>"+data[i].subtitle+"</td>";
                                   str+="<td>"+data[i].detail+"</td>";
                                   str+="<td><img src='"+data[i].subImgs+"'></td>";
                                   str+="<td>"+data[i].brandName+"</td>";
                                   str+="<td>"+data[i].price+"</td>";
                                   str+="<td>"+data[i].stock+"</td>";
                                   str+="<td><button>收藏</button><button>加入购物车</button></td>";
                                   str+="</tr>";
                               }
                           }else {
                               str+="<tr><td>此类型没有商品</td><tr>"
                           }

                               $("#table").append(str)
                           $("#showProduct").html($("#productDiv").html())
                       }
                   })*/

        })
    }
    var brandId="";
    var times=0;
    function selectBrand(obj) {
        brandId=$(obj).attr("value")
        console.log(obj)
        var collection = $(".img1");
        $.each(collection, function () {
            $(this).removeClass("end");
        })

        if(times%2==0){
            $(obj).addClass("end");
        }
        if(times%2==1){
            $(obj).removeClass("end");
            brandId="";
        }
        times++
        findProductList()
    }
</script>
<link type="text/css" href="/js/css/style.css" rel="stylesheet" />
<div id="productDiv" style="display: none">
    <div class="layui-row" >
        <div class="layui-card" >
            <div class="layui-card-body">
                <div class="form-box">
                    <div class="layui-form layui-form-item">
                        <div class="layui-inline">
                            <div class="layui-form-mid">商品名称:</div>
                            <div class="layui-input-inline" style="width: 100px;">
                                <input type="text" autocomplete="off" class="layui-input">
                            </div>
                            <div class="layui-form-mid">品牌名称</div>
                            <div class="layui-input-inline" style="width: 100px;">
                                <input type="text" autocomplete="off" class="layui-input">
                            </div>
                            <button class="layui-btn layui-btn-blue">查询</button>
                            <button class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                    <table id="productTable"></table>
                </div>
            </div>
        </div>
    </div>
 <%--  <table border="1" cellpadding="0" cellspacing="0" style="width: 90%">
       <tr>
           <td>选择</td>
           <td>商品名称</td>
           <td>宣传标题</td>
           <td>商品描述</td>
           <td>主图片</td>
           <td>品牌</td>
           <td>价格</td>
           <td>库存</td>
           <td>操作</td>
       </tr>
       <tbody id="table" >

       </tbody>
   </table>--%>
</div>
</body>
</html>
