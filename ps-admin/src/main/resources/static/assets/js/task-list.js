var TaskList = (function(){

    return{
        /**
         * 任务列表渲染器，用于排序功能
         * @param store  任务列表集合
         */
        tableRenderer: function(store){
            var $tableBody = $("[data-store]");
            $tableBody.empty();
            for(var i = 0, len = store.length; i < len; i++){
                var item = store[i], $row = $("<tr data-task-id='" + item.id + "'></tr>");
                $row.append("<td>" + item.sort + "</td>")
                    .append("<td>" + item.scheduler + "</td>")
                    .append("<td>" + item.monitor + "</td>")
                    .append("<td>" + item.executor + "</td>")
                    .append("<td>" + item.createTimeStr + "</td>");
                var $checkboxWrapper = $("<td class='text-center'></td>");
                var $checkbox = item.paused ? $('<input type="checkbox" name="bootstrap-switch"  />') : $('<input type="checkbox" name="bootstrap-switch" checked />')
                $checkboxWrapper.append($checkbox);
                $row.append($checkboxWrapper);
                $tableBody.append($row);
            }
            TaskList.initSwitch();
        },
        /**
         * 初始化switch组件
         */
        initSwitch: function(){
            $('[name="bootstrap-switch"]').bootstrapSwitch({
                onText: '执行',
                offText: '暂停',
                size: 'small',
                beforeChange: function (callback){
                    swal({
                        title: "您确定吗？",
                        type: "warning",
                        confirmButtonText: "确定",
                        showCancelButton: true,
                        cancelButtonText: '取消'
                    }, callback);
                },
                onSwitchChange: function(event, state){
                    $.ajax({
                        url: $.WEB_ROOT + '/task/update',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            taskId: $(event.target).closest("tr").attr("data-task-id"),
                            paused: !state
                        }
                    }).done(function(rs){
                        if(rs){
                            swal({
                                title: "修改成功",
                                type: "success",
                                confirmButtonText: "确定",
                                closeOnConfirm: false
                            }, function(){
                                window.location.reload();
                            })

                        }else{
                            swal({
                                title: "修改失败",
                                type: "error",
                                confirmButtonText: "确定",
                                closeOnConfirm: false
                            }, function(){
                                window.location.reload();
                            })
                        }
                    }).fail(function(error){
                        swal({
                            title: "提交失败",
                            type: "error",
                            confirmButtonText: "确定",
                            closeOnConfirm: false
                        }, function(){
                            window.location.reload();
                        })
                    })
                }
            });
        }
    }

})();

$(function(){
    TaskList.initSwitch();
})