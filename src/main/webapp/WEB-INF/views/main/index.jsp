<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath();%>
<html>
<head>
    <title>首页</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" href="<%= path%>/resources/layui/css/layui.css" type="text/css">
    <link rel="stylesheet" href="<%= path%>/resources/font-awesome/css/font-awesome.css" type="text/css">
    <script type="application/javascript">var path = '<%= path%>'</script>
    <style>
        body .layui-card {box-shadow: 1px 1px 1px 2px rgba(0,0,0,.05);}
        .layui-fluid {padding: 15px}
        .layadmin-backlog .layadmin-backlog-body {display: block;padding: 10px 15px;background-color: #f8f8f8;color: #999;border-radius: 2px;transition: all .3s;-webkit-transition: all .3s;}
        .layadmin-carousel {height: 185px!important;background-color: #fff;}
        .layadmin-backlog-body:hover {background-color: #f2f2f2;color: #888;}
        .layadmin-backlog-body p cite {font-style: normal;font-size: 30px;font-weight: 300;color: #009688;}
        [lay-href], [lay-tips], [layadmin-event] {cursor: pointer;}
        .layadmin-carousel .layui-carousel, .layadmin-carousel>[carousel-item]>* {background-color: #fff;}
        .layadmin-carousel .layui-col-space10 {margin: 0;}
        .layadmin-backlog-body h3 {padding-bottom: 10px;font-size: 12px;}
    </style>
</head>
<body class="layui-layout-body" style="overflow-y:visible;">
<div class="layadmin-tabsbody-item layui-show">
    <div class="layui-fluid">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-row layui-col-space15">
                    <div class="layui-col-md6">
                        <div class="layui-card">
                            <div class="layui-card-header">主机</div>
                            <div class="layui-card-body">
                                <div class="layui-carousel layadmin-carousel layadmin-backlog" lay-anim="" lay-indicator="inside" lay-arrow="none" style="width: 100%; height: 280px;">
                                    <div carousel-item="">
                                        <ul class="layui-row layui-col-space10 layui-this">
                                            <li class="layui-col-xs6">
                                                <a lay-href="" class="layadmin-backlog-body">
                                                    <h3>应用主机</h3>
                                                    <p>
                                                        <cite>220</cite>
                                                        <span style="float: right;color: red;font-style: normal;font-size: 22px;font-weight: 300;margin-right: 10px;">2</span>
                                                    </p>
                                                </a>
                                            </li>
                                            <li class="layui-col-xs6">
                                                <a lay-href="" class="layadmin-backlog-body">
                                                    <h3>存储主机</h3>
                                                    <p>
                                                        <cite>9</cite>
                                                        <span style="float: right;width: 80%;">
                                                            <marquee align="middle" behavior="scroll" color="red">正在执行任务...</marquee>
                                                        </span>
                                                    </p>
                                                </a>
                                            </li>
                                            <li class="layui-col-xs6">
                                                <a lay-href="" class="layadmin-backlog-body">
                                                    <h3>数据库主机</h3>
                                                    <p><cite>75</cite></p>
                                                </a>
                                            </li>
                                            <li class="layui-col-xs6">
                                                <a lay-href="" class="layadmin-backlog-body">
                                                    <h3>代理主机</h3>
                                                    <p><cite>2</cite></p>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                    <button class="layui-icon layui-carousel-arrow" lay-type="sub"></button><button class="layui-icon layui-carousel-arrow" lay-type="add"></button></div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-md6">
                        <div class="layui-card">
                            <div class="layui-card-header">数据库</div>
                            <div class="layui-card-body">
                                <div class="layui-carousel layadmin-carousel layadmin-backlog" lay-anim="" lay-indicator="inside" lay-arrow="none" style="width: 100%; height: 280px;">
                                    <div carousel-item="">
                                        <ul class="layui-row layui-col-space10 layui-this">
                                            <li class="layui-col-xs6">
                                                <a lay-href="" class="layadmin-backlog-body">
                                                    <h3>MYSQL</h3>
                                                    <p><cite>2</cite></p>
                                                </a>
                                            </li>
                                            <li class="layui-col-xs6">
                                                <a lay-href="" class="layadmin-backlog-body">
                                                    <h3>ORACLE</h3>
                                                    <p><cite>9</cite></p>
                                                </a>
                                            </li>
                                            <li class="layui-col-xs6">
                                                <a lay-href="" class="layadmin-backlog-body">
                                                    <h3>DB2</h3>
                                                    <p><cite>75</cite></p>
                                                </a>
                                            </li>
                                            <li class="layui-col-xs6">
                                                <a lay-href="" class="layadmin-backlog-body">
                                                    <h3>SQLServer</h3>
                                                    <p><cite>2</cite></p>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                    <button class="layui-icon layui-carousel-arrow" lay-type="sub"></button><button class="layui-icon layui-carousel-arrow" lay-type="add"></button></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
