<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath();%>
<html>
<head>
    <title>设置</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" href="<%= path%>/resources/layui/css/layui.css" type="text/css">
    <link rel="stylesheet" href="<%= path%>/resources/font-awesome/css/font-awesome.css" type="text/css">
    <script type="application/javascript">var path = '<%= path%>'</script>
    <style>
        body .layui-card {box-shadow: 1px 1px 1px 2px rgba(0,0,0,.05);}
        .layui-fluid {padding: 15px}
        .layadmin-backlog .layadmin-backlog-body {display: block;background-color: #f8f8f8;color: #999;
            border-radius: 2px;transition: all .3s;-webkit-transition: all .3s;padding-top: 25px;}
        .layadmin-carousel {height: 185px!important;background-color: #fff;}
        .layadmin-backlog-body:hover {background-color: #f2f2f2;color: #888;}
        .layadmin-backlog-body p cite {font-style: normal;font-size: 30px;font-weight: 300;color: #009688;}
        [lay-href], [lay-tips], [layadmin-event] {cursor: pointer;}
        .layadmin-carousel .layui-col-space10 {margin: 0;}
        .layadmin-backlog-body h3 {padding-bottom: 10px;font-size: 16px;text-align: center}
        .layadmin-backlog-body p{text-align: center}
    </style>
</head>
<body class="layui-layout-body" style="overflow-y:visible;">
<div class="layadmin-tabsbody-item layui-show">
    <div class="layui-fluid">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-row layui-col-space15">
                    <div class="layui-col-md3">
                        <div class="layui-card">
                            <div class="layui-card-body">
                                <div class="layui-carousel layadmin-carousel layadmin-backlog" lay-anim="" lay-indicator="inside" lay-arrow="none" style="width: 100%;height: 120px !important;">
                                    <div carousel-item="">
                                        <a lay-href="<%=path%>/sys/setting" class="layadmin-backlog-body">
                                            <h3>组织</h3>
                                            <p>自定义组织架构，方便管理设备权限</p>
                                        </a>
                                    </div>
                                    <button class="layui-icon layui-carousel-arrow" lay-type="sub"></button>
                                    <button class="layui-icon layui-carousel-arrow" lay-type="add"></button></div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-md3">
                        <div class="layui-card">
                            <div class="layui-card-body">
                                <div class="layui-carousel layadmin-carousel layadmin-backlog" lay-anim="" lay-indicator="inside" lay-arrow="none" style="width: 100%; height: 120px !important;">
                                    <div carousel-item="">
                                        <a lay-href="" class="layadmin-backlog-body">
                                            <h3>设备管理</h3>
                                            <p>自定义添加设备</p>
                                        </a>
                                    </div>
                                    <button class="layui-icon layui-carousel-arrow" lay-type="sub"></button>
                                    <button class="layui-icon layui-carousel-arrow" lay-type="add"></button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-md3">
                        <div class="layui-card">
                            <div class="layui-card-body">
                                <div class="layui-carousel layadmin-carousel layadmin-backlog" lay-anim="" lay-indicator="inside" lay-arrow="none" style="width: 100%;height: 120px !important;">
                                    <div carousel-item="">
                                        <a lay-href="" class="layadmin-backlog-body">
                                            <h3>库存脚本</h3>
                                            <p>创建和编辑脚本后，可以在任何设备上执行</p>
                                        </a>
                                    </div>
                                    <button class="layui-icon layui-carousel-arrow" lay-type="sub"></button>
                                    <button class="layui-icon layui-carousel-arrow" lay-type="add"></button></div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-col-md3">
                        <div class="layui-card">
                            <div class="layui-card-body">
                                <div class="layui-carousel layadmin-carousel layadmin-backlog" lay-anim="" lay-indicator="inside" lay-arrow="none" style="width: 100%;height: 120px !important;">
                                    <div carousel-item="">
                                        <a lay-href="" class="layadmin-backlog-body">
                                            <h3>通知</h3>
                                            <p>使用Email，SMS创建发送通知的模板。</p>
                                        </a>
                                    </div>
                                    <button class="layui-icon layui-carousel-arrow" lay-type="sub"></button>
                                    <button class="layui-icon layui-carousel-arrow" lay-type="add"></button></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="application/javascript">

</script>
</html>