var Index = (function(){

    return{
        /**
         * 任务列表渲染器，用于排序功能
         * @param store  任务列表集合
         */
        tableRenderer: function(store){
            var $tableBody = $("[data-store]");
            $tableBody.empty();
            for(var i = 0, len = store.length; i < len; i++){
                var item = store[i], $row = $("<tr></tr>");
                $row.append("<td>" + item.sort + "</td>")
                    .append("<td>" + item.scheduler + "</td>")
                    .append("<td>" + item.monitor + "</td>")
                    .append("<td>" + item.executor + "</td>")
                    .append("<td>" + item.createTimeStr + "</td>");
                $tableBody.append($row);
            }
        }
    }

})();