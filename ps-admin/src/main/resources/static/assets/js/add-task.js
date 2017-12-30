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
                alert('提交成功')
            }
        }).fail(function(error){
            console.log(error)
            alert('fail')
        })
    })

})