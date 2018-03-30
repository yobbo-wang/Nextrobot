<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>自动化运维工具管理平台</title>
    <link rel="stylesheet" href="<%= path%>/resources/layui/css/layui.css" type="text/css">
    <link rel="stylesheet" href="<%= path%>/resources/font-awesome/css/font-awesome.css" type="text/css">
    <link rel="stylesheet" href="<%= path%>/resources/nextrobot/css/engine.css" type="text/css">
    <script type="application/javascript">var path = '<%= path%>'</script>
</head>
<body class="kit-theme">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header header layui-ms">
        <div class="layui-main">
            <a href="" class="logo">
                <img alt="logo" src="<%=path %>/resources/image/logo.png" />
            </a>
            <ul class="layui-nav layui-tab-left" lay-filter="">
                <li class="layui-nav-item layui-this">
                    <a href="javascript:;" >仪表盘</a>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">任务</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a data-options="url:'http://www.baidu.com',icon:'fa-share-alt',title:'任务模板',id:'task-template',renderType:'page'"
                             href="javascript:;" class="kit-target">
                                <span>任务模板</span>
                            </a>
                        </dd>
                        <dd>
                            <a href="javascript:;">
                                <span>定时任务</span>
                            </a>
                        </dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">任务模板</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" >自动化脚本</a><hr></dd>
                        <dd><a href="javascript:;" >YML模板</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">引擎流程</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">预案开发及维护</a><hr></dd>
                        <dd><a href="javascript:;">单任务流程</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">应用引擎</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">权限配置</a><hr></dd>
                        <dd>
                            <a data-options="url:'<%= path%>/engine/appengine/index.html',icon:'fa-share-alt',title:'应用引擎',id:'engine-setting'"
                             href="javascript:;" class="kit-target">应用引擎</a>
                        </dd>
                    </dl>
                </li>
            </ul>
            <ul class="layui-nav layui-layout-right layui-nav-right" lay-filter="">
                <li class="layui-nav-item">
                    <a href="javascript:;" class="kit-target" data-options="url:'<%= path%>/sys/setting',icon:'fa-share-alt',title:'设置',id:'setting'">设置</a>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">个人消息<span class="layui-badge-dot"></span></a>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;"><img src="<%=path%>/resources/image/user.jpg" class="layui-nav-img">admin</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" class="kit-target" data-options="url:'<%= path%>/login.jsp',icon:'fa-share-alt',title:'修改密码',id:'update-pwd'">修改信息</a></dd>
                        <dd><a href="javascript:;" class="kit-target" data-options="url:'<%= path%>/login.jsp',icon:'fa-share-alt',title:'安全管理',id:'security-manager'">安全管理</a></dd><hr>
                        <dd><a href="javascript:;" class="clearCache">清除缓存</a></dd>
                        <dd><a href="<%=path%>/logout" >退出系统</a></dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

     <div class="layui-side layui-bg-black kit-side">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree layui-bg-black layui-inline">
              <li class="layui-nav-item layui-nav-itemed">
                <a href="javascript:;">默认展开</a>
                <dl class="layui-nav-child">
                  <dd><a href="javascript:;">选项一</a></dd>
                  <dd><a href="javascript:;">选项二</a></dd>
                  <dd><a href="javascript:;">选项三</a></dd>
                  <dd><a href="">跳转项</a></dd>
                </dl>
              </li>
              <li class="layui-nav-item">
                <a href="javascript:;">解决方案</a>
                <dl class="layui-nav-child">
                  <dd><a href="">移动模块</a></dd>
                  <dd><a href="">后台模版</a></dd>
                  <dd><a href="">电商平台</a></dd>
                </dl>
              </li>
              <li class="layui-nav-item"><a href="">云市场</a></li>
              <li class="layui-nav-item"><a href="">社区</a></li>
            </ul>
        </div>
     </div>

    <!-- body 容器-->
    <div class="layui-body" id="container" style="overflow: hidden;
    border-top: 5px solid #1AA094;border-left: 2px solid #1AA094;">
        <div style="padding: 15px;"><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63e;</i> 正在拼命加载...</div>
    </div>
</div>
</body>
<script src="<%= path%>/resources/layui/layui.js" type="application/javascript"></script>
<script type="application/javascript">
    var message;
    layui.config({
        base: '<%=path%>/resources/nextrobot/js/',
        version: '1.0.1'
    }).use(['app', 'message'], function() {
        var app = layui.app,
            $ = layui.jquery,
            layer = layui.layer;
        //将message设置为全局以便子页面调用
        message = layui.message;
        //主入口
        app.set({
            type: 'iframe'
        }).init();

        $('dl.skin > dd').on('click', function() {
            var $that = $(this);
            var skin = $that.children('a').data('skin');
            switchSkin(skin);
        });
        var setSkin = function(value) {
                layui.data('kit_skin', {
                    key: 'skin',
                    value: value
                });
            },
            getSkinName = function() {
                return layui.data('kit_skin').skin;
            },
            switchSkin = function(value) {
                var _target = $('link[kit-skin]')[0];
                // _target.href = _target.href.substring(0, _target.href.lastIndexOf('/') + 1) + value + _target.href.substring(_target.href.lastIndexOf('.'));
                // setSkin(value);
            },
            initSkin = function() {
                var skin = getSkinName();
                switchSkin(skin === undefined ? 'default' : skin);
            }();

        $('#color').click(function(){
            layer.open({
                type:1,
                title:'配色方案',
                area: ['290px', 'calc(100% - 52px)'],
                offset: 'rb',
                shadeClose:true,
                id:'colors',
                anim: 2,
                shade:0.2,
                closeBtn:0,
                isOutAnim:false,
                resize:false,
                move: false,
                skin: 'color-class',
                btn:['黑白格','橘子橙','原谅绿','少女粉','天空蓝','枫叶红'],
                yes: function(index, layero){
                    switchSkin('default');
                }
                ,btn2: function(index, layero){
                    switchSkin('orange');
                    return false;
                }
                ,btn3: function(index, layero){
                    switchSkin('green');
                    return false;
                }
                ,btn4: function(index, layero){
                    switchSkin('pink');
                    return false;
                }
                ,btn5: function(index, layero){
                    switchSkin('blue.1');
                    return false;
                }
                ,btn6: function(index, layero){
                    switchSkin('red');
                    return false;
                }

            });
        })
        $('.layui-nav-item').click(function(){
            $(this).siblings('li').attr('class','layui-nav-item');
        })

        //清除缓存
        $(".clearCache").click(function(){
            window.sessionStorage.clear();
            window.localStorage.clear();
            var index = layer.msg('清除缓存中，请稍候',{icon: 16,time:false,shade:0.8});
            setTimeout(function(){
                layer.close(index);
                layer.msg("缓存清除成功！");
            },1000);
        })
    });
</script>
</html>
