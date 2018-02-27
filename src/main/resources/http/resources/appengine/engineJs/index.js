;
(function ($) {
    window.appEngine = {
        dataType: [
            {"value":"short","text":"short"},
            {"value":"int","text":"int"},
            {"value":"long","text":"long"},
            {"value":"float","text":"float"},
            {"value":"double","text":"double"},
            {"value":"boolean","text":"boolean"},
            {"value":"char","text":"char"},
            {"value":"long","text":"long"},
            {"value":"byte","text":"byte"},
            {"value":"java.lang.Integer","text": "Integer"},
            {"value":"java.lang.String","text":"String"},
            {"value":"java.lang.Boolean","text":"Boolean"},
            {"value":"java.lang.Float","text":"Float"},
            {"value":"java.lang.Double","text":"Double"},
            {"value":"java.util.Date","text":"java.util.Date"},
            {"value":"java.sql.Timestamp","text":"Timestamp"},
            {"value":"java.sql.Time","text":"Time"},
            {"value":"byte[]","text":"byte[]"},
            {"value":"java.sql.Date","text":"java.sql.Date"},
            {"value":"java.sql.Blob","text":"Blob"},
            {"value":"java.sql.Clob","text":"Clob"}
        ],
        loadSuccessData : function(data){
            if(data.rows.length > 0 && data.rows[0].name != undefined ){
                var entity = data.rows[0];
                $("#entityName").textbox("setValue",entity.name);
                $("#remark").textbox("setValue",entity.comm);
                $("#create").combobox("setValue",entity.create_flag);
                $("#createtime").textbox("setValue",entity.createtime);
                $("#datagrid-entity").datagrid("deleteRow",0);
                $("#aem_entityId").val(entity.id);
                var rows = $("#datagrid-entity").datagrid("getRows");
                for(var i=0;i<rows.length;i++){
                    if(rows[i].DATA_TYPE == '-4'){
                        $("#datagrid-entity").datagrid("getRows")[i].SOURCE_DATA_TYPE = appEngine.dataType[20].type;
                    }else if(rows[i].DATA_TYPE == '-5'){

                    }else{
                        $("#datagrid-entity").datagrid("getRows")[i].SOURCE_DATA_TYPE = appEngine.dataType[parseInt(rows[i].DATA_TYPE)].type;
                    }
                }
                return;
            }
            if(data.rows[0].status !=undefined && data.rows[0].status == "false"){
                $("#datagrid-entity").datagrid("deleteRow",0); //清空列表
                $("#entityName").textbox("clear");
                $("#remark").textbox("clear");
                $("#create").combobox("clear");
                $("#createtime").textbox("clear");
                $("#aem_entityId").val("");
                return;
            }
        },
        dialogId: new Array(),
        editMenu : function (menu) {
            switch(menu){
                case 'add': editMenu(menu); break;
                case 'del': delMenu(); break;
                case 'mod': editMenu(menu);break;
                default: true
            }
        },
        openDialog:function(options){
            var dialogId = appEngine.dialogId, _b = false;
            if(dialogId.length != 0)
            {
                for ( var _id in dialogId) {
                    $("#"+dialogId[_id]).remove();
                }
                _b = true;
            }
            $(document.body).append("<div id="+options.id+" class='easyui-dialog'></div>");
            $("#"+options.id).dialog({
                width : options.width,
                height : options.height,
                modal : options.modal==null?false:options.modal,
                maximizable : options.maximizable==null?false:options.maximizable,
                title : options.title,
                closed : options.closed==null ? false : options.closed,
                href : options.url,
                buttons : [ {text : "确定",iconCls : "icon-save",handler : function() {
                        options.save();
                    }
                }, {text : "返回",iconCls : "icon-undo",handler : function() {
                        $("#"+options.id).dialog("close");
                    }
                } ]
            });
            if(!_b)
            {
                appEngine.dialogId.push(options.id);
            }else{
                if($.inArray(options.id,dialogId)==-1)
                {
                    appEngine.dialogId.push(options.id);
                }
            }
            $("#"+options.id).dialog("open");
        },
        displayTable: function (clazz) {
            var data = $(clazz).data('detail'), data = data.replace(new RegExp("'","gm"),'"'), data = JSON.parse(data);
            var state = $.data($('#menu-list')[0], 'datagrid');
            var tables = data.row.tables, id = data.row.id, length = tables.length;
            var status = $(clazz).attr('status');
            if(status == 'opened'){
                $('#tr-h1-'+id).remove();
                $('#tr-h2-'+id).remove();
                $(clazz).attr('status', 'close');
                return;
            }
            var h1 = '<tr class="treegrid-tr-tree" id="tr-h1-'+id+'" style="height:'+ (length+1)*28 +'px">';
                h1 += '<td colspan="1" style="border-right:0">';
                h1 += '<div class="datagrid-row-detail">&nbsp;</div>';
                h1 += '</td></tr>';
            var h2 = '<tr class="treegrid-tr-tree" id="tr-h2-'+id+'" style="height:'+ (length+1)*28 +'px">';
                h2 += '<td colspan="6"><table id="entity-'+id+'"></table></td>';
            $('#' + state.rowIdPrefix + '-1-' + id).after(h1);
            $('#' + state.rowIdPrefix + '-2-' + id).after(h2);
            $('#entity-'+id).datagrid({
                singleSelect: true,
                rownumbers: true,
                checkbox: true,
                columns:[[
                    {field:'entityName',title:'实体名',width:'20%'},
                    {field:'remark',title:'备注',width:'30%'},
                    {field:'createDateStr',title:'创建时间',width:'30%'},
                    {field:'id',align:'center',title:'操作',width:'20%',formatter:function(value,row,index){
                        var h = "<a href='javascript:;' style='text-decoration: none;color:red;' data-id='"+value+"' onclick='appEngine.deleteEntity(this)'>删除</a>";
                        h += "<a href='javascript:;' style='text-decoration: none;margin-left: 5px;' onclick='appEngine.addEntity(\""+value+"\")'>修改</a>";
                        return h;
                    }},
                ]],
                data: tables,
                onSelect: function (index, row) {
                    $('#entityInfo').form('load', row);
                }
            });
            $(clazz).attr('status', 'opened');
        },
        deleteEntity: function (clazz) {
            var id= $(clazz).data('id');
            if(id == null || id == undefined) return;
            $.messager.progress({title : "温馨提示",msg : "请稍后，正在处理......"});
            $.post(path + '/menu/deleteEntity', {id: id}, function (result) {
                $.messager.progress("close");
                if (result.success) {
                    $.messager.show({title:'温馨提示',msg:result.data,timeout:3000,showType:'show'});
                    $('#menu-list').treegrid("reload");
                } else {
                    alert(result.data);
                }
            });
        },
        addEntity: function (id) {
            var rows = $("#menu-list").datagrid("getSelected");
            var menuId = rows.id;
            if(id != undefined){
                if(rows == null)return;
            }
            appEngine.openDialog({
                width: 420,
                height: 300,
                modal: true,
                maximizable: false,
                title: (id != undefined) ? '新增实体' : '修改实体',
                closed: true,
                id: "addEntity",
                url: path + '/engine/appengine/menu/entity.html?menuId=' + menuId + '&id='+id,
                save:function(){
                    $('#add-entity').form('submit', {
                        url : path + "/menu/addEntity",
                        method : 'post',
                        onSubmit : function() {
                            var result = $(this).form('validate');
                            if(result)$.messager.progress({title : "温馨提示",msg : "请稍后，正在处理......"});
                            return result;
                        },
                        success : function(data) {
                            $.messager.progress("close");
                            var result = eval('(' + data + ')');
                            if (result.success) {
                                $.messager.show({title:'温馨提示',msg:result.data,timeout:3000,showType:'show'});
                                $('#addEntity').dialog("close");
                                $('#menu-list').treegrid("reload");
                            } else {
                                $('#errorMsg').html("<span style='color:Red'>错误提示:" + result.data + "</span>");
                            }
                        }
                    });
                }
            });
        },
        createEntityCode: function () {  //生成实体代码
            if($("#sysMenuTableId").val() == "") return;
            var result = $('#entityInfo').form('validate');
            if(!result)return;
            appEngine.endEditing();//结束所有编辑行
            var entityRow = $('#datagrid-entity').datagrid("getRows");
            if(entityRow.length == 0)return;
            var entityInfo = $('#entityInfo').serializeArray();
            appEngine.openDialog({
                width: 400,
                height: 200,
                modal: true,
                maximizable: false,
                title: '选择生成业务代码',
                closed: true,
                id: "createEntityMode",
                save:function(){
                    var checks = $('#entityMode input:checkbox:checked');
                    if(checks.length == 0) return;
                    var entityMode = [];
                    $.each(checks, function (i, r) {
                        entityMode.push(r.getAttribute("name"));
                    });
                    entityInfo.push({name: "entityMode", value: JSON.stringify(entityMode)});
                    entityInfo.push({name: "entityRow", value: JSON.stringify(entityRow)});
                    $.messager.progress({title : "温馨提示",msg : "请稍后，正在处理......"});
                    $.post(path + '/menu/createBusinessCode', entityInfo , function (result) {
                        $.messager.progress("close");
                        if (result.success) {
                            $.messager.show({title:'温馨提示',msg:result.data,timeout:3000,showType:'show'});
                            $('#createEntityMode').dialog("close");
                        } else {
                            $('#errorMsg').html("<span style='color:Red'>错误提示:" + result.data + "</span>");
                        }
                    });
                }
            });
            var html = '<div style="padding: 5px;"><from id="entityMode">';
            html += '<input type="checkbox" checked name="entity" id="entity"/> <label for="entity">实体代码</label>';
            html += '<input type="checkbox" name="service" id="service"/> <label for="service">业务操作层代码</label>';
            html += '<input type="checkbox" name="dao" id="dao"/> <label for="dao">数据操作层代码</label>';
            html += '<div style="color: #0000FF;">说明：应用引擎只负责生成业务代码，不生成数据库表，新生成的实体代码重新启动项目后，会自动创建数据库表结构。</div>';
            html += '<div><label id="errorMsg" /></div>';
            html += '</from></div>';
            $('#createEntityMode').html(html);
        },
        endEditing:function(index, field){
            if (appEngine.endIndex == undefined){return true}
            if ($('#datagrid-entity').datagrid('validateRow', appEngine.endIndex)){
                //验证数据
                $('#datagrid-entity').datagrid('endEdit', appEngine.endIndex);
                appEngine.endIndex = undefined;
                return true;
            } else {
                return false;
            }
        },
        editEntity: function (type) {
            if(type == 'add'){
                var counts = $('#datagrid-entity').datagrid('getRows').length;
                $('#datagrid-entity').datagrid('appendRow',{ORDINAL_POSITION: counts + 1});
            }else if(type == "del"){
                var row = $('#datagrid-entity').datagrid('getSelected');
                var index = $('#datagrid-entity').datagrid('getRowIndex',row);
                $('#datagrid-entity').datagrid('deleteRow',index); //动态删除
            }else if(type == 'maintain'){

            }
        },
        onClickCell : function(index, field){
            if (appEngine.endEditing(index, field)){
                $('#datagrid-entity').datagrid('selectRow', index)
                    .datagrid('editCell', {index:index,field:field});
                appEngine.endIndex = index;
            }
        }
    };
    
    function delMenu() {
        var rows = $("#menu-list").datagrid("getSelected");
        if(rows==null)
            return;
        var id= rows.id;
        $.messager.confirm('确认','您确认想要删除记录吗？',function(r){
            if (r){
                $.messager.progress({title : "温馨提示",msg : "请稍后，正在处理......"});
                $.post(path + '/menu/delete', {id: id}, function (result) {
                    $.messager.progress("close");
                    if (result.success) {
                        $.messager.show({title:'温馨提示',msg:result.data,timeout:3000,showType:'show'});
                        $('#menu-list').treegrid("reload");
                    } else {
                        alert(result.data);
                    }
                });
            }
        });
    }
    //新增菜单
    function editMenu(type) {
        var id = "";
        if(type == 'mod'){
            var rows = $("#menu-list").datagrid("getSelected");if(rows==null)return; id= rows.id;
        };
        appEngine.openDialog({
            width: 550,
            height: 500,
            modal: true,
            maximizable: false,
            title: type == 'add' ? "新增菜单" : "修改菜单",
            closed: true,
            id: "editMenu",
            url: path + '/engine/appengine/menu/index.html?id=' + id,
            save:function(){
                $('#menu-edit').form('submit', {
                    url : path + "/menu/save",
                    method : 'post',
                    onSubmit : function() {
                        var result = $(this).form('validate');
                        if(result)$.messager.progress({title : "温馨提示",msg : "请稍后，正在处理......"});
                        return result;
                    },
                    success : function(data) {
                        $.messager.progress("close");
                        var result = eval('(' + data + ')');
                        if (result.success) {
                            $.messager.show({title:'温馨提示',msg:result.data,timeout:3000,showType:'show'});
                            $('#editMenu').dialog("close");
                            $('#menu-list').treegrid("reload");
                        } else {
                            $('#errorMsg').html("<span style='color:Red'>错误提示:" + result.data + "</span>");
                        }
                    }
                });
            }
        });
    }
}
)(jQuery);