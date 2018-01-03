$(function(){
    var topicForm = $("#taskForm"), error = $("#taskForm .alert-danger");

    topicForm.validate({
        debug: true,
        errorElement: 'span', //default input error message container
        errorClass: 'help-block help-block-error', // default input error message class
        focusInvalid: false, // do not focus the last invalid input
        ignore: "", // validate all fields including form hidden input
        rules: {
            scheduler: {
                required: true
            },
            monitor: {
                required: true
            },
            executor: {
                required: true
            },
            sort: {
                required: true
            }
        },
        messages: {
            scheduler: {
                required: "请输入调度类。"
            },
            monitor: {
                required: "请输入监控类。"
            },
            executor: {
                required: "请输入执行类。"
            },
            sort: {
                required: "请输入优先级。"
            }
        },
        errorPlacement: function (error, element) { // render error placement for each input type
            if(element.attr("data-error-container")){
                error.appendTo(element.attr("data-error-container"));
            }else{
                error.insertAfter(element); // for other inputs, just perform default behavior
            }
        },
        highlight: function (element) { // hightlight error inputs
            $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
        },
        unhighlight: function (element) { // revert the change done by hightlight
            $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
        },
        success: function (label) {
            label.closest('.form-group').removeClass('has-error'); // set success class to the control group
        },
        submitHandler: function (form) {
            error.hide();
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
                form.reset();
            }).fail(function(error){
                swal({
                    title: "提交失败",
                    type: "error",
                    confirmButtonText: "确定"
                })
            })
        }

    });





})