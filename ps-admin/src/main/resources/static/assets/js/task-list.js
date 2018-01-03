$(function(){
    $('[name="bootstrap-switch"]').bootstrapSwitch({
        onText: '执行',
        offText: '暂停',
        size: 'small',
        onSwitchChange: function(){
            /*swal({
                    title: "您确定吗",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonClass: "btn-primary",
                    confirmButtonText: "启动",
                    cancelButtonText: "取消",
                    closeOnConfirm: false
                },
                function(){
                    swal({
                        title: "启动成功",
                        type: "success",
                        confirmButtonText: "确定"
                    });
                });*/
        }
    });
})