$(function(){
    $('[name="bootstrap-switch"]').bootstrapSwitch({
        onText: '执行',
        offText: '暂停',
        size: 'small',
        onSwitchChange: function(event, state){
            console.log(state)
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
})