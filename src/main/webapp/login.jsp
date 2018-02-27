<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>自动化运维系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link href="<%= path%>/resources/layui/css/layui.css" rel="stylesheet" />
    <link id="layuicss-layer" rel="stylesheet" href="<%=path %>/resources/layui/css/modules/layer/layer.css" media="all">
    <link id="layuicss-layuiAdmin" rel="stylesheet" href="<%=path %>/resources/ms/css/admin-1.css" media="all">
    <link href="<%=path %>/resources/ms/css/login-1.css" rel="stylesheet" />
    <link href="<%= path%>/resources/font-awesome/css/font-awesome.css" rel="stylesheet" />
</head>
<body class="layui-layout-body">
<div id="LAY_app">
    <div class="layadmin-user-login" id="LAY-user-login" style="display: none;background: url('<%=path%>/resources/image/background.png') no-repeat center center;
            background-size: cover;background-repeat: no-repeat;">
        <div class="layadmin-user-login-main" style="float: right;background-color: #fff;margin-right: 10%;">
            <div class="layadmin-user-login-box layadmin-user-login-header">
                <h2>先进自动化运维管理系统</h2>
                <p>开源自动化运维工具</p>
            </div>
            <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
                <form class="layui-form" id="login">
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-username"><i class="fa fa-user"></i></label>
                        <input type="text" name="name" lay-verify="required" autocomplete="off" placeholder="用户名:demo" class="layui-input" >
                    </div>
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"><i class="fa fa-unlock-alt"></i></label>
                        <input type="password" name="password" lay-verify="required" autocomplete="off" placeholder="密码:tplay" class="layui-input">
                    </div>
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"><i class="fa fa-code"></i></label>
                        <input type="text" name="captcha" lay-verify="required" autocomplete="off" placeholder="验证码" class="layui-input" style="width:62%;float: left;margin-right:11px;">
                        <img src="<%= path%>/resources/image/code.png" alt="captcha"  height="36" id="captcha" style="margin-top: 1px" />
                        <%--<img src="/tplay/public/captcha.shtml" alt="captcha" onclick="this.src='/tplay/public/captcha.shtml?seed='+Math.random()" height="36" id="captcha" style="margin-top: 1px" />--%>
                    </div>
                    <div class="layui-form-item">
                        <input type="checkbox" lay-skin="primary" title="记住账号" name="remember" value="1" ><div class="layui-unselect layui-form-checkbox" lay-skin="primary"><span>记住账号?</span><i class="layui-icon"></i></div>
                    </div>
                    <div class="layui-form-item">
                        <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登 入</button>
                    </div>
                    <input type="hidden" name="__token__" value="261bb41130d0791893522d783e9e0522804050f1" />
                </form>
            </div>
        </div>
    </div>
</div>

<script src="<%=path %>/resources/layui/layui.js"></script>
<script>
    layui.use(['layer', 'form'], function() {
        var layer = layui.layer,
            $ = layui.jquery,
            form = layui.form;
        $(window).on('load', function() {
            form.on('submit(login)', function(data) {
                $.ajax({
                    url: "<%=path%>/sys/login",
                    data: $('#login').serialize(),
                    type: 'post',
                    async: false,
                    success: function(res) {
                        layer.msg(res.data.msg,{offset: '50px',anim: 0.3});
                        if(res.success) {
                            setTimeout(function() {
                                location.href = res.data.url;
                            }, 1000);
                        } else {
                            $('#captcha').click();
                        }
                    }
                })
                return false;
            });
        });
    });
</script>
</body>
</html>
