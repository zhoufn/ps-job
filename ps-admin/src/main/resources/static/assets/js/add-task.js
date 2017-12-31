$(function(){
    $('#addBtn').on('click', function(){
        $.ajax({
            url: $.WEB_ROOT + '/task/add',
            type: 'POST',
            dataType: 'json',
            data: {
                scheduler: $("#scheduler").val(),
                monitor: $("#monitor").val(),
                executor: $("#executor").val(),
                sort: $("#sort").val()
            }
        }).done(function(rs){
            if(rs){
                swal({
                    title: "提交成功",
                    type: "success",
                    confirmButtonText: "确定"
                })
            }else{
                swal({
                    title: "提交失败",
                    type: "error",
                    confirmButtonText: "确定"
                })
            }
        }).fail(function(error){
            swal({
                title: "提交失败",
                type: "error",
                confirmButtonText: "确定"
            })
        })
    })

})